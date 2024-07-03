package com.yupi.onlineojbackendquestionservice.service.impl;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.onlineojbackendcommon.common.ErrorCode;
import com.yupi.onlineojbackendcommon.constant.CommonConstant;
import com.yupi.onlineojbackendcommon.exception.BusinessException;
import com.yupi.onlineojbackendcommon.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.onlineojbackendcommon.model.dto.questionsubmit.QuestionSubmitQueryAddRequest;
import com.yupi.onlineojbackendcommon.model.entity.Question;
import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import com.yupi.onlineojbackendcommon.model.entity.User;
import com.yupi.onlineojbackendcommon.model.enums.QuestionSubmitEnum;
import com.yupi.onlineojbackendcommon.model.enums.QuestionSubmitJudgeStatusEnum;
import com.yupi.onlineojbackendcommon.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.onlineojbackendcommon.model.vo.QuestionSubmitVO;
import com.yupi.onlineojbackendcommon.utils.SqlUtils;
import com.yupi.onlineojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.yupi.onlineojbackendquestionservice.rabbitMQ.MyMessageProducer;
import com.yupi.onlineojbackendquestionservice.service.QuestionSubmitService;
import com.yupi.onlineojbackendserviceclient.service.JudgeFeignClient;
import com.yupi.onlineojbackendserviceclient.service.QuestionFeignClient;
import com.yupi.onlineojbackendserviceclient.service.UserFeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author Rui
* @description 针对表【question_submit】的数据库操作Service实现
* @createDate 2024-06-10 15:03:46
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements QuestionSubmitService {

    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;
    @Resource
    private MyMessageProducer myMessageProducer;



    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long  doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 取出用户所用的语言，判断是否有这种编程语言
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(enumByValue == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误！");
        }

        Question question = questionFeignClient.getQuestionById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交
        long userId = loginUser.getId();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(question.getId());
        questionSubmit.setUserId(userId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        // TODO 初始化题目提交状态
        questionSubmit.setStatus(QuestionSubmitEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");

        boolean result = this.save(questionSubmit);
        if(! result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }

        Long questionSubmitId = questionSubmit.getId();
        //发送消息
        myMessageProducer.sendMessage("code_exchange","my_routingKey", String.valueOf(questionSubmitId));

        //异步执行判题策略
        //        CompletableFuture.runAsync(()->{
//            judgeFeignClient.doJudge(questionSubmitId);
//        });

        return questionSubmitId;
    }


    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryAddRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryAddRequest questionSubmitQueryAddRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryAddRequest == null) {
            return queryWrapper;
        }

        Long questionId = questionSubmitQueryAddRequest.getQuestionId();
        Long userId = questionSubmitQueryAddRequest.getUserId();
        Integer status = questionSubmitQueryAddRequest.getStatus();
        String language = questionSubmitQueryAddRequest.getLanguage();
        String sortField = questionSubmitQueryAddRequest.getSortField();
        String sortOrder = questionSubmitQueryAddRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);

        // 空指针安全处理
        if (status != null) {
            queryWrapper.eq(QuestionSubmitJudgeStatusEnum.getEnumByValue(status.toString()) != null, "status", status);
        }

        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        return queryWrapper;
    }



    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);  //question类转化为questionVO类

        Long userId = loginUser.getId();

        if(!userFeignClient.isAdmin(loginUser) && questionSubmit.getUserId() != userId){  //若不是题目提交者也不是管理员那么对信息脱敏
            questionSubmitVO.setCode(null);
            questionSubmitVO.setJudgeInfo(null);
        }

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }

        List<QuestionSubmitVO> questionSubmitVOS = questionSubmitList.stream().map(questionSubmit -> {
            return getQuestionSubmitVO(questionSubmit, loginUser);
        }).collect(Collectors.toList());

        questionSubmitVOPage.setRecords(questionSubmitVOS);
        return questionSubmitVOPage;
    }

}




