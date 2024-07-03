// import { createRouter, createWebHistory } from "vue-router";

// const routes = [
//   {
//     path: "/",
//     name: "Home",
//     component: () => import(/* webpackChunkName: "home" */ "../views/HomeView.vue")
//   },
//   {
//     path: "/about",
//     name: "About",
//     component: () => import(/* webpackChunkName: "about" */ "../views/AboutView.vue")
//   }
// ];

// const router = createRouter({
//   history: createWebHistory(process.env.BASE_URL),
//   routes
// });

// export default router;


import { createRouter, createWebHistory } from "vue-router";
import { routes } from "@/router/routes";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
