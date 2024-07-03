<template>
  <div id="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view></router-view>
    </template>
    <template v-else>
      <BasicLayout />
    </template>
  </div>
</template>

<style>
</style>

<script setup>
import BasicLayout from './layouts/BasicLayout.vue'
import { useRoute } from 'vue-router';
import { onMounted, computed, ref } from "vue";
import { useStore } from "vuex";
const store = useStore();
const route = useRoute();
const theme = ref();



const doInit = () => {
  console.log("全局初始化函数");
};

onMounted(() => {
  doInit();
  theme.value = localStorage.getItem("theme");
  if (theme.value === "dark") {
    store.commit("theme/toggleTheme", "dark");
  } else {
    store.commit("theme/toggleTheme", "light");
  }
});


</script>



