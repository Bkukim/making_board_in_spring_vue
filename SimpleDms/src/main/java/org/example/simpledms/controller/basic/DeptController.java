package org.example.simpledms.controller.basic;

import lombok.extern.slf4j.Slf4j;
import org.example.simpledms.model.entity.basic.Dept;
import org.example.simpledms.service.basic.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : org.example.simpledms.controller.basic
 * fileName : DeptController
 * author : PC
 * date : 2024-04-02
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-02         PC          최초 생성
 */
@Slf4j
@RestController // 일반 컨트롤러는 return값이 jsp파일이었던것과  다르게 리턴값이 json데이터이다.
@RequestMapping("/api/basic")
public class DeptController {


    private final DeptService deptService;

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/dept")
    public ResponseEntity<Object> findAll(// 프론트와 신호를 보낼 수 있게 해주는 타입이다. 어떤 자료형이 들어올지 모르니 Object로 해준다. 얘는 객체의 조상이라서 다 받아준다.
            @RequestParam(defaultValue = "") String dname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Dept> pageList = deptService.findAllByDnameContaning(dname, pageable);
            Map<String, Object> response = new HashMap<>();// jsp에서는 model을 이용해서 키와 밸류를 프론트로 보내주었는데, 이번에는 그렇게 하지 못하니 Map을 이용하여 키 밸류로 만들어 보내주자.
            response.put("dept", pageList.getContent()); // 부서 정보
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
    @PostMapping("/dept")
    public ResponseEntity<Object> create(@RequestBody Dept dept){// jsp 에서는 @ModelAtrribute 를 사용했지만, vue 에서는 @RequestBody 를 사용해서 객체를 받아들인다.
        try {
            Dept dept2 = deptService.save(dept);

                return new ResponseEntity<>(dept2, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dept/{dno}")
    public ResponseEntity<Object> findByDno(@PathVariable int dno){

        try {
            Optional<Dept> dept = deptService.findById(dno);
            if (dept == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(dept,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/dept/{dno}")
    public ResponseEntity<Object> update(@PathVariable int dno,
                                         @RequestBody Dept dept){
        try {
                Dept dept1 = deptService.save(dept);
                return new ResponseEntity<>(dept1, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/dept/deletion/{dno}")
    public ResponseEntity<Object> delete(@PathVariable int dno) {
        try {
            boolean success =  deptService.deleteById(dno);
            if (success) {
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
