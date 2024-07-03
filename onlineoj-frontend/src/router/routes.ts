import { RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AdminView from "../views/AdminView.vue";
import NoAuthView from "../views/NoAuthView.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import UserLayout from "../layouts/UserLayout.vue";
import UserLoginView from "../views/user/UserLoginView.vue";
import UserRegisterView from "../views/user/UserRegisterView.vue";
import AddQuestionView from "@/views/question/AddQuestionView.vue";
import ManageQuestionView from "@/views/question/ManageQuestionView.vue";
import QuestionsView from "../views/question/QuestionsView.vue"
import WorkQuestionView from "@/views/question/WorkQuestionView.vue";
import QuestionsSubmitListView from "../views/question/QuestionSubmitListView.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path:"/user",
    name:"用户",
    component:UserLayout,
    meta :{
      IsHideOrNo:true,
    },
    children:[
        {
          path: "/user/login",
          name: "用户登录",
          component: UserLoginView,
        },
        {
          path: "/user/register",
          name: "用户注册",
          component: UserRegisterView,
        },
    ]
  },
  {
    path: "/Home",
    name: "主页",
    component: HomeView,
  },
  {
    path: "/questions",
    name: "浏览题目",
    component: QuestionsView,
  },
  {
    path: "/questions_submit",
    name: "题目提交列表",
    component: QuestionsSubmitListView,
    
  },
  {
    path: "/work/question/:id",
    name: "做题页面",
    component: WorkQuestionView,
    props:true,
    meta: {
      access:ACCESS_ENUM.USER,
      IsHideOrNo:true,
    }
  },
  {
    path: "/add/question",
    name: "创建题目",
    component: AddQuestionView,
    meta: {
      access:ACCESS_ENUM.ADMIN,
      IsHideOrNo:true,
    }
  },
  {
    path: "/updata/question",
    name: "更新题目",
    component: AddQuestionView,
    meta: {
      access:ACCESS_ENUM.USER,
      IsHideOrNo:true,
    }
  },
  {
    path: "/manage/question",
    name: "管理题目",
    component: ManageQuestionView,
    meta: {
      access:ACCESS_ENUM.ADMIN,
    }
  },
  // {
  //   path: "/Hide",
  //   name: "隐藏路由",
  //   component: HomeView,
  //   meta :{
  //     IsHideOrNo:true,
  //   },
  // },
  {
    path: "/NoAuth",
    name: "没有权限",
    component: NoAuthView,
    meta :{
      IsHideOrNo:true,
    },
  },
  // {
  //   path: "/admin",
  //   name: "仅管理员可见",
  //   component: AdminView,
  //   meta: {
  //     access:ACCESS_ENUM.ADMIN,
  //   }
  // },
  // {
  //   path: "/About",
  //   name: "关于我的",
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  // },
];
