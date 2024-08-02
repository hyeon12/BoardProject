package org.hyeon.file.services;

import lombok.RequiredArgsConstructor;
import org.hyeon.file.entities.FileInfo;
import org.hyeon.file.repositories.FileInfoRepository;
import org.hyeon.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileInfoRepository fileInfoRepository;
    private final FileProperties properties;

    public List<FileInfo> upload(MultipartFile[] files, String gid, String location){
        // 파일 업로드 시 필수 요소
        /**
         * 1. 파일 정보 저장
         * 2. 파일을 서버로 이동
         * 3. 이미지이면 썸네일 생성
         * 4. 업로드한 파일목록 반환
         */
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        List<FileInfo> uploadedFiles = new ArrayList<>();

        // 1. 파일 정보 저장
        for (MultipartFile file : files){
            String fileName = file.getOriginalFilename(); // 업로드 파일 원래 이름
            String contentType = file.getContentType(); // 파일 형식
            String extension = fileName.substring(fileName.lastIndexOf("."));

            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .contentType(contentType)
                    .extension(extension)
                    .build();
            fileInfoRepository.saveAndFlush(fileInfo);

            // 2. 파일을 서버로 이동 (10개씩 디렉토리에 저장)
            long seq = fileInfo.getSeq();
            String uploadDir = properties.getPath() + "/" + (seq % 10L);
            File dir = new File(uploadDir);
            if(!dir.exists() && !dir.isDirectory()){
                dir.mkdir(); // 10개로 분산한 폴더 없으면 생성
            }

            String uploadPath = uploadDir + "/" + seq + extension;
            try {
                file.transferTo(new File(uploadPath));

                uploadedFiles.add(fileInfo); // 업로드 성공 파일

            } catch (IOException e){
                e.printStackTrace();
                //파일 업로드 중에 오류 발생 시 삭제!
                //파일 이동 실패시 정보 삭제하는 것
                fileInfoRepository.delete(fileInfo);
                fileInfoRepository.flush();
            }
        }
        return uploadedFiles;
    }

}