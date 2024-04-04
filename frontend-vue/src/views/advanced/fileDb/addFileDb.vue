<template>
    <div>
      <div class="col-6 mx-auto">
        <!-- {/* 이미지명(fileTitle) 입력 박스 시작 */} -->
        <div class="row g-3 align-items-center mb-3">
          <div class="col-3">
            <label htmlFor="fileTitle" class="form-label"> 이미지명 </label>
          </div>
  
          <div class="col-9">
            <input
              type="text"
              class="form-control"
              id="fileTitle"
              name="fileTitle"
              v-model="fileDb.fileTitle"
            />
          </div>
        </div>
        <!-- {/* 이미지명 입력 박스 끝 */} -->
  
        <!-- {/* 이미지내용 입력 박스 시작 */} -->
        <div class="row g-3 align-items-center mb-3">
          <div class="col-3">
            <label htmlFor="fileContent" class="form-label"> 내용 </label>
          </div>
  
          <div class="col-9">
            <input
              type="text"
              class="form-control"
              id="fileContent"
              name="fileContent"
              v-model="fileDb.fileContent"
            />
          </div>
        </div>
        <!-- {/* 이미지내용 입력 박스 끝 */} -->
  
        <div class="input-group mb-3">
          <!-- {/* upload 선택상자/버튼 start */} -->
          <input type="file" ref="file"/> <!-- type을 파일로 해주어야한다. -->
          <!-- 파일 선택상자는 v-model이 안된다. 그래서 ref="변수명"을 해주어야한다.-->
  
          <button
            class="btn btn-outline-secondary"
            type="button"
            id="inputGroupFileAddon04"
            @click="create"
          >
            Upload
          </button>
        </div>
        <!-- {/* upload 선택상자/버튼 end */} -->
  
        <!-- {/* upload 성공/실패 메세지 출력 시작 */} -->
        <div v-if="message != ''" class="alert alert-success" role="alert">
          {{ message }}
        </div>
        <!-- {/* upload 성공/실패 메세지 출력 끝 */} -->
      </div>
    </div>
  </template>
  <script>
  import FileDbService from '@/services/advanced/FileDbService';
  export default {
      // TODO: 데이터 바인딩 속성 정의
      data() {
          return {
              currentImage: undefined, // 현재이미지
              message: "",             // 성공메세지 변수
              fileDb: {
                  uuid: null,          
                  fileTitle: "",
                  fileContent: "",
                  fileUrl: ""
              },                        // 파일 객체
          }
      },
  
    methods: {
        selectImage(){
            //1) 파일 선택 상자에서 첫 번째로 선택한 이미지를 변수에 저장해야한다.
            // ref="file"로 해놧으므로 file로 접근해야한다.
             this.currentImage = this.$refs.file.file[0]; // 배열의 특징을 가지므로 file0을 해주어야한다. 아니면 배열에서 몇번째를 가져오는 지를 모름
             this.message="";

        },
        async create(){
            try {
              let response = await FileDbService.save(this.fileDb, this.currentImage);
              console.log(response);
              this.message = response.data
            } catch (e) {
              console.log(e);
            }
        }
    },
}
</script>
<style >
    
</style>