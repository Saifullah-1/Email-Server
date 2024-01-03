import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import mitt from  'mitt'

const emitter = mitt()

let Mail =createApp(App)

Mail.config.globalProperties.emitter = emitter
Mail.use(router)
Mail.mount('#app')
