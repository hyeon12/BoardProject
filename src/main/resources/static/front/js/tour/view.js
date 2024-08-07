window.addEventListener("DOMContentLoaded", function() {
    const options = {
        center: {
            lat: 37.559000150419294,
            lng: 126.94180349844724,
        },
        marker: [
                {lat: 37.559000150419294, lng: 126.94180349844724},
                {lat: 37.559352862687014, lng: 126.94455347992769},
                {lat: 37.556803442460954, lng: 126.94541550198346},
        ],
        markerImage: "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
    };

    mapLib.load("map1", 1000, 600, options);

    /*
    mapLib.load("map1", 300, 300, options);
    mapLib.load("map2", 400, 400, options);
    mapLib.load("map3", 500, 500, options);
    */
});
