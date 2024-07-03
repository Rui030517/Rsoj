<template>

    <a-row id="gobalHeader" style="" align="center" :wrap="false">
     
        <a-col flex="auto">
            <a-menu mode="horizontal" :selected-keys="selectedKey" @menu-item-click="doMenuClick">
                <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px' }" disabled>
                
                    <div class="title-bar">
                        <img src="../assets/屏幕截图 2024-06-05 231925.png" class="logo">
                        <div class="title">R OJ</div>
                    </div>
                </a-menu-item>
                <a-menu-item style="font-size: 20px;" v-for="item in visibleRouters" :key="item.path">
                    {{ item.name }}
                </a-menu-item>
            </a-menu>
        </a-col>

        <a-col flex="100px">
        <div class="user">
            {{ store.state.user?.loginUser?.userName ?? "未登录" }}
        </div>
        </a-col>
    </a-row>

</template>

<script setup>
import { routes } from "../router/routes";
import { useRouter, useRoute } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import ACCESS_ENUM from "../access/accessEnum";
import checkAccess from "../access/checkAccess";

const router = useRouter();
const store = useStore();

// setTimeout( () => {
//     store.dispatch("user/getLoginUser",{
//         userName:"ruiZZ",
//         userRole:ACCESS_ENUM.ADMIN,
//     }) 
// },3000)


const loginUser = store.state.user.loginUser;

const visibleRouters = computed(() => {  
    // console.log(loginUser);
        return  routes.filter((item) => {
                if(item.meta?.IsHideOrNo){
                    return false;
                }
                // if(!checkAccess(loginUser,item?.meta?.access)){
                //     return false;
                // }
                //  这里一个坑 千万不能写loginUser 因为loginUser是在computed实时计算函数外定义的，当用户重新更新是不会改变loginUser的值
                // 可以把loginUser = store.state.user.loginUser写在computed里面或者直接引用store.state.user.loginUser
                if(!checkAccess(store.state.user.loginUser,item?.meta?.access)){
                    return false;
                }
                return true;
            });
    });
 

// //过滤掉隐藏的路由，选出需要展示在导航栏的路由
// const visibleRouters = routes.filter((item) => {
//     if(item.meta?.IsHideOrNo){
//         return false;
//     }
//     if(!checkAccess(loginUser,item?.meta?.access)){
//         return false;
//     }
//     return true;
// })


const selectedKey = ref(["/Home"])
router.afterEach((to,from,failure) => {
    selectedKey.value = [to.path]
});


const doMenuClick = (key) =>{
    router.push({
        path: key,
    })
}


</script>


<style scoped>
.title-bar{
    display: flex;
    align-items: center;
}

.title{
    color: #444;
    margin-left: 14px;
    font-size: 20px;
    font-style: italic;
    /* 添加文字阴影效果 */
    text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
    /* 添加文字描边效果 */
    -webkit-text-stroke: 1px black;
}

.user{
    color: #4ea18b;
    margin-left: 14px;
    font-size: 20px;
    font-style: italic;
    /* 添加文字阴影效果 */
    text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
    /* 添加文字描边效果 */
    -webkit-text-stroke: 1px black;
}

.logo{
    height: 40px;
    transform: scale(1.6); /* 通过将比例设置为大于 1 的值来放大图像 */
}
</style>
