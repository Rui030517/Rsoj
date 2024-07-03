package com.yupi.onlineojbackendserviceclient.service;


import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 判题服务
 */
@FeignClient(name = "onlineoj-backend-judge-service",path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") Long questionSubmitId);
}
