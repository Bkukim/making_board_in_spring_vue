import { createRouter, createWebHistory } from 'vue-router'

// 메뉴와 url을 연결해주는 작업
const routes = [

  {
    path: '/',
    component: () => import("../views/basic/dept/DeptList.vue")
  },
  {
    
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
