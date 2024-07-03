import ACCESS_ENUM from "@/access/accessEnum";
import { StoreOptions } from "vuex";
import { UserControllerService } from "../../generated";

export default {
    namespaced : true,
    state :() => ({
        loginUser:{
            userName:"未登录",
            
        }
    
    }),

  mutations :{
    updataUser(state,payload){
          state.loginUser = payload;
        },
    } ,

  //执行异步操作，触发mutations的修改
    actions : {
        async getLoginUser({commit,state},payload){
            const res = await UserControllerService.getLoginUserUsingGet();
            console.log(res);
            
            if(res.code === 0){
                commit("updataUser",res.data);
            }
            else{
                commit("updataUser",{...state.loginUser,userRole:ACCESS_ENUM.NOT_LOGIN});
            }
        }
    },

} as StoreOptions<any>;
