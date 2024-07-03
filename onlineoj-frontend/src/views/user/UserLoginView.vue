<template>
   <div id="userLayout">
      <h2>用户登录</h2>
      <a-form :model="form" size="large" label-align="left" auto-label-width style="max-width: 480px; margin: 0 auto;" @submit="handleSubmit">
         <a-form-item field="userAccount"  label="UserName">
            <a-input v-model="form.userAccount" placeholder="请输入您的用户名"/>
         </a-form-item>
         <a-form-item field="userPassword" tooltip="密码长度不能低于6位..." label="PassWord">
            <a-input-password v-model="form.userPassword" placeholder="请输入您的密码" />
         </a-form-item>
         <!-- <a-form-item field="isRead">
            <a-checkbox v-model="form.isRead">我已阅读用户协议</a-checkbox>
         </a-form-item> -->
         <a-form-item>
            <a-button type="primary" status="success" html-type="submit">登录</a-button>
         </a-form-item>
      </a-form>
   </div>
</template>


<script>
import { reactive } from 'vue';
import { UserControllerService, UserLoginRequest} from "../../../generated"
import message from "@arco-design/web-vue/es/message"
import { useRouter } from "vue-router"
import { useStore } from "vuex"

export default {
  setup() {
    const form = reactive({
      userAccount: '',
      userPassword: '',
      // isRead: false,
    } );

    const router = useRouter();
    const store = useStore();

    const handleSubmit = async() => {
      const res = await UserControllerService.userLoginUsingPost(form);
      if(res.code === 0){
         // alert(JSON.stringify(res.data));
         store.dispatch("user/getLoginUser");
         // console.log(store.state.user.loginUser);
         router.push({
            path:"/Home",
            replace:true,  //不会占用浏览器历史记录的堆栈，点回退就不会再回到登录页面
         })
      }
      else {
         message.error(res.message);
      }
    };

    return {
      form,
      handleSubmit,
    };
  },
};
</script>


<style scoped>
</style>