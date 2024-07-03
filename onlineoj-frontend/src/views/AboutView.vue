<template>
    <div class="about">
        {{ value }}
        <CodeEditor :value="value" :handle-change="onChange" />
    </div>
</template>

<script setup lang="ts">
import CodeEditor from "../components/CodeEditor.vue";
import { ref, withDefaults, defineProps} from "vue";

const value = ref();


const onChange = (v: string) =>{
    value.value = v;
    console.log(value);
}

</script>

<style scoped></style>