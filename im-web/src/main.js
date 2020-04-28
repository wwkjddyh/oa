// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/icon/iconfont.css'
import './assets/css/common.css'

Vue.config.productionTip = false

Vue.use(ElementUI)
if (window.location.protocol == 'https') {
  window.location.href = JSON.parse(JSON.stringify(window.location.href.replace('https','http')));
}

console.log('修改后 === ',window.location);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
