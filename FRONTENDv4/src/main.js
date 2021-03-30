import Vue from 'vue'
import App from './App.vue'
import router from "./router";


import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)


Vue.config.productionTip = false

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css'

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
