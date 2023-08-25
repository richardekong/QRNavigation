(g => {
    var h, a, k, p = "The Google Maps JavaScript API", c = "google", l = "importLibrary", q = "__ib__", m = document,
        b = window;
    b = b[c] || (b[c] = {});
    var d = b.maps || (b.maps = {}), r = new Set, e = new URLSearchParams,
        u = () => h || (h = new Promise(async (f, n) => {
            await (a = m.createElement("script"));
            e.set("libraries", [...r] + "");
            for (k in g) e.set(k.replace(/[A-Z]/g, t => "_" + t[0].toLowerCase()), g[k]);
            e.set("callback", c + ".maps." + q);
            a.src = `https://maps.${c}apis.com/maps/api/js?` + e;
            d[q] = f;
            a.onerror = () => h = n(Error(p + " could not load."));
            a.nonce = m.querySelector("script[nonce]")?.nonce || "";
            m.head.append(a)
        }));
    d[l] ? console.warn(p + " only loads once. Ignoring:", g) : d[l] = (f, ...n) => r.add(f) && u().then(() => d[l](f, ...n))
})
({key: "AIzaSyAu0FkaOcWxFUUZHz-hdsJeJX3CVO2NfUo", v: "beta"});

// Initialize and add the map
let map;

async function initMap() {
    // pass a list locations from thymeleaf template
    const positions = locations;
    // Request needed libraries.
    //@ts-ignore
    const {Map} = await google.maps.importLibrary("maps");
    const {AdvancedMarkerElement} = await google.maps.importLibrary("marker");


    // The map, centered at a location
    map = new Map(document.getElementById("map"), {
        zoom: 15,
        center: findCenter(positions),
        mapId: "DEMO_MAP_ID",
        disableDefaultUI: true,
    });

    // The markers, positioned at desired locations
    positions.forEach(position => {
        const logo = document.createElement("img");
        logo.src = logoURL;
        logo.style.width = '15%';
        logo.style.height = '15%';
        logo.style.borderRadius = '5px';
        new AdvancedMarkerElement({
            map: map,
            position: position,
            content: logo
        });

    });
}

const findCenter = (locs) => {
    let averageLatitude = locs.reduce((total, location) => total + location.lat, 0) / locs.length;
    let averageLongitude = locs.reduce((total, location) => total + location.lng, 0) / locs.length;
    return {lat: averageLatitude, lng: averageLongitude};
};


initMap();
