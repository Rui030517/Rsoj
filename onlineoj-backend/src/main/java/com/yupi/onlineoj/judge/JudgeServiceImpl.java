package com.yupi.onlineoj.judge;


import cn.hutool.json.JSONUtil;
import com.yupi.onlineoj.common.ErrorCode;
import com.yupi.onlineoj.exception.BusinessException;
import com.yupi.onlineoj.judge.codesandbox.CodeSandBox;
import com.yupi.onlineoj.judge.codesandbox.CodeSandBoxFactory;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeReponse;
import com.yupi.onlineoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.onlineoj.judge.strategy.JudgeContext;
import com.yupi.onlineoj.judge.strategy.JudgeManage;
import com.yupi.onlineoj.model.dto.question.JudgeCase;
import com.yupi.onlineoj.judge.codesandbox.model.JudgeInfo;
import com.yupi.onlineoj.model.entity.Question;
import com.yupi.onlineoj.model.entity.QuestionSubmit;
import com.yupi.onlineoj.model.enums.QuestionSubmitEnum;
import com.yupi.onlineoj.service.QuestionService;
import com.yupi.onlineoj.service.QuestionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService{

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionService questionService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private JudgeManage judgeManage;

    /**
     * 判题服务实现
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {

        // 1) 接受传进的id --> 根据传入的id获取题目的信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交信息不存在");
        }
        Question question = questionService.getById(questionSubmit.getQuestionId());
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }

        // 2) 更改题目的判题状态为判题中，防止重复执行
        //若题目已在判题中
        if(Objects.equals(questionSubmit.getStatus(), QuestionSubmitEnum.RUNNING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目已在判题中");
        }
        // 判题状态（0-待判题、1-判题中、2-成功、3-失败）
        QuestionSubmit questionSubmitUpdate = questionSubmitService.getById(questionSubmitId);
//        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.RUNNING.getValue());
        boolean suc = questionSubmitService.updateById(questionSubmitUpdate);
        if(!suc){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }


        // 3) 调用沙箱 编译执行 得到执行结果
        //使用工厂模式，根据用户传入的字符串来生成对应的代码沙箱实现类
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCase = question.getJudgeCase();
        //得到判题用例列表
        List<JudgeCase> list = JSONUtil.toList(judgeCase, JudgeCase.class);
        //得到判题用例的输入用例列表
        List<String> inputList = list.stream()
                .map(JudgeCase::getInput)
                .collect(Collectors.toList());

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .input(inputList)
                .language(language)
                .build();

        ExecuteCodeReponse executeCodeReponse = codeSandBox.ExecuteCode(executeCodeRequest);
//        System.out.println("沙箱执行响应：" + executeCodeReponse);

        // 4) 根据代码沙箱执行结果更新题目提交结果状态(判题)

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInput(inputList);
        judgeContext.setJudgeInfo(executeCodeReponse.getJudgeInfo());
        judgeContext.setOutput(executeCodeReponse.getOutput());
        judgeContext.setJudgeCaseList(list);
        judgeContext.setQuestion(question);
        judgeContext.setLanguage(language);
        System.out.println("传入专门判题策略的：" + judgeContext);
        //交给判题策略单独实现
        JudgeInfo judgeInfo = judgeManage.doJudge(judgeContext);
        System.out.println("hahahahhaha");

//        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        suc = questionSubmitService.updateById(questionSubmitUpdate);
        if(!suc){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目提交状态更新失败");
        }

        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        System.out.println("返回的结果：" + questionSubmitResult);
        return questionSubmitResult;
    }
}
