package com.yupi.onlineoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.onlineoj.common.BaseResponse;
import com.yupi.onlineoj.common.ErrorCode;
import com.yupi.onlineoj.common.ResultUtils;
import com.yupi.onlineoj.exception.BusinessException;
import com.yupi.onlineoj.exception.ThrowUtils;
import com.yupi.onlineoj.model.dto.question.QuestionQueryRequest;
import com.yupi.onlineoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.onlineoj.model.dto.questionsubmit.QuestionSubmitQueryAddRequest;
import com.yupi.onlineoj.model.entity.Question;
import com.yupi.onlineoj.model.entity.QuestionSubmit;
import com.yupi.onlineoj.model.entity.User;
import com.yupi.onlineoj.model.vo.QuestionSubmitVO;
import com.yupi.onlineoj.model.vo.QuestionVO;
import com.yupi.onlineoj.service.QuestionSubmitService;
import com.yupi.onlineoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        final User loginUser = userService.getLoginUser(request);
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交列表（封装类）
     *
     * @param questionSubmitQueryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryAddRequest questionSubmitQueryAddRequest, HttpServletRequest request) {
        long current = questionSubmitQueryAddRequest.getCurrent();
        long size = questionSubmitQueryAddRequest.getPageSize();
        User loginUser = userService.getLoginUser(request);

        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryAddRequest));


        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,loginUser));
    }

}
