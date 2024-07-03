<template>
    <div id="userlayout">
        <a-layout style="min-height: 100vh;">
        <a-layout-header class="header">
            <a-space style="margin-top: 16px;">
                <img src="../assets/屏幕截图 2024-06-05 231925.png" class="logo">
                <div style="font-size: 26px;">R OJ</div>
            </a-space>
        </a-layout-header>
        <a-layout-content class="content">
            <router-view/>
        </a-layout-content>
        <!-- <a-layout-footer class="footer">
            <h2>这是底部</h2>
        </a-layout-footer> -->
    </a-layout>
    </div>

</template>

<script>

</script>


<style scoped>
#userlayout{
    text-align: center;
    background: url("https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png") no-repeat;
    background-size: cover;
}
 
#userlayout .logo{
    width: 100px;
    height: 84px;

}

#userlayout .header{
    /* border-bottom: 1px solid #ff69b4; */
    margin-bottom: 10px;
    /* box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);  */
}

#userlayout .content{
    /* background : linear-gradient(to right, #bbb,#fff); */
    margin-bottom: 16px;
    padding-top: 36px;
}

#userlayout .footer{
    /* background: #efefef; */
    /* padding: 10px; */
    position: sticky;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
}
</style>
