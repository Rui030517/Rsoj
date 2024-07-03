package com.yupi.onlineoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.onlineoj.model.dto.question.QuestionQueryRequest;
import com.yupi.onlineoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.onlineoj.model.dto.questionsubmit.QuestionSubmitQueryAddRequest;
import com.yupi.onlineoj.model.entity.Question;
import com.yupi.onlineoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.onlineoj.model.entity.User;
import com.yupi.onlineoj.model.vo.QuestionSubmitVO;
import com.yupi.onlineoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Rui
* @description 针对表【question_submit】的数据库操作Service
* @createDate 2024-06-10 15:03:46
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryAddRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryAddRequest questionSubmitQueryAddRequest);


    /**
     * 获取题目提交封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit ,User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
