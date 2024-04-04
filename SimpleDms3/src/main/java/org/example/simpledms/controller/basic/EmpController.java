package org.example.simpledms.controller.basic;

import lombok.extern.slf4j.Slf4j;
import oracle.ucp.proxy.annotation.Post;
import org.example.simpledms.model.entity.basic.Dept;
import org.example.simpledms.model.entity.basic.Emp;
import org.example.simpledms.service.basic.DeptService;
import org.example.simpledms.service.basic.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName : org.example.simpledms.controller.basic
 * fileName : EmpController
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
@RestController
@RequestMapping("/api/basic")
public class EmpController {

    private final EmpService empService;

    @Autowired
    public EmpController(EmpService empService) {
        this.empService = empService;
    }

    @GetMapping("/emp")
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "") String ename,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3")int size){
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Emp> pageList = empService.findByEnameContaning(ename, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("emp", pageList.getContent()); // 부서 정보
            response.put("currentPage", pageList.getNumber()); // 현재페이지 번호
            response.put("totalItems", pageList.getTotalElements()); // 전체데이터 개수
            response.put("totalPages", pageList.getTotalPages()); // 전체 페이지수

            if (pageList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }catch (Exception e){
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
