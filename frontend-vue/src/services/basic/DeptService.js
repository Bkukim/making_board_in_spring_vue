// crud 공통함수를 만들기 위함이다. 
import http from "@/utils/http-common"; // 이걸 해줘야 스프링과 통신이 가능하다.

class DeptService{
    // 공통함수만 정의 하도록 한다.
    // todo : 전체 조회
    getAll(dname, page/* 현재 페이지 */, size/* 한 블럭당 페이지 갯수 */){
        // 여기서도 조회는 get 방식을 사용한다.
        return http.get(`/basic/dept?dname=${dname}&page=${page}&size=${size}`); // 이 함수가 스프링에 get방식으로 요청을 한다. 
        // spring의 controller의 url을 입력해주면 controller함수가 실행된다. jsp로 치면 form의 action의 역할을 한다.
        // 쿼리 스트링 형태로 보내주면된다. ?와 &를 사용하니 controller에서는 @Pathvariable을 사용할 것이다.
    }

    create(dept){
        return http.post("/basic/dept", dept);// post는 객체를 전달 해야하므로 axios의 post함수는 controller의 url와 객체를 리턴 값으로 전달한다.
    }
    get(dno){
        return http.get(`/basic/dept/${dno}`)
    }
    update(dno, dept){
        return http.put(`/basic/dept/${dno}`, dept);
    }
    delete(dno){
        return http.delete(`basic/dept/deletion/${dno}`);
    }
}

export default new DeptService();