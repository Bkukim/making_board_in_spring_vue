<!-- html 작성 -->
<template lang="">
  <div>
    <!-- 검색어 -->
    <div class="row mb-5 mt-5 justify-content-center"><!-- justify-content-center는 div태그를 가운데 정렬 해주는 속성 --> <!--  밖의 div는 행을 의미 -->
      <!-- 입력박스 -->
      <div class="col-12 w-50 input-group mb-3"><!--  안의 div는 열을 의미, w는 width이고 50은 %이다. -->
        <input
          type="text"
          class="form-control"
          placeholder="부서명 검색"
          name="ename"
          v-model="searchEname" /><!--searchDname은 부서명 검색 변수와 바인딩 할것  -->
        <button
          class="btn btn-outline-secondary"
          type="submit"
          id="button-addon2"
          @click="retrieveEmp"
        >
          검색
        </button>
      </div>
    </div>
    <!-- 페이징 번호 -->
    <div class="row">
        <!-- todo 한 페이지당 화면에 보일 개수 조정 (select태그) -->
        <div class="col-12 w-25 mb-3">
            1페이지당 개수 : 
            <select 
            class="form-select form-select-sm"
            @change="pageSizeChange"
            v-model="pageSize">
                <!-- todo vue반목문 실행 -->
                <option v-for="(data, index) in pageSizes" :key="index" :value="data">{{data}}</option>
              
            </select>
        </div>
      <div>
            <!-- 페이지 번호  -->
            <!-- 현재 페이지는 = page, 한 페이지당 요소 갯수 갯수 = pageSize, 전체 데이터 갯수 = count -->
            <b-pagination
                class="col-12 mb-3"
                v-model="page"
                :total-rows="count"
                :per-page="pageSize"
                @click="retrieveEmp"
            ></b-pagination>
        </div>
    </div>
    
    <!-- 테이블 -->
    <div class="row">
      <div class="col-12">
        <!-- 테이블 디자인 -->
        <table class="table">
          <thead>
            <tr>
              <th scope="col">eno</th>
              <th scope="col">ename</th>
              <th scope="col">job</th>
              <th scope="col">manager</th>
              <th scope="col">hiredate</th>
              <th scope="col">salary</th>
              <th scope="col">commission</th>
              <th scope="col">dno</th>
              <th scope="col">action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(data, index) in emp" :key="index">
              <td>{{data.eno}}</td>
              <td>{{data.ename}}</td>
              <td>{{data.job}}</td>
              <td>{{data.manager}}</td>
              <td>{{data.hiredate}}</td>
              <td>{{data.salary}}</td>
              <td>{{data.commission}}</td>
              <td>{{data.dno}}</td>
              <td>{{data.acton}}</td>
              <td>
                <span class="badge text-bg-success">수정</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<!-- js 작성 부분 -->
<script>
 import EmpService from "@/services/basic/EmpService";

export default {

  data() { // 속성을 가지는 함수. 변수들을 가지는 객체를 리턴한다.
    return {
        emp:[], // 스프링에서 전송할 변수
        searchEname: "", // 부서명검색 변수
        page: 1, // 현재 페이지 번호
        count: 4, // 전체 데이터 개수
        pageSize: 3, // 한 페이지당 요소개수

        pageSizes: [3,6,9] // 한 페이지당 요소개수 배열
    }
  },
  /* vue 함수 정의 : 클릭/더블클릭 등 */
  methods: {// 공통 함수 : 페이징 관련 함수들

    pageNoChange(value){ // 페이지 번호 클릭시 실행될 함수
      // this.속성 => data() 안에 속성들 접근
        this.page = value; // 1) 현재페이지 변경되면
        this.retrieveDept(); // 2) 재조회를 요청하게 만들었다.
    },

    // select 박스 변경시 실행될 함수
    pageSizeChange(){
      this.page = 1; // 현재 페이지 번호 초기화
      this.retrieveEmp(); // 재조회 요청
    },

    // spring의 전체조회 요청함수
    // getAll 전체조회 함수 : 비동기 함수  -> 한번에 다 나눠주고 오는대로 받는것. 속도는 빠른데 먼저준애 순서대로 받을 수 없다.
    // 그래서 해줘야하는게 async 함수
    async retrieveEmp(){
      try{
        // 공통 전체 조회 함수 실행 -> DeptService.js에 만든 함수를 가져올 것이다.
        let response = await EmpService.getAll(this.searchEname, this.page-1/* spring에서는0부터 시작하므로 */, this.pageSize); // axios 사용 promise가 다 있어서 비동기가 가능하다. 
        // const dept = response.data.dept;
        // const count = response.data.totalItems;
        //  객체분할 할당
        const {emp, totalItems} = response.data; // 위 두 코드를 한 줄로 합쳐주는 기능, axios파일은 data변수에 담겨서 이동한다. 
        this.emp = emp;
        this.count = totalItems;

        // response에 뭐가 들어있는지를 확인하기위해 로깅
        console.log(response.data);
        
      }catch(e){
        console.log(e)
      }        
    }
  },
  mounted() {
    // 최초 화면이 뜰때 전체 조회
    this.retrieveEmp();

  },
};
</script>

<!--  css 작성 부분 -->
<style></style>
