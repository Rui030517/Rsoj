<!-- <template>
    <div id="addQuestionView">
        <h1 style="text-align: center;" v-if="!updataPage">创建题目</h1>
        <h1 style="text-align: center;" v-if="updataPage">更新题目</h1>
        <a-form :model="form" label-align="left">

            <a-form-item field="title" label="标题">
                <a-input v-model="form.title" style="width: 635px;" placeholder="请输入题目的标题" />
            </a-form-item>
            <a-form-item field="tags" label="标签">
                <a-input-tag  v-model="form.tags" style="width: 635px;" placeholder="请选择题目的标签"  allow-clear/>
            </a-form-item>


            <a-form-item field="content" label="题目内容编辑区">
                <MdEditor :value="form.content" :handle-change="doContentChange" />
            </a-form-item>
            <a-form-item field="answer" label="答案编辑区">
                <MdEditor :value="form.answer" :handle-change="doAnswerChange" />
            </a-form-item>

            <a-form-item label="题目配置" :content-flex="false" >
                <a-space direction="vertical" style="min-width: 480px;">
                    <a-form-item field="form.judgeConfig" label="题目时间限制(S)">
                        <a-input-number :style="{width:'600px'}" v-model="form.judgeConfig.timeLimit" placeholder="请输入题目时间限制" allow-clear hide-button>
                            <template #suffix>
                                <icon-info-circle />
                            </template>
                        </a-input-number>
                    </a-form-item>
                    <a-form-item field="form.judgeConfig" label="题目空间限制(MB)">
                        <a-input-number :style="{width:'600px'}" v-model="form.judgeConfig.memoryLimit" placeholder="请输入题目空间限制" allow-clear hide-button>
                            <template #suffix>
                                <icon-info-circle />
                            </template>
                        </a-input-number>
                    </a-form-item>
                    
                </a-space>
            </a-form-item>

            <a-form-item label="测试用例" :content-flex="false" >        
                <a-form-item v-for="(judgeCaseItem,index) of form.judgeCase" :label="`测试用例${index}`" :key="index" no-style>

                    <a-space direction="vertical" class="oneExample">
                        <a-form-item :field="form.judgeCase[index].input" :label="`输入用例${index +1 }`" :key="index" style="margin-top: 10px; margin-left: 10px;">
                            <a-input style="max-width: 280px;" v-model="judgeCaseItem.input" placeholder="请输入输入用例" />
                        </a-form-item>
                    
                        <a-form-item :field="form.judgeCase[index].output" :label="`输出用例${index +1 }`" :key="index" style="margin-top: 10px; margin-left: 10px;">
                            <a-input style="max-width: 280px;" v-model="judgeCaseItem.output" placeholder="请输入输出用例" />
                        </a-form-item>

                        <a-button type="primary" status="warning" @click="handleDelete(index)" style="margin-bottom: 10px; margin-left: 130px;">删除此测试用例</a-button>
                    </a-space>

                </a-form-item>
                <div style="margin-top: 32px;">
                    <a-button type="outline" status="success" @click="handleAdd">添加测试用例</a-button>
                </div>
            </a-form-item>

           
            <a-form-item style="margin-top: 16px;"> 
                <a-button type="primary" status="success" style="min-width: 200px;" @click="doSubmit">提交</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script setup lang="ts">

import { onMounted, reactive, ref } from "vue";
import MdEditor from "../../components/MdEditor.vue";
import { QuestionControllerService } from "../../../generated";
import message from "@arco-design/web-vue/es/message"
import { useRoute, useRouter } from "vue-router";
import Message from "@arco-design/web-vue/es/message";
import { QuestionAddRequest } from "../../../generated";

const route = useRoute();
const router = useRouter();
const updataPage = route.path.includes("updata");

