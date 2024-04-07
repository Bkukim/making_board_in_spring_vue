import http from "@/utils/http-common";

class GAlleryService{
    getAll(fileTitle,page,size){
        return http.get(`/advanced/gallery?fileTite=${fileTitle}&page=${page}&size=${size}`)
    }
    save(gallery, image){
        let formData = new FormData();
        formData.append("galleryTitle", gallery.galleryTitle);
        formData.append("image", image);
        return http.post("/advanced/gallery/upload", formData,{
            headers : {
                "Content-Type": "multipart/form-data",
            }
        });
    }
    get(uuid){
        return http.get(`/advanced/gallery/get/${uuid}`)
    }
    update(gallery, image){
        let formData = new FormData;
        formData.append("galleryTitle", gallery.galleryTitle);
        formData.append("image", image);
        return http.put(`/advanced/gallery/update/${gallery.uuid}`, formData,{
            headers : {
                "Content-Type": "multipart/form-data",
            }
        });
    }
    delete(uuid){
        return http.delete(`/advanced/gallery/delete/${uuid}`);
    }
}

export default new GAlleryService;