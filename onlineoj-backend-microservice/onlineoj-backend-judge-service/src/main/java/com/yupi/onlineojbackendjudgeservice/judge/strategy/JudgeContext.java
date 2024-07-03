package com.yupi.onlineojbackendjudgeservice.judge.strategy;


import com.yupi.onlineojbackendcommon.model.codesandbox.JudgeInfo;
import com.yupi.onlineojbackendcommon.model.dto.question.JudgeCase;
import com.yupi.onlineojbackendcommon.model.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * 判题上下文（用于传递判题策略中的所需要的信息）
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> input;

    private List<String> output;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private String language;
}
