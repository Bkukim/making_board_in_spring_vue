<template>
  <!-- 5) -->
  <div v-if="dept">
    <div class="col-6 mx-auto mt-5">
      <div>
        <div class="row g-3 align-items-center mb-3">
          <div class="col-3">
            <label htmlFor="dname" class="col-form-label" > Dname </label>
          </div>

          <div class="col-9">
            <input
              type="text"
              id="dname"
              required
              class="form-control"
              placeholder="dname"
              name="dname"
              v-model="dept.dname"
            />
          </div>
        </div>

        <div class="row g-3 align-items-center mb-3">
          <div class="col-3">
            <label htmlFor="loc" class="col-form-label"> Loc </label>
          </div>

          <div class="col-9">
            <input
              type="text"
              id="loc"
              required
              class="form-control"
              placeholder="loc"
              name="loc"
              v-model="dept.loc"
            />
          </div>
        </div>
      </div>

      <div class="row g-3 mt-3 mb-3">
        <button class="btn btn-outline-danger ms-3 col" @click="deleteDept">
          Delete
        </button>

        <button
          type="submit"
          class="btn btn-outline-success ms-2 col"
          @click="updateDept"
        >
          Update
        </button>
      </div>

      <p v-if="message !=''" class="alert alert-success mt-3 text-center">
        {{ message }}
      </p>
    </div>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Dept...</p>
  </div>
</template>
<script>
import DeptService from '@/services/basic/DeptService';
export default {
    data() {
        return {
            dept: null,
            message:"", // 수정 성공시 화면 성공 메세지 출력하는 변수
        }
    },
    methods: {
        // 상세 조회 함수
        async getDept(dno){ // dno는 어디서 나온 값? == mounted에서 getDept가 실행된때 이 페이지의 url의 dno를 받는다. 
            try {
                let response = await DeptService.get(dno);
                this.dept = response.data;
                console.log(response);
            } catch (e) {
                console.log(e)
            }
        },
        updateDept(){

        },
        deleteDept(){

        }

    },
    mounted() {
        this.message = "";
        this.getDept(this.$route.params.dno); // 주소에서 변수 가져오기 방법이다. this.$route.params.변수명을 해줘서 이 url인 '/dept/:dno'에서 dno를 사용한다는 뜻
    },
};
</script>
<style lang=""></style>
