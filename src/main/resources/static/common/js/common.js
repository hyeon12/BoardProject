const commonLib = {
    /*
    * ajax 요청 공통 기능
    *
    * @param responseType : 응답 데이터 타입(text - text로, 그 외는 json)
    */
    ajaxLoad(url, method = "GET", data, headers, responseType) {
        if (!url) {
            return; // url 없는 경우 보내지 X
        }

        const csrfToken = document.querySelector("meta[name='csrf_token']")?.content?.trim();
        // ?. - optional 체이닝 : null 이거나 undefined 이면 그냥 끝내도록! (안전하게 사용 가능)
        const csrfHeader = document.querySelector("meta[name='csrf_header']")?.content?.trim();
        const rootUrl = document.querySelector("meta[name='rootUrl']")?.content?.trim() ?? '';

        rootUrl = rootUrl === '/' ? '' : rootUrl;

        url = location.protocol + "//" + location.host + rootUrl + url;
        console.log(url);
        method = method.toUpperCase();
        if (method === 'GET') {
            data = null;
        }

        if (!data instanceof FormData && typeof data !== 'string' && data instanceof Object){
            data = JSON.stringify(data);
        }

        if (csrfHeader && csrfToken) {
            headers = headers ?? {};
            headers[csrfHeader] = csrfToken;
        }

        const options = {
            method
        };

        if(data) options.body = data;
        if(headers) options.headers = headers;

        return new Promise((resolve, reject) => {
        fetch(url, options)
            .then(res => responseType === 'text' ? res.text() : res.json()) // res.json() - JSON / res.text() - 텍스트
            .then(data => resolve(data))
            .catch(err => reject(err));
        });
    }
};