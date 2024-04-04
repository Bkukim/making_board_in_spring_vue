import { createRouter, createWebHistory } from 'vue-router'

// 메뉴와 url을 연결해주는 작업
const routes = [
  {
    path: '/',
    alias: "/dept", // 추가 url을 생성할 수 있다. 이렇게 되면 두 url을 실행해도 하나의 페이지가 나온다.
    component: () => import("../views/basic/dept/DeptList.vue")
  },
 {
    path: '/emp',   
    component: () => import("../views/basic/emp/EmpList.vue")
  },
  {
    path: '/add-dept',   
    component: () => import("../views/basic/dept/AddDept.vue")
  },
  {
    path: '/dept/:dno',   
    component: () => import("../views/basic/dept/DeptDetail.vue")
  }, 
  {
    path: '/fileDb',   
    component: () => import("../views/advanced/fileDb/FileDbList.vue")
  },
  {
    path: '/add-fileDb',   
    component: () => import("../views/advanced/fileDb/addFileDb.vue")
  },
  {
    path: '/gallery',   
    component: () => import("../views/advanced/gallery/GalleryList.vue")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
