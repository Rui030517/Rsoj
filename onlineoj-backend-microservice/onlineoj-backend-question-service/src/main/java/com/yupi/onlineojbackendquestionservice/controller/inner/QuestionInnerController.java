package com.yupi.onlineojbackendquestionservice.controller.inner;

import com.yupi.onlineojbackendcommon.model.entity.Question;
import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import com.yupi.onlineojbackendquestionservice.service.QuestionService;
import com.yupi.onlineojbackendquestionservice.service.QuestionSubmitService;
import com.yupi.onlineojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;


    /**
     * 根据题目id获取题目
     * @param questionId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") Long questionId){
        return  questionService.getById(questionId);
    }

    /**
     * 根据题目id获取题目提交信息
     * @param questionSubmitId
     * @return
     */
    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId){
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 根据题目提交实体更新题目提交状态
     * @param questionSubmit
     * @return
     */
    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

}
