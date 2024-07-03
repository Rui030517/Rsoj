<template>
    <div id="basiclayout">
        <a-layout style="min-height: 100vh;">
        <a-layout-header class="header">
            <GobalHeader/>
        </a-layout-header>
        <a-layout-content class="content">
            <router-view/>
        </a-layout-content>
        <a-layout-footer class="footer">
            <p class="footer-into">© 2024 Rui</p>
        </a-layout-footer>
      </a-layout>
    </div>

</template>

<script>

import GobalHeader from '../components/GobalHeader.vue'

export default{
  components : {
    GobalHeader,
  },
};

</script>


<style scoped>
#basiclayout{

}

#basiclayout .header{
    border-bottom: 1px solid #ff69b4;
    margin-bottom: 10px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); 
}

#basiclayout .content{
    background : linear-gradient(to right, #fefefe,#fff);
    margin-bottom: 16px;
    padding-top: 16px;
    margin-left: 32px;
    margin-right: 32px;
}

#basiclayout .footer{
    background: #efefef;
    /* padding: 10px; */
    position: sticky;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
    max-height: 50px;
}

#basicLayout .footer {
  padding: 6px;
  margin-top: auto;
  text-align: center;
  position: relative;
}

#basicLayout .footer .footer-info {
  /* © 2023 Eureka */
  color: var(--color-text-1);
  font-family: 思源黑体;
  font-size: 10px;
  font-weight: 400;
  line-height: 23px;
  letter-spacing: 0px;
}
</style>