const localData = async()=>{
    const id = route.query.id;
    if(!id){
        return ;
    }
    const res = await QuestionControllerService.getQuestionByIdUsingGet(id as any);

    if(res.code === 0){
        form.value = res.data as any;
        if(!form.value.judgeConfig){
            form.value.judgeConfig = {
                timeLimit:1,
                memoryLimit:64,
            }
        }else{
            form.value.judgeConfig = JSON.parse(form.value.judgeConfig as any);
        }
        if(!form.value.judgeCase){
            form.value.judgeCase = [{
                input:"",
                output:"",
            }]
        }else{
            form.value.judgeCase = JSON.parse(form.value.judgeCase as any);
        }
        if(!form.value.tags){
            form.value.tags = []
        }else{
            form.value.tags = JSON.parse(form.value.tags as any);
        }
    }
    else{
        Message.error("加载失败"+res.message);
    }
}

onMounted(()=>{
    localData();
});

const form = ref({
    
        "title": "",
        "tags": [
        ],
        "answer": "",
        "content": "",
        "judgeCase": [
            {
            "input": "",
            "output": ""
            }
        ],
        "judgeConfig": {
            "memoryLimit": 64,
            "timeLimit": 1,
        },
    
})

const handleAdd = () => {
      form.value.judgeCase.push({
        input: '',
        output:'',
      })
};

const handleDelete = (index:any) => {
    form.value.judgeCase.splice(index, 1)
}

const doContentChange = (value:string) =>{
    form.value.content = value;
}
const doAnswerChange = (value:string) =>{
    form.value.answer = value;
}

const doSubmit = async() =>{
    // console.log(form);
    if(updataPage){
        const res = await QuestionControllerService.updateQuestionUsingPost(form.value as QuestionAddRequest);
        if(res.code == 0){
            message.success("更新成功");
            router.push({
                path:"/manage/question"
            })
        }
        else{
            message.error("更新失败",res.message);
        }
    }
    else {
        const res = await QuestionControllerService.addQuestionUsingPost(form.value as QuestionAddRequest);
        if(res.code == 0){
            message.success("创建成功");
            // 清空表单值
            form.value = {
                "title": "",
                "tags": [],
                "answer": "",
                "content": "",
                "judgeCase": [
                    {
                        "input": "",
                        "output": ""
                    }
                ],
                "judgeConfig": {
                    "memoryLimit": 64,
                    "timeLimit": 1,
                },
            };
        }
        else{
            message.error("创建失败：",res.message);
        }
    }
}

</script>

<style scoped>
#addQuestionView{

}

.oneExample{
    min-width: 400px; 
    border: 1px black dotted;
    margin-right: 10px;
    margin-top: 10px;
}

</style> -->





