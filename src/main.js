import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'bootstrap/dist/css/bootstrap.min.css'
import './assets/main.css'

import $ from 'jquery'
import Popper from 'popper.js'
import 'bootstrap'

window.$ = window.jQuery = $
window.Popper = Popper

createApp(App).use(router).mount('#app')
