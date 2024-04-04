<template>
  <div >
    <div class="row mt-5" >
      <div v-if="!submitted"> <!-- if문이 참이면 이 div가 보인다. -->
        <div class="col-6 mx-auto">
          <div class="row g-3 align-items-center mb-3">
            <div class="col-3">
              <label htmlFor="dname" class="col-form-label"> Dname </label>
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

          <div class="row g-3 mt-3 mb-3">
            <button class="btn btn-outline-primary ms-2 col" @click="saveDept">저장</button>
          </div>
        </div>
      </div>

      <div v-else>
        <h4>You submitted successfully!</h4>
        <button class="btn btn-success" @click="newDept">돌아가기</button>
      </div>
    </div>
  </div>
</template>
<script>
import DeptService from '@/services/basic/DeptService';
export default {
    data() {
        return {
            dept: {},// 배열이 아니고 하나의 객체만 필요하므로 중괄호. 대괄호는 배열일때 사용
            submitted: false, // 저장버튼클릭시 true로 바뀐다.
        }
    },
    methods: {
        async saveDept(){ // axios함수를 사용할 때는 비동기함수로 만들어야한다.
            try{
            
                let data = {
                    dname: this.dept.dname,
                    loc: this.dept.loc
                }
                if (data.dname == null || data.loc == null) {
                    return;            
                }              
                else{
                    let response = await DeptService.create(data); // 왜 this dept로 하지 않는가. 
                console.log(response)
                this.submitted = true;
                }
               
                // 백엔드로 객체 추가 요청
            }catch(e){
                    console.log(e)
            }
        },
        newDept(){
            // 뷰는 변수값을 조작하면 화면이 자동 갱신됨. 이것을 이용해서 제출후 저장화면으로 이동이 가능하다.
            this.submitted = false; // false를 해줌으로 초기화면으로 돌아간다. 이걸false로 바꿔주는 순간 화면이 갱신된다.
            this.dept = {}; // dept도 아무것도 없는 상태로 초기화 시켜준다.
        }
    },
};
</script>
<style></style>
