package com.community.sjy.web.controller;
import com.community.sjy.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final UserService userService;

    @PostMapping("/uploadImage")
    public ResponseEntity<Object> uploadFiles(MultipartFile multipartFiles,String imageName) { // 파라미터의 이름은 client의 formData key값과 동일해야함

        String UPLOAD_PATH = "C:\\workspace\\springbootwork\\SJY\\SJY-frontend\\src\\image\\ContestImage"; // 공모전 이미지 업로드 위치


        try {
            System.out.println(imageName.startsWith("P"));
            System.out.println("imageName= " + imageName);
            System.out.println("multipartFile= " + multipartFiles);

                MultipartFile file = multipartFiles;
                System.out.println("file= " + file);
                String fileId = imageName;
                long fileSize = file.getSize(); // 파일 사이즈

                File fileSave = new File(UPLOAD_PATH, fileId); // ex) fileId.jpg
                if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
                    fileSave.mkdirs();
                }

                file.transferTo(fileSave); // fileSave의 형태로 파일 저장
                System.out.println("fileId= " + fileId);
                System.out.println("fileSize= " + fileSize);

        } catch(IOException e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Object>("Success", HttpStatus.OK);
    }


    @PostMapping("/uploadImage/profile")
    public ResponseEntity<Object> uploadprofileimage(MultipartFile multipartFiles,String imageName,String username) { // 파라미터의 이름은 client의 formData key값과 동일해야함

        String UPLOAD_PATH = "C:\\workspace\\springbootwork\\SJY\\SJY-frontend\\src\\image\\ProfileImage"; // 공모전 이미지 업로드 위치

        try {
            System.out.println("imageName= " + username);
            System.out.println("imageName= " + imageName);
            System.out.println("multipartFile= " + multipartFiles);

            MultipartFile file = multipartFiles;
            System.out.println("file= " + file);
            String fileId = imageName;
            long fileSize = file.getSize(); // 파일 사이즈
            File fileSave = new File(UPLOAD_PATH, fileId); // ex) fileId.jpg
            if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
                fileSave.mkdirs();
            }

            file.transferTo(fileSave); // fileSave의 형태로 파일 저장
            System.out.println("fileId= " + fileId);
            System.out.println("fileSize= " + fileSize);
            userService.프로필이미지저장(username ,imageName);

        } catch(IOException e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Object>("Success", HttpStatus.OK);
    }


    @PostMapping("/deleteImage/profile")
    public ResponseEntity<Object> deleteprofileimage(String imageName,String username) { // 파라미터의 이름은 client의 formData key값과 동일해야함

        String UPLOAD_PATH = "C:\\workspace\\springbootwork\\SJY\\SJY-frontend\\src\\image\\ProfileImage"; // 공모전 이미지 업로드 위치
        String fileId = imageName;
        File fileDelete = new File(UPLOAD_PATH, fileId);
        if(fileDelete.exists()) { // 파일이 존재하면 삭제
            fileDelete.delete();
        }
        userService.프로필이미지삭제(username);
        return new ResponseEntity<Object>("Success", HttpStatus.OK);
    }
}