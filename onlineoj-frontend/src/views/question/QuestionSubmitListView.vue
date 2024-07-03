<!-- <template>
    <div id="questionsSubmitView">

        <a-form :model="serchParms" layout="inline">
            <a-form-item field="questionId" label="题号">
                <a-input v-model="serchParms.questionId" style="min-width: 240px;" placeholder="查找题目id对应提交记录" />
            </a-form-item>
            
            <a-form-item field="language" label="编程语言" style="min-width: 240px;">
                <a-select :style="{width:'320px'}" v-model="serchParms.language" placeholder="根据编程语言分类">
                    <a-option>java</a-option>
                    <a-option>cpp</a-option>
                    <a-option>go</a-option>
                </a-select>
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
        <template #judgeConfig="{ record }">
                <a-space>
                    {{ JSON.stringify(record.judgeInfo) }}
                </a-space>
            </template>
            <template #createTime="{ record }">
                <a-space>
                    {{ moment(record.createTime).format('YYYY-MM-DD') }}
                </a-space>
            </template>
        </a-table>
        
    </div>
</template>

<script setup lang="ts">
import { Message } from "@arco-design/web-vue";
import { QuestionControllerService, QuestionQueryRequest, QuestionSubmitQueryAddRequest } from "../../../generated";
import { onMounted, ref, watchEffect } from "vue"
import { QuestionVO } from "../../../generated";
import { useRouter } from "vue-router";
import moment from "moment"

const router = useRouter();
const datalist = ref([]);
const total = ref(0);
const serchParms = ref<QuestionSubmitQueryAddRequest>({
    questionId: undefined,
    language: undefined,
    pageSize: 10,
    current: 1, 
});

const loadData = async() => {
    const res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(serchParms.value);
    if(res.code === 0){
        datalist.value = res.data.records;
        total.value = res.data.total;
        console.log("题目提交列表信息：",res.data);  
        console.log(datalist.value);  
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
    title: '提交号',
    dataIndex: 'id',
    },{
    title: '题目id',
    dataIndex: 'questionId',
    }, {
    title: '提交用户id',
    dataIndex: 'userId',
    },{
    title: '编程语言',
    dataIndex: 'language',
    }, 
    {
    title: '判题信息',
    slotName: 'judgeInfo',
    },
    {
    title: '判题结果',
    dataIndex: 'status',
    },{
    title: '提交时间',
    slotName: 'createTime'
    } 
];
  
    

</script>

<style scoped>
#questionsSubmitView{

}
</style>
 -->



<template>
    <div id="questionsSubmitView">

        <a-form :model="serchParms" layout="inline">
            <a-form-item field="questionId" label="题号">
                <a-input v-model="serchParms.questionId" style="min-width: 240px;" placeholder="查找题目id对应提交记录" />
            </a-form-item>
            
            <a-form-item field="language" label="编程语言" style="min-width: 240px;">
                <a-select :style="{width:'320px'}" v-model="serchParms.language" placeholder="根据编程语言分类">
                    <a-option>java</a-option>
                    <a-option>cpp</a-option>
                    <a-option>go</a-option>
                </a-select>
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
        <template #judgeConfig="{ record }">
                <a-space>
                    {{ JSON.stringify(record.judgeInfo) }}
                </a-space>
            </template>
            <template #createTime="{ record }">
                <a-space>
                    {{ moment(record.createTime).format('YYYY-MM-DD') }}
                </a-space>
            </template>
        </a-table>
        
    </div>
</template>


<script setup lang="ts">
import { Message } from "@arco-design/web-vue";
import { QuestionControllerService } from "../../../generated";
import { ref,onMounted } from "vue";
import { useRouter } from "vue-router";
import moment from "moment";

const router = useRouter();
const datalist = ref([]);
const total = ref(0);

const serchParms = ref({
    questionId: null,
    language: null,
    pageSize: 10,
    current: 1,
});

const loadData = async () => {
    try {
        const res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(serchParms.value);
        if (res.code === 0) {
            datalist.value = res.data.records;
            total.value = res.data.total;
            console.log("题目提交列表信息：", res.data);
            console.log(datalist.value);
        } else {
            Message.error(`获取题目信息失败: ${res.message}`);
        }
    } catch (error) {
        Message.error("请求失败，请稍后重试");
        console.error("加载数据时出错:", error);
    }
};

const doSerch = () => {
    loadData();
};

const toQuestionPage = (id: number) => {
    router.push(`/work/question/${id}`);
};

const doPageChange = (page: number) => {
    serchParms.value = {
        ...serchParms.value,
        current: page,
    };
};

onMounted(() => {
    serchParms.value.current = 1;
    loadData();
});

const columns = [
    {
        title: '提交号',
        dataIndex: 'id',
    },
    {
        title: '题目id',
        dataIndex: 'questionId',
    },
    {
        title: '提交用户id',
        dataIndex: 'userId',
    },
    {
        title: '编程语言',
        dataIndex: 'language',
    },
    {
        title: '判题信息',
        slotName: 'judgeInfo',
    },
    {
        title: '判题结果',
        dataIndex: 'status',
    },
    {
        title: '提交时间',
        slotName: 'createTime',
    }
];
</script>
