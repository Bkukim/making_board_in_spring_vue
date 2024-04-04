import http from "@/utils/http-common";

class GAlleryService{
    getAll(fileTitle,page,size){
        return http.get(`/advanced/gallery?fileTite=${fileTitle}&page=${page}&size=${size}`)
    }
}

export default new GAlleryService;