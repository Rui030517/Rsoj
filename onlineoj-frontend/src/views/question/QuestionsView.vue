<template>
    <div id="questionsView">

        <a-form :model="serchParms" layout="inline">
            <a-form-item field="title" label="题目名称">
                <a-input v-model="serchParms.title" style="min-width: 240px;" placeholder="请输入要搜索的题目名称" />
            </a-form-item>
            <a-form-item field="tags" label="题目标签">
                <a-input-tag v-model="serchParms.tags" style="min-width: 280px;" placeholder="请输入要搜索的题目标签" />
            </a-form-item>
            
            <a-form-item>
                <a-button type="primary" @click="doSerch()">搜索</a-button>
            </a-form-item>
        </a-form>
        <a-divider :size="0" />

        <a-table :columns="columns" 
        :data="datalist" 
        :pagination="{showTotal:true ,pageSize:serchParms.pageSize,current:serchParms.current,total}"
        @page-change="doPageChange">
            <template #tags="{ record }">
                <a-space>
                    <a-space wrap>
                        <a-tag v-for="(tag, index) of record.tags" :key="index" color="#00b42a" >{{ tag }}</a-tag>
                    </a-space>
                </a-space>
            </template>
            <template #AcRate="{ record }">
                <a-space>
                    {{ `${record.submitNum?record.acceptedNum/record.submitNum*100:"0"}%(${record.acceptedNum}/${record.submitNum})` }}
                </a-space>
            </template>
            <template #createTime="{ record }">
                <a-space>
                    {{ moment(record.createTime).format('YYYY-MM-DD') }}
                </a-space>
            </template>
            <template #optional="{ record }">
                <a-space>
                    <a-button type="outline" status="warning" @click="toQuestionPage(record.id)">去做题</a-button>    
                </a-space>
            </template>
        </a-table>
        
    </div>
</template>

<script setup lang="ts">
import { Message } from "@arco-design/web-vue";
import { QuestionControllerService, QuestionQueryRequest } from "../../../generated";
import { onMounted, ref, watchEffect } from "vue"
import { QuestionVO } from "../../../generated";
import { useRouter } from "vue-router";
import moment from "moment"

const router = useRouter();
const datalist = ref([]);
const total = ref(0);
const serchParms = ref<QuestionQueryRequest>({
    title:'',
    tags:[],
    pageSize: 10,
    current: 1, 
});

const loadData = async() => {
    const res = await QuestionControllerService.listQuestionVoByPageUsingPost(serchParms.value);
    if(res.code === 0){
        datalist.value = res.data.records;
        total.value = res.data.total;
        console.log(res.data);   
    }
    else {
        Message.error("获取题目信息失败",res.message);
    }
}

const doSerch = () =>{
    loadData();
}


const toQuestionPage = (id:number) =>{
    router.push({
        path:`/work/question/${id}`,
    })
}


const doPageChange = (page: number)=> {
    serchParms.value = {
        ...serchParms.value,
        current:page
    };
};

onMounted(() => { 
    serchParms.value = {
        ...serchParms.value,
        current:1,
    }
    loadData();
})     
  
  
const columns = [
    {
    title: '题号',
    dataIndex: 'id',
    }, {
    title: '标题',
    dataIndex: 'title',
    }, {
    title: '标签',
    slotName: 'tags',
    },  {
    title: '提交数',
    dataIndex: 'submitNum'
    },{
    title: '通过数',
    dataIndex: 'acceptedNum'
    },{
    title: 'AC率',
    slotName: 'AcRate'
    },{
    title: '创建时间',
    slotName: 'createTime'
    } ,{
    title: '操作',
    slotName: 'optional' 
    }
];
  
    

</script>

<style scoped>
#questionsView{

}
</style>

