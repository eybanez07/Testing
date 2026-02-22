import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class CampusNavigator extends JFrame {

    private Image mapImage;
    private JComboBox<String> floorDropdown;
    private JTextField searchField;
    private MapPanel mapPanel;

    private HashMap<String, Point> roomLocations;
    private Point currentMarker;

    private final int MIN_FLOOR = 3;
    private final int MAX_FLOOR = 7;

    public CampusNavigator() {

        setTitle("ADDU Finster Navigator");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel();

        String[] floors = new String[MAX_FLOOR - MIN_FLOOR + 1];
        for (int i = 0; i < floors.length; i++) {
            floors[i] = "Floor " + (i + MIN_FLOOR);
        }

        floorDropdown = new JComboBox<>(floors);
        searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");

        topPanel.add(new JLabel("Floor:"));
        topPanel.add(floorDropdown);
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // ===== MAP PANEL =====
        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        loadFloor(MIN_FLOOR);

        floorDropdown.addActionListener(e -> {
            int selectedFloor = floorDropdown.getSelectedIndex() + MIN_FLOOR;
            loadFloor(selectedFloor);
        });

        searchBtn.addActionListener(e -> searchRoom());

        setVisible(true);
    }

    private void loadFloor(int floor) {

        ImageIcon icon = new ImageIcon("images/finster/floor" + floor + ".jpg");

        if (icon.getIconWidth() == -1) {
            System.out.println("Image not found for floor " + floor);
        }

        mapImage = icon.getImage();
        floorDropdown.setSelectedIndex(floor - MIN_FLOOR);

        initializeRooms(floor);
        currentMarker = null;

        mapPanel.repaint();
    }

    // 🔥 MANUALLY PLACE ROOMS HERE
    private void initializeRooms(int floor) {

        roomLocations = new HashMap<>();

        if (floor == 3) {
            roomLocations.put("F301", new Point(819, 779));
            roomLocations.put("F302", new Point(400, 600));
            roomLocations.put("F303", new Point(500, 600));
            roomLocations.put("F304", new Point(600, 600));
            roomLocations.put("F305", new Point(700, 600));
            roomLocations.put("F306", new Point(800, 600));
            roomLocations.put("F307", new Point(900, 600));
            roomLocations.put("F308", new Point(1000, 600));
            roomLocations.put("F309", new Point(1100, 600));
            roomLocations.put("F310", new Point(1200, 600));
            roomLocations.put("F311", new Point(1300, 600));
            roomLocations.put("F312", new Point(1400, 600));
            roomLocations.put("F313", new Point(1450, 600));
        }

        if (floor == 4) {
            roomLocations.put("F401", new Point(300, 600));
        }

        if (floor == 5) {
            roomLocations.put("F501", new Point(300, 600));
        }

        if (floor == 6) {
            roomLocations.put("F601", new Point(300, 600));
        }

        if (floor == 7) {
            roomLocations.put("F701", new Point(300, 600));
        }
    }

    private void searchRoom() {

        String input = searchField.getText().toUpperCase().trim();

        if (!input.matches("F[3-7][0-1][0-9]") &&
            !input.matches("F[3-7]0[1-9]") &&
            !input.matches("F[3-7]1[0-3]")) {

            JOptionPane.showMessageDialog(this,
                    "Invalid format.\nExample: F305\nFloors 3–7, Rooms 01–13");
            return;
        }

        int floor = Character.getNumericValue(input.charAt(1));

        loadFloor(floor);

        if (roomLocations.containsKey(input)) {
            currentMarker = roomLocations.get(input);
            mapPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Room not registered yet.");
        }
    }

    // =====================================================
    // MAP PANEL (WITH COORDINATE CLICK TOOL)
    // =====================================================
    class MapPanel extends JPanel {

        public MapPanel() {

            // CLICK MAP TO PRINT REAL COORDINATES
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    if (mapImage == null) return;

                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    int imgWidth = mapImage.getWidth(null);
                    int imgHeight = mapImage.getHeight(null);

                    double scaleX = (double) panelWidth / imgWidth;
                    double scaleY = (double) panelHeight / imgHeight;
                    double scale = Math.min(scaleX, scaleY);

                    int drawWidth = (int) (imgWidth * scale);
                    int drawHeight = (int) (imgHeight * scale);

                    int offsetX = (panelWidth - drawWidth) / 2;
                    int offsetY = (panelHeight - drawHeight) / 2;

                    int realX = (int) ((e.getX() - offsetX) / scale);
                    int realY = (int) ((e.getY() - offsetY) / scale);

                    System.out.println("REAL IMAGE COORDINATES -> X: " + realX + " Y: " + realY);
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (mapImage == null) return;

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = mapImage.getWidth(this);
            int imgHeight = mapImage.getHeight(this);

            double scaleX = (double) panelWidth / imgWidth;
            double scaleY = (double) panelHeight / imgHeight;
            double scale = Math.min(scaleX, scaleY);

            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);

            int offsetX = (panelWidth - drawWidth) / 2;
            int offsetY = (panelHeight - drawHeight) / 2;

            g.drawImage(mapImage, offsetX, offsetY, drawWidth, drawHeight, this);

            if (currentMarker != null) {

                int scaledX = (int) (currentMarker.x * scale) + offsetX;
                int scaledY = (int) (currentMarker.y * scale) + offsetY;

                g.setColor(Color.BLUE);
                g.fillOval(scaledX - 10, scaledY - 10, 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        new CampusNavigator();
    }
}
