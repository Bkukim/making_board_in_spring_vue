package org.example.simpledms.controller.advanced;

import lombok.extern.slf4j.Slf4j;

import org.example.simpledms.model.entity.advanced.FileDb;

import org.example.simpledms.service.advanced.FileDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : org.example.simpledms.controller.advanced
 * fileName : FileDbController
 * author : PC
 * date : 2024-04-04
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-04         PC          최초 생성
 */
@RestController
@RequestMapping("/api/advanced")
@Slf4j
public class FileDbController {

    private final FileDbService fileDbService;
    @Autowired
    public FileDbController(FileDbService fileDbService) {
        this.fileDbService = fileDbService;
    }

    @GetMapping("/fileDb")
    public ResponseEntity<Object> findAll(// 프론트와 신호를 보낼 수 있게 해주는 타입이다. 어떤 자료형이 들어올지 모르니 Object로 해준다. 얘는 객체의 조상이라서 다 받아준다.
                                          @RequestParam(defaultValue = "") String fileTitle,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<FileDb> pageList = fileDbService.findAllByFileTitleContaining(fileTitle, pageable);
            Map<String, Object> response = new HashMap<>();// jsp에서는 model을 이용해서 키와 밸류를 프론트로 보내주었는데, 이번에는 그렇게 하지 못하니 Map을 이용하여 키 밸류로 만들어 보내주자.
            response.put("fileDb", pageList.getContent()); // 부서 정보
            response.put("currentPage", pageList.getNumber()); // 현재페이지 번호
            response.put("totalItems", pageList.getTotalElements()); // 전체데이터 개수
            response.put("totalPages", pageList.getTotalPages()); // 전체 페이지수

            // 1) pageList 값이 없으면 : dB 테이블 없음 => NO_CONTENT(203) 신호를 보낸다.(203)
            // 2) pageList 값이 있으면 : 성공 => OK(200)
            if (pageList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 에러가 나면 이신호를 웹브라우저에 보내어 준다.
            // 프로그램의 품질이 증가된다.
            // 이걸해주면 에러가 나도 신호가 오기에 에러원인을 알 수있다.
            // INTERNAL_SERVER_ERROR(500) 백엔드 서버 에러
        }

    }
    @GetMapping("/fileDb/{uuid}")
    public ResponseEntity<byte[]> findByIdDownloading(@PathVariable String uuid) {
        FileDb fileDb = fileDbService.findById(uuid).get();

        return ResponseEntity.ok()
//           Todo : attachment: => attachment;
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDb.getFileName() + "\"")
                .body(fileDb.getFileData());
    }
    @PostMapping("/fileDb/upload")
    public ResponseEntity<Object> uploadFileDb(@RequestParam(defaultValue = "") String fileTitle,
                                               @RequestParam(defaultValue = "") String fileContent,
                                               @RequestParam MultipartFile image){
        try {
                fileDbService.save(null, fileTitle, fileContent, image);
            return new ResponseEntity<>("업로드 성공", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("업로드시 에러 발생",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fileDb/get/{uuid}")
    public ResponseEntity<Object> getFileDb(@PathVariable String uuid){
        try {
            Optional<FileDb> fileDb = fileDbService.findById(uuid);
            if (fileDb == null) {
                return new ResponseEntity<>("파일이 존재하지 않음", HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(fileDb, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("문제발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/fileDb/update/{uuid}")
    public ResponseEntity<Object> updateFileDb(@PathVariable String uuid,
                                               @RequestParam(defaultValue = "") String fileTitle,
                                               @RequestParam(defaultValue = "") String fileContent,
                                               @RequestParam MultipartFile image){
        try {
            FileDb fileDb = fileDbService.save(uuid, fileTitle, fileContent, image);
            if (fileDb == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>("수정 성공", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("fileDb/delete/{uuid}")
    public ResponseEntity<Object> deleteFileDb(@PathVariable String uuid){
        try {
            fileDbService.removeById(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
