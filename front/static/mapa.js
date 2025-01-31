const map = L.map('map').setView([-4.007890, -79.211677], 14);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: 'Map data © <a href="https://www.openstreetmap.org/">OpenStreetMap</a>'
}).addTo(map);

let baseUrl = 'http://localhost:8000/myapp/grafo';
let markers = {};

async function fetchGrafo() {
    try {
        const response = await fetch(baseUrl); // Asegúrate de que esta URL esté correcta
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const r = await response.json();
        const grafo = r.data; // Verifica que 'data' contiene nodes y edges
        render(grafo);
    } catch (error) {
        console.error("Error al obtener el grafo:", error);
    }
}

function render(grafo) {
    const nodes = grafo.nodes;
    const edges = grafo.edges;

    nodes.forEach(node => {
        const marker = L.marker([node.lat, node.long]).addTo(map);
        marker.bindPopup(`<b>${node.nombre}</b>`);
        markers[node.id] = [node.lat, node.long];
    });

    edges.forEach(edge => {
        const from = markers[edge.from];
        const to = markers[edge.to];
        if (from && to) {
            L.polyline([from, to], { color: 'blue', weight: 3 }).addTo(map);
        }
    });
}

async function fetchMinPath(algoritmo, origen, destino) {
    let url = `${baseUrl}/minPath/${algoritmo}/${origen}/${destino}`;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const json = await response.json();
        drawMinPath(json.data);
    } catch (error) {
        console.error("Error al obtener el camino más corto:", error);
    }
}

function drawMinPath(path) {
    if (!path || path.length < 2) {
        console.error("Camino no válido recibido:", path);
        return;
    }
    
    let polylinePoints = path.map(id => markers[id]);
    L.polyline(polylinePoints, { color: 'red', weight: 4 }).addTo(map);
}

async function fetchMinWeight(algoritmo, origen, destino) {
    let url = `${baseUrl}/minWeight/${algoritmo}/${origen}/${destino}`;
    let card = document.getElementById('minWeight');
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const json = await response.json();
        const weight = json.data;

        const routeInfo = document.getElementById("route-info");
        const routeDetails = document.getElementById("route-details");
        const closeButton = document.getElementById("close-card");

        routeDetails.innerHTML = `La <strong>ruta</strong> es de: <strong>${weight}</strong> km.`;
        routeInfo.classList.remove("d-none");

        closeButton.addEventListener("click", function () {
            routeInfo.classList.add("d-none");
        });
    } catch (error) {
        console.error("Error al obtener el peso más corto:", error);
    }
}

document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    
    let origen = document.getElementById('origin').value;
    let destino = document.getElementById('destination').value;
    let algoritmo = document.getElementById('algorit').value;
    
    if (origen && destino && algoritmo) {
        fetchMinPath(algoritmo, origen, destino);
        fetchMinWeight(algoritmo, origen, destino);
    } else {
        alert("Por favor seleccione todas las opciones");
    }
});

fetchGrafo();