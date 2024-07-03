package com.yupi.onlineoj.judge.strategy;

import com.yupi.onlineoj.model.dto.question.JudgeCase;
import com.yupi.onlineoj.judge.codesandbox.model.JudgeInfo;
import com.yupi.onlineoj.model.entity.Question;
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
