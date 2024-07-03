package com.yupi.onlineojbackendjudgeservice.controller.inner;

import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import com.yupi.onlineojbackendjudgeservice.judge.JudgeService;
import com.yupi.onlineojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {


    @Resource
    private JudgeService judgeService;


    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") Long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }
}
