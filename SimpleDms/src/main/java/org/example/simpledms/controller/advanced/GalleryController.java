package org.example.simpledms.controller.advanced;

import lombok.extern.slf4j.Slf4j;
import org.example.simpledms.model.entity.advanced.FileDb;
import org.example.simpledms.model.entity.advanced.Gallery;
import org.example.simpledms.service.advanced.FileDbService;
import org.example.simpledms.service.advanced.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : org.example.simpledms.controller.advanced
 * fileName : GalleryController
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
class GalleryController {



    private final GalleryService galleryService;
    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping("/gallery")
    public ResponseEntity<Object> findAll(// 프론트와 신호를 보낼 수 있게 해주는 타입이다. 어떤 자료형이 들어올지 모르니 Object로 해준다. 얘는 객체의 조상이라서 다 받아준다.
                                          @RequestParam(defaultValue = "") String galleryTitle,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Gallery> pageList = galleryService.findAllByGalleryTitleContaining(galleryTitle, pageable);
            Map<String, Object> response = new HashMap<>();// jsp에서는 model을 이용해서 키와 밸류를 프론트로 보내주었는데, 이번에는 그렇게 하지 못하니 Map을 이용하여 키 밸류로 만들어 보내주자.
            response.put("gallery", pageList.getContent()); // 부서 정보
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

     @GetMapping("/gallery/{uuid}")
    public ResponseEntity<byte[]> findByIdDownloading(@PathVariable String uuid) {
        Gallery gallery = galleryService.findById(uuid).get();

        return ResponseEntity.ok()
//           Todo : attachment: => attachment;
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + gallery.getGalleryFileName() + "\"")
                .body(gallery.getGalleryData());
    }

    @PostMapping("/gallery/upload")
    public ResponseEntity<Object> uploadGallery(@RequestParam(defaultValue = "") String galleryTitle,
                                                @RequestParam MultipartFile image) {
        try {

            galleryService.save(null, galleryTitle, image);
            return new ResponseEntity<>("업로드 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gallery/get/{uuid}")
    public ResponseEntity<Object> getGallery(@PathVariable String uuid){
        try {
            Optional<Gallery> gallery = galleryService.findById(uuid);
            if (gallery == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(gallery, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/gallery/update/{uuid}")
    public ResponseEntity<Object> updateGallery(@PathVariable String uuid,
                                                @RequestParam(defaultValue = "") String galleryTitle,
                                                @RequestParam MultipartFile image){
        try {
            Gallery gallery = galleryService.save(uuid, galleryTitle, image);
            if (gallery == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>("수정완료",HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/gallery/delete/{uuid}")
    public ResponseEntity<Object> deleteGallery(@PathVariable String uuid){

        try {
            boolean result = galleryService.deleteById(uuid);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
