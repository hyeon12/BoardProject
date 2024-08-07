const items = [
    [126.94180349844724, 37.559000150419294], //126.94175748357844, 37.559955184451866
    [126.94455347992769, 37.559352862687014],
    [126.94541550198346, 37.556803442460954]
];

window.addEventListener("DOMContentLoaded", function(){

    // 여러 개의 마커 생성하기 => 위에 정의한 items 좌표의 마커 생성하기
    const mapEl = document.getElementById("map");
    mapEl.style.width = "1000px";
    mapEl.style.height = "600px";

    const map = new kakao.maps.Map(mapEl, {
        center: new kakao.maps.LatLng(items[0][1], items[0][0]),
        level: 5,
    });
    // 좌표 객체를 만들어서 여러 개의 마커를 생성하는 것
    const markers = items.map(pos => {
        const position = new kakao.maps.LatLng(pos[1], pos[0]);
        return new kakao.maps.Marker({position});
    }); // pos=position

    markers.forEach(marker => marker.setMap(map));
    // 지도 클릭시 좌표 정보
            kakao.maps.event.addListener(map, 'click', function(e) {
                const latLng = e.latLng; // 이벤트 발생한 좌표 가져옴!
                console.log(latLng);
                //const marker = new kakao.maps.Marker({
                //    position: latLng
                //});
                //marker.setMap(map); // 마커 생성
            });

    const iwContent = '<h1>정보!</h1>'; // 마커보다 조금 위, 혹은 조금 아래로 위치 조정 필요
    const iwPos = new kakao.maps.LatLng(items[0][1] + 0.02, items[0][0]);

    const infoWindow = new kakao.maps.InfoWindow({
        position: iwPos,
        content: iwContent
    });

    infoWindow.open(map, markers[0]);

    const removeEls = document.getElementsByClassName("remove");
    for (let i = 0; i < removeEls.length; i++) {
        removeEls[i].addEventListener("click", function() {
            markers[i].setMap(null);
        });
    }

    /*
    let map;
    // 마커 생성하기 + 현 위치에 마커 표시
    navigator.geolocation.getCurrentPosition((pos) =>{
        const { latitude, longitude } = pos.coords; // 현 위치

        const mapOption = {
            center: new kakao.maps.LatLng(latitude, longitude),
            level: 3,
        };

        map = new kakao.maps.Map(mapEl, mapOption);

        const markerPos = new kakao.maps.LatLng(latitude, longitude); // 좌표 객체
        const marker = new kakao.maps.Marker({ // 마커 객체
            position: markerPos
        });

        marker.setMap(map); // 현 위치 기준 마커 생성
        mapProcess(map); // 여러 개의 마커 생성(아래 mapProcess 함수 참고)
    }); */

    /* 여러 개의 마커 제어하기 */
    /*
    function mapProcess(map){
        // 지도 클릭시 좌표 정보
        kakao.maps.event.addListener(map, 'click', function(e) {
            //console.log(e);
            const latLng = e.latLng; // 이벤트 발생한 좌표 가져옴!
            const marker = new kakao.maps.Marker({
                position: latLng
            });

            marker.setMap(map); // 마커 생성
        });
    }*/
});