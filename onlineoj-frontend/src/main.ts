// 导入Vue库中的createApp函数
import { createApp } from "vue";

// 导入根组件App.vue
import App from "./App.vue";

// 导入Vue Router实例
import router from "./router";

// 导入Vuex Store实例
import store from "./store";

// 导入Arco Design Vue组件库
import ArcoVue from "@arco-design/web-vue";

// 导入Arco Design的CSS样式文件
import "@arco-design/web-vue/dist/arco.css";
import "@/access/index"
import "bytemd/dist/index.css"

// 使用createApp函数创建Vue应用实例，并传入根组件App.vue
createApp(App)
  // 使用Vue Router
  .use(router)
  // 使用Vuex Store
  .use(store)
  // 使用Arco Design Vue组件库
  .use(ArcoVue)
  // 将Vue应用实例挂载到HTML页面上的id为"app"的DOM元素上
  .mount("#app");
