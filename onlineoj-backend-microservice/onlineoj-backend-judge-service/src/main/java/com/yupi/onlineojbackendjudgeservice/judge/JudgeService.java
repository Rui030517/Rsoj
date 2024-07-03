package com.yupi.onlineojbackendjudgeservice.judge;


import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(Long questionSubmitId);
}
