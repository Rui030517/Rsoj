import router from "@/router";
import store from "@/store";
import ACCESS_ENUM from "./accessEnum";
import checkAccess from "./checkAccess";

router.beforeEach(async(to,from,next) => {
  console.log("登录用户信息：",store.state.user.loginUser);
    
  let loginUser = store.state.user.loginUser;
  if(!loginUser || !loginUser.userRole){  //如果是没有登陆过的情况下,自动登录
    await store.dispatch("user/getLoginUser");
    loginUser = store.state.user.loginUser;
  } 

  const needAccess = to.meta?.access as string ??ACCESS_ENUM.NOT_LOGIN;
  if(needAccess !== ACCESS_ENUM.NOT_LOGIN){
    if(!loginUser || !loginUser.userRole || loginUser.userRole === ACCESS_ENUM.NOT_LOGIN){
        next(`/user/login?redirect=${to.fullPath}`);
        return ;
    }
  }

  if(!checkAccess(loginUser,needAccess)){
    next("/NoAuth");
    return ;
  }

  next();
})