<template>
    <div id="AddQuestionView">
      <a-drawer
        :visible="drawerVisible"
        @cancel="CloseDrawer"
        :width="880"
        :footer="false"
      >
        <template #title>
          <div>{{ id ? "更新题目" : "创建题目" }}</div>
        </template>
        <a-form :model="form">
          <a-form-item field="title" label="标题">
            <a-input
              v-model="form.title"
              placeholder="请输入标题"
              class="titleInput"
            />
          </a-form-item>
          <a-form-item field="tags" label="标签">
            <a-input-tag v-model="tags" placeholder="请选择标签" allow-clear />
          </a-form-item>
          <a-form-item field="extent" label="难度">
            <a-select v-model="extent" :style="{ width: '160px' }">
              <a-option>简单</a-option>
              <a-option>中等</a-option>
              <a-option>困难</a-option>
            </a-select>
          </a-form-item>
          <a-form-item field="content" label="题目内容">
            <MdEditor :value="form.content" :handle-change="onContentChange" />
          </a-form-item>
          <a-form-item field="answer" label="答案">
            <MdEditor :value="form.answer" :handle-change="onAnswerChange" />
          </a-form-item>
          <a-divider orientation="center">
            <span class="dividerText">核心代码模式配置区域</span>
          </a-divider>
          <a-form-item field="templateCode" label="核心代码模板">
            <CodeEditor
              :value="form.templateCode"
              :code-default="templateForm.code"
              :language="codeLanguage"
              :handle-change="onTemplateFormChange"
              id="codeEditor"
            />
          </a-form-item>
          <a-form-item field="mergeCode" label="合并代码">
            <CodeEditor
              :value="form.mergeCode"
              :code-default="mergeCodeForm.code"
              :language="codeLanguage"
              :handle-change="onMergeCodeFormChange"
              id="codeEditor"
            />
          </a-form-item>
          <a-divider />
          <a-form-item
            label="判题配置"
            :content-flex="false"
            :merge-props="false"
          >
            <a-space direction="vertical" style="min-width: 480px">
              <a-form-item field="judgeConfig.timeLimit" label="时间限制">
                <a-input-number
                  v-model="form.judgeConfig.timeLimit"
                  placeholder="请输入时间限制"
                  :min="minVal"
                  size="large"
                />
                ms
              </a-form-item>
              <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
                <a-input-number
                  v-model="form.judgeConfig.memoryLimit"
                  placeholder="请输入内存限制"
                  :min="minVal"
                  size="large"
                />
                KB
              </a-form-item>
              <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
                <a-input-number
                  v-model="form.judgeConfig.stackLimit"
                  placeholder="请输入堆栈限制"
                  :min="minVal"
                  size="large"
                />
                KB
              </a-form-item>
            </a-space>
          </a-form-item>
          <a-form-item
            label="判题用例配置"
            tooltip="判题用例的前两条将会作为 测试用例 展示"
            :content-flex="false"
            :merge-props="false"
          >
            <judgmentConfig
              :judgeCase="form.judgeCase"
              :handle-delete="handleDelete"
            />
            <a-button @click="handleAdd" class="addTest">
              <icon-plus :style="{ fontSize: '16px', marginRight: '4px' }" />
              新增测试用例
            </a-button>
          </a-form-item>
          <div style="margin-top: 16px" />
          <a-form-item>
            <a-button
              type="primary"
              style="min-width: 200px"
              @click="doSubmit"
              class="submit"
              >提交
            </a-button>
          </a-form-item>
        </a-form>
      </a-drawer>
    </div>
  </template>
  
  <script setup lang="ts">
  import { IconPlus } from "@arco-design/web-vue/es/icon";
  import MdEditor from "../../components/MdEditor.vue";
  import judgmentConfig from "@/components/addQuestion/JudgmentConfig.vue";
  import { defaultTemplateCode, defaultMergeCode } from "@/config/codeDefault";
  import { Message, Notification } from "@arco-design/web-vue";
  import "@arco-design/web-vue/es/message/style/css.js";
  import "@arco-design/web-vue/es/notification/style/css.js";
  import { QuestionControllerService } from "../../../generated";
  import {
    ref,
    computed,
    onMounted,
    watch,
    toRef,
    defineAsyncComponent,
    withDefaults, defineProps
  } from "vue";
  import { useStore } from "vuex";
  const CodeEditor = defineAsyncComponent(
    () => import("../../components/CodeEditor.vue")
  );
  /**
   *  定义组件属性类型
   */
  interface Props {
    questionId: string;
  }
  /**
   * 给组件指定初始值
   */
  const props = withDefaults(defineProps<Props>(), {
    questionId: () => "",
  });
  interface judgeCase {
    input: string;
    output: string;
  }
  const id = ref();
  watch(toRef(props, "questionId"), (newId) => {
    id.value = newId;
    loadData();
  });
  const store = useStore();
  const extent = ref("简单");
  const tags = ref();
  const form = ref();
  
  const codeLanguage = "java";
  const templateForm = ref({
    code: defaultTemplateCode,
  });
  const mergeCodeForm = ref({
    code: defaultMergeCode,
  });
  const minVal = 0;
  
  // 获取创建题目抽屉显示状态
  const drawerVisible = computed(() => store.state.questionDrawer.drawerVisible);
  /**
   * 根据题目 id 获取老的数据
   */
  const loadData = async () => {
    reset();
    if (!id.value) {
      return;
    }
    const res = await QuestionControllerService.getQuestionByIdUsingGet(
      id.value as any
    );
    if (res.code === 0) {
      form.value = res.data as any;
  
      // json 转 js 对象
      if (!form.value.judgeCase) {
        form.value.judgeCase = [
          {
            input: "",
            output: "",
          },
        ];
      } else {
        form.value.judgeCase = JSON.parse(form.value.judgeCase as any);
      }
      if (!form.value.judgeConfig) {
        form.value.judgeConfig = {
          memoryLimit: 1000,
          stackLimit: 1000,
          timeLimit: 1000,
        };
      } else {
        form.value.judgeConfig = JSON.parse(form.value.judgeConfig as any);
      }
      if (!form.value.tags) {
        form.value.tags = [];
      } else {
        form.value.tags = JSON.parse(form.value.tags as any);
        extent.value = form.value.tags.shift() as any;
        tags.value = form.value.tags;
      }
      if (form.value.templateCode) {
        templateForm.value.code = form.value.templateCode;
      }
      if (form.value.mergeCode) {
        mergeCodeForm.value.code = form.value.mergeCode;
      }
    } else {
      Message.error("加载失败" + res.message);
    }
  };
  
  const reset = () => {
    form.value = {
      title: "",
      tags: [] as string[],
      answer: "",
      content: "",
      judgeConfig: {
        memoryLimit: 1000,
        stackLimit: 1000,
        timeLimit: 1000,
      },
      judgeCase: [
        {
          input: "",
          output: "",
        },
      ],
      templateCode: defaultTemplateCode,
      mergeCode: defaultMergeCode,
    };
    templateForm.value.code = form.value.templateCode;
    mergeCodeForm.value.code = form.value.mergeCode;
    tags.value = [];
  };
  
  onMounted(() => {
    loadData();
  });
  
  const onContentChange = (value: string) => {
    form.value.content = value;
  };
  const onAnswerChange = (value: string) => {
    form.value.answer = value;
  };
  
  const onTemplateFormChange = (v: string) => {
    form.value.templateCode = v;
  };
  const onMergeCodeFormChange = (v: string) => {
    form.value.mergeCode = v;
  };
  
  const handleTags = () => {
    return [extent.value, ...tags.value].filter((v) => v);
  };
  
  const CloseDrawer = () => {
    store.commit("questionDrawer/showDrawerVisible", false);
  };
  /**
   * 判题用例
   */
  const handleAdd = () => {
    form.value.judgeCase.push({
      input: "",
      output: "",
    });
  };
  const handleDelete = (record: judgeCase) => {
    console.log(record);
    form.value.judgeCase.splice(record, 1);
  };
  const verifyJudgeCase = () => {
    return (
      form.value.judgeCase.some(
        (item: judgeCase) => !item.input || !item.output
      ) === false
    );
  };
  
  const doSubmit = async () => {
    form.value.tags = handleTags();
    if (!verifyJudgeCase()) {
      Notification.error({
        title: "提交失败",
        content: "输入用例或输出用例不能为空",
        closable: true,
      });
      return;
    }
    if (id.value) {
      const res = await QuestionControllerService.updateQuestionUsingPost(
        form.value
      );
      if (res.code === 0) {
        Message.success("更新成功");
      } else {
        Message.error("更新失败" + res.message);
      }
    } else {
      const res = await QuestionControllerService.addQuestionUsingPost(
        form.value
      );
      if (res.code === 0) {
        Message.success("创建成功");
      } else {
        Message.error("创建失败" + res.message);
      }
    }
    store.commit("questionDrawer/showDrawerVisible", false);
  };
  </script>
  
  <style scoped>
  #AddQuestionView {
    max-width: 880px;
    margin: auto;
  }
  #codeEditor {
    height: 200px;
    width: 100%;
  }
  .titleInput {
    width: 100%;
  }
  .arco-input-tag {
    width: 100%;
  }
  .arco-input-number {
    margin-right: 12px;
  }
  .addTest {
    width: 100%;
    color: var(--color-text-2);
    background-color: var(--color-fill-2);
    border: 2px dashed var(--color-neutral-3);
  }
  .submit {
    margin-left: auto;
  }
  :deep(
      .arco-list-medium
        .arco-list-content-wrapper
        .arco-list-content
        > .arco-list-item
    ) {
    padding: 5px 15px;
  }
  
  .button-box {
    display: flex;
    justify-content: end;
  }
  .dividerText {
    font-size: 12px;
    color: gray;
  }
  </style>