// 하나의 화면에 여러 가지 지도 출력, 지정한 id 에 지도 출력
const mapLib = {
    /**
    * 지도 로드
    *
    * @param mapId : 지도를 출력할 요소 id 이름 -> mapId 에 load
    * @param width : 지도 너비
    * @param height : 지도 높이
    * @param options - 옵션
    *                - center: { lat: 위도, lng: 경도, 마커 이미지, 인포 윈도우 ... }
    *                - zoom : 확대 정도(1~10) // 숫자가 작을 수록 확대(+++)
    *                - markerImage : 공통 마커 이미지 주소, 개별 마커 이미지가 있는 경우는 그걸로 대체,
    *                - marker : [{ lat : 위도, lng : 경도, info : html 데이터(인포윈도우), image: 이미지 주소 - 마커이미지}]
    */
    load(mapId, width = 300, height = 300, options){
        const mapEl = document.getElementById(mapId);
        if (!mapEl || !options?.center) return;

        // 입력된 값대로 출력 (동적으로)
        mapEl.style.width = `${width}px`;
        mapEl.style.height = `${height}px`;

        let { center, marker, markerImage } = options;

        // 지도 가운데 좌표 처리 S
        const zoom = options?.zoom ?? 3; // 기본값 3
        const position = new kakao.maps.LatLng(center.lat, center.lng);
        const map = new kakao.maps.Map(mapEl, {
            center: position,
            level: zoom,
        });
        // 지도 가운데 좌표 처리 E

        // 마커 출력 처리 S
        if (marker) { // 있을 때만
            if (!Array.isArray(marker)) marker = [marker];

            const markers = marker.map(m => {

                const opt = { position: new kakao.maps.LatLng(m.lat, m.lng)}

                // 마커 이미지 처리
                const mi = markerImage ? markerImage : m.image;
                if(mi) {
                    const mImage = new kakao.maps.MarkerImage(mi, new kakao.maps.Size(64, 69), {offset: new kakao.maps.Point(27, 69)});
                    opt.image = mImage;
                }

                const _marker = new kakao.maps.Marker(opt);

                _marker.setMap(map);

                return _marker;
            });

        } // endif
        // 마커 출력 처리 E
    }
};