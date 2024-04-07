import http from "@/utils/http-common";

class FileDbSerivce{
    getAll(fileTitle,page,size){
        return http.get(`/advanced/fileDb?fileTitle=${fileTitle}&page=${page}&size=${size}`)
    }

    save(fileDb, image){
        let formData = new FormData();
        formData.append("fileTitle", fileDb.fileTitle);
        formData.append("fileContent", fileDb.fileContent);
        formData.append("image", image);
        return http.post("/advanced/fileDb/upload", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          });
        
        /* json파일로 통신할 때는 항상 생략되지만 멀티파트 파일을 보낼때는 json파일이 아니므로 이렇게 보내야한다. */
    }
    get(uuid){
        return http.get(`/advanced/fileDb/get/${uuid}`)
    }
    update(fileDb, image){
      let formData = new FormData();
      formData.append("fileTitle", fileDb.fileTitle);
      formData.append("fileContent", fileDb.fileContent);
      formData.append("image", image);

      return http.put(`/advanced/fileDb/update/${fileDb.uuid}`,formData,{
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      
    }
    delete(uuid){
        return http.delete(`/advanced/fileDb/delete/${uuid}`);
    }
}

export default new FileDbSerivce;