package com.yupi.onlineoj.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yupi.onlineoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
@EqualsAndHashCode
public class QuestionSubmitQueryAddRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目提交结果状态
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 提交用户id
     */
    private Long userId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}