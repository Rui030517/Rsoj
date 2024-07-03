package com.yupi.onlineojbackendserviceclient.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.onlineojbackendcommon.model.dto.question.QuestionQueryRequest;
import com.yupi.onlineojbackendcommon.model.entity.Question;
import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import com.yupi.onlineojbackendcommon.model.vo.QuestionVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
* @author Rui
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-06-10 15:02:55
*/
@FeignClient(name = "onlineoj-backend-question-service",path = "/api/question/inner")
public interface QuestionFeignClient{

    /**
     * 根据题目id获取题目
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") Long questionId);

    /**
     * 根据题目id获取题目提交信息
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId);

    /**
     * 根据题目提交实体更新题目提交状态
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
