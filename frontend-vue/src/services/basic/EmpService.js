import http from "@/utils/http-common";

class EmpService{
    getAll(ename,page,size){
        return http.get(`/basic/emp?ename=${ename}&page=${page}&size=${size}`)
    }
}

export default new EmpService;