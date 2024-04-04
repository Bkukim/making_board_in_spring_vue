<template>
  <div>
    <div class="col-md-8 mt-5">
      <!-- {/* 검색어 start */} -->
      <div class="input-group mb-3">
        <input type="text" class="form-control" placeholder="Search by title" v-model="searchTitle"/>
        <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="button"
          @click="retrieveGallery">
            Search
          </button>
        </div>
      </div>
      <!-- {/* 검색어 end */} -->
    </div>

    <div class="col-md-12 mt-3">
      <h4>Gallery List</h4>
      <!-- {/* page start */} -->
      <div class="mb-3">
        Items per Page:
        <select v-model="pageSize" @change="pageSizeChange">
          <option v-for="(data, index) in pageSizes" :key="index" :value="data">{{ data }}</option>
        </select>
      </div>

      <!-- b-pagination : 부트스트랩 - 페이지 번호 컨트롤 -->
      <!-- total-rows : 전체 데이터 개수 -->
      <!-- per-page : 1페이지 당 개수 -->
      <!-- change : handlePageChange(), 페이지 번호 변경 시 실행되는 이벤트 -->
      <b-pagination
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        @click="retrieveGallery"
      ></b-pagination>
      <!-- {/* page end */} -->

      <!-- {/* 쇼핑카트 이미지 start */} -->
      <div class="row">
        <div v-for="(data,index) in fileDb" :key="index" class="col-sm-6">
          <div class="card">
            <!-- 카드 이미지 -->
            <img :src="data.fileUrl" class="card-img-top" alt="강의" />
            <!-- 본문 : 제목 + 내용 -->
            <div class="card-body">
                <!-- 제목 -->
              <h5 class="card-title">{{ data.galleryTitle }}</h5>
                <!-- 내용 -->
              <p class="card-text">{{ data.galleryContent }}</p>
              <router-link :to="'/gallery/' + data.uuid"><span class="badge bg-warning">수정</span></router-link>
              <a
                style="
                   {
                    color: inherit;
                  }
                "
                class="ms-2"
                @click="deleteFileDb(data.uuid)"
              >
                <span class="badge bg-danger">Delete</span>
              </a>
            </div>
          </div>
        </div>
      </div>
      <!-- {/* 쇼핑카트 이미지 end */} -->
    </div>
  </div>
</template>
<script>
import GalleryService from '@/services/advanced/GalleryService.js'
export default {
  data() {
    return {
      gallery: [],
      searchTitle : "",
      page: 1, // 현재 페이지 번호
      count: 4, // 전체 데이터 개수
      pageSize: 3, // 한 페이지당 요소개수

      pageSizes: [3, 6, 9], // 한 페이지당 요소개수 배열
    };
  },
  methods: {
    // 전체조회 함수
    async retrieveGallery(){
        try {
            let response = await GalleryService.getAll(this.searchTitle, this.page -1, this.pageSize);
            const{gallery, totalItems} = response.data;
            this.gallery = gallery;
            this.count = totalItems;
        } catch (e) {
            console.log(e);
        }
    },
    // 삭제 함수
    // deleteFileDb(uuid){},
    // 페이지 공통함수
    // select 박스 변경시 실행될 함수
    pageSizeChange(){
      this.page = 1; // 현재 페이지 번호 초기화
      this.retrieveGallery(); // 재조회 요청
    },
  },
  mounted() {
    this.retrieveGallery();
  },
};
</script>
<style></style>
