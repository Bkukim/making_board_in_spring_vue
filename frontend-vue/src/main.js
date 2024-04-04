import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// TODO: bootstartp vue3 import
import BootstrapVue3 from 'bootstrap-vue-3'
// TODO: bootstartp vue3 css import
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
(1) // TODO: bootstrap import
import 'bootstrap/dist/js/bootstrap.bundle'
import 'bootstrap/dist/css/bootstrap.min.css'
createApp(App)
.use(BootstrapVue3)
.use(store)
.use(router)
.mount('#app')
