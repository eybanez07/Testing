import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

public class HouseLocator extends JFrame {

    private Image mapImage;
    private JTextField searchField;
    private MapPanel mapPanel;

    // Store locations and their original image coordinates
    private HashMap<String, Point> locations;

    public HouseLocator() {
        setTitle("House Location Finder");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load image
        mapImage = new ImageIcon("house.png").getImage();

        // Initialize locations (based on 1527x1392 image)
        locations = new HashMap<>();
        locations.put("fridge", new Point(995, 53));
        locations.put("bed", new Point(1239, 632));
        locations.put("table", new Point(440, 550));
        locations.put("door", new Point(1166, 1372));

        // Top panel (search bar)
        JPanel topPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Find");

        topPanel.add(new JLabel("Enter location: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Map panel
        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // Button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchLocation();
            }
        });

        setVisible(true);
    }

    private void searchLocation() {
        String input = searchField.getText().toLowerCase();

        if (locations.containsKey(input)) {
            Point p = locations.get(input);
            mapPanel.setMarker(p);
        } else {
            JOptionPane.showMessageDialog(this, "Location not found!");
            mapPanel.setMarker(null);
        }
    }

    class MapPanel extends JPanel {
        private Point marker;

        public void setMarker(Point p) {
            marker = p;
            repaint();
        }

protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int panelWidth = getWidth();
    int panelHeight = getHeight();

    int imgWidth = mapImage.getWidth(this);
    int imgHeight = mapImage.getHeight(this);

    g.drawImage(mapImage, 0, 0, panelWidth, panelHeight, this);

    if (marker != null && imgWidth > 0 && imgHeight > 0) {
        int scaledX = (marker.x * panelWidth) / imgWidth;
        int scaledY = (marker.y * panelHeight) / imgHeight;

        g.setColor(Color.BLUE);   // 🔵 BLUE DOT
        g.fillOval(scaledX - 10, scaledY - 10, 20, 20);
    }
}

    }

    public static void main(String[] args) {
        new HouseLocator();
    }
}
