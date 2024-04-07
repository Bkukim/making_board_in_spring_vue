// FileDbDetail.vue // vueInit
<template>
  <div class="col-6 mx-auto">
    <!-- {/* 이미지명(fileTitle) 입력 박스 시작 */} -->
    <div class="mb-3 col-md-12">
      <label htmlFor="galleryTitle" class="form-label"> 이미지명 </label>
      <input
        type="text"
        class="form-control"
        id="galleryTitle"
        required
        name="galleryTitle"
        v-model="gallery.galleryTitle"
      />
    </div>
    <!-- {/* 이미지명 입력 박스 끝 */} -->

    <!-- {/* 이미지내용 입력 박스 시작 */} -->
    <div class="mb-3 col-md-12">
      <label htmlFor="galleryContent" class="form-label"> 내용 </label>
      <input
        type="text"
        class="form-control"
        id="galleryContent"
        required
        name="galleryContent"
        v-model="gallery.galleryContent"

      />
    </div>

    <!-- {/* 이미지내용 입력 박스 끝 */} -->
    <div class="mb-3 col-md-12">
      <img :src="gallery.galleryFileUrl" class="card-img-top" alt="강의" />
    </div>

    <!-- {/* upload 선택상자/버튼 start */} -->
    <div class="input-group mb-3">
      <!-- {/* upload 선택상자/버튼 start */} -->
      <label class="btn btn-default p-0 mb-3">
        <input type="file" ref="file" @change="selectImage"/>
      </label>

      <button class="btn btn-success mb-3" @click="update">Update</button>
    </div>
    <!-- {/* upload 선택상자/버튼 end */} -->

    <!-- {/* upload 성공/실패 메세지 출력 시작 */} -->
    <div v-if="message" class="alert alert-success" role="alert">
      {{ message }}
    </div>
    <!-- {/* upload 성공/실패 메세지 출력 끝 */} -->
  </div>
</template>

<script>
import GalleryService from '@/services/advanced/GalleryService';
export default {
    data() {
        return {
            currentImage: undefined,
            message: "", //성공메세지 변수

            gallery: {
                uuid: this.$route.params.uuid,
                galleryTitle:"",
                galleryContent: "",
                galleryFileUrl:""
            }
        }
    },
    methods: {
        async get(uuid){
            try {
                let response = await GalleryService.get(uuid);   
                console.log(response);
                this.gallery=response.data
            } catch (e) {
                console.log(e);
            }
        },
        selectImage(){
          this.currentImage=this.$refs.file.files[0];
        },
        async update(){
         try {
          let response = await GalleryService.update(this.gallery, this.currentImage);
          this.message = response.data;
         } catch (e) {
            console.log(e)
            this.currentImage="";
            this.message="";
         }
        }
    },
    mounted() {
        this.get(this.$route.params.uuid);
        this.message='';
    },
}
</script>
<style >
    
</style>