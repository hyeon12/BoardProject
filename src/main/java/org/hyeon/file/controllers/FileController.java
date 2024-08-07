package org.hyeon.file.controllers;

import lombok.RequiredArgsConstructor;
import org.hyeon.file.entities.FileInfo;
import org.hyeon.file.services.FileDeleteService;
import org.hyeon.file.services.FileDownloadService;
import org.hyeon.file.services.FileInfoService;
import org.hyeon.file.services.FileUploadService;
import org.hyeon.global.exceptions.RestExceptionProcessor;
import org.hyeon.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements RestExceptionProcessor {

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileInfoService infoService;
    private final FileDeleteService deleteService;

    @PostMapping("upload")
    public ResponseEntity<JSONData> upload(@RequestPart("file") MultipartFile[] files,
                                           @RequestParam(name="gid", required = false) String gid,
                                           @RequestParam(name="location", required = false) String location){

        List<FileInfo> items = uploadService.upload(files, gid, location);
        HttpStatus status = HttpStatus.CREATED;
        JSONData data = new JSONData(items);
        data.setStatus(status);

        return ResponseEntity.status(status).body(data);
    }

    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq){
        downloadService.download(seq);
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq){
        FileInfo data = deleteService.delete(seq);

        return new JSONData(data); //JSON 형태로 삭제된 파일 정보
    }

    @DeleteMapping("/deleteS/{gid}")
    public JSONData deleteS(@PathVariable("gid") String gid, @RequestParam(name="location", required=false) String location){ // gid = 필수 / location = 추가적인 데이터 (RequestParam)
        List<FileInfo> items = deleteService.delete(gid, location);

        return new JSONData(items); //JSON 형태로 삭제된 파일 정보
    }

    @GetMapping("/info/{seq}") //개별 조회
    public JSONData get(@PathVariable("seq") Long seq){
        FileInfo data = infoService.get(seq);

        return new JSONData(data);
    }

    @GetMapping("/list/{gid}") //목록 조회
    public JSONData getList(@PathVariable("gid") String gid, @RequestParam(name="location", required=false) String location){ //location 필수 X
        List<FileInfo> items = infoService.getList(gid, location);

        return new JSONData(items);
    }

}
