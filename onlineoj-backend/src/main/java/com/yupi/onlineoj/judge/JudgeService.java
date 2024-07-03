package com.yupi.onlineoj.judge;

import com.yupi.onlineoj.model.entity.QuestionSubmit;
import com.yupi.onlineoj.model.vo.QuestionSubmitVO;

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
