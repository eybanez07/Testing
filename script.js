// Original image dimensions
const imgWidth = 1527;
const imgHeight = 1392;

// Locations (x,y based on original image)
const locations = {
    fridge: {x: 995, y: 53},
    bed: {x: 400, y: 800},
    table: {x: 700, y: 600},
    door: {x: 100, y: 1200}
};

function findLocation() {
    const input = document.getElementById("search").value.toLowerCase();
    const marker = document.getElementById("marker");
    const map = document.getElementById("house-map");

    if (locations[input]) {
        // Get image displayed size
        const rect = map.getBoundingClientRect();
        const scaleX = rect.width / imgWidth;
        const scaleY = rect.height / imgHeight;

        const x = locations[input].x * scaleX;
        const y = locations[input].y * scaleY;

        // Show marker
        marker.style.left = x + "px";
        marker.style.top = y + "px";
        marker.style.display = "block";
    } else {
        alert("Location not found!");
        marker.style.display = "none";
    }
}
