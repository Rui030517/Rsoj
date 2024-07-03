package com.yupi.onlineojcodesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeReponse {

    private List<String> output;

    private String message;

    private Integer status;

    private JudgeInfo judgeInfo;
}
