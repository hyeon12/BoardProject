/**
* 파일 업로드, 삭제, 조회 공통 기능
*
*/

const fileManager = {
    /**
    * 파일 업로드
    *
    */
    upload(files, gid, location) {
        try {
            if (!files || files.length == 0) {
                throw new Error("파일을 선택 하세요.");
            }

            if (!gid || !gid.trim()) {
                throw new Error("필수 항목(gid) 누락 입니다.");
            }

            const formData = new FormData();
            formData.append("gid", gid.trim());

            for (const file of files) {
                formData.append("file", file); // 전송될 name 값 = "file"
            }

            if (location && location.trim()) { // location 있고, 공백 아닐때!
                formData.append("location", location.trim()); // name="location"
            }

            const { ajaxLoad } = commonLib; // 비구조 할당 = 해당 기능만 구조 분해!

            ajaxLoad('/file/upload', 'POST', formData);

        } catch (e) {
            console.error(e);
            alert(e.message);
        }
    },
    /**
    * 파일 삭제
    *
    */
    delete() {

    },
    /**
    * 파일 조회
    *
    */
    search() {

    }
};


window.addEventListener("DOMContentLoaded", function() {
    // 파일 업로드 버튼 이벤트 처리 S
    const fileUploads = document.getElementsByClassName("fileUploads");
    const fileEl = document.createElement("input");
    fileEl.type = 'file';
    fileEl.multiple = true;

    for (const el of fileUploads) {
        el.addEventListener("click", function() {
            fileEl.value = "";
            delete fileEl.gid;
            delete fileEl.location;

            const dataset = this.dataset;
            fileEl.gid = dataset.gid;
            if (dataset.location) fileEl.location = dataset.location;

            fileEl.click();

        });
    }
    // 파일 업로드 버튼 이벤트 처리 E

    // 파일 업로드 처리
    fileEl.addEventListener("change", function(e) {
        const files = e.target.files;
        fileManager.upload(files, fileEl.gid, fileEl.location);

    });

});