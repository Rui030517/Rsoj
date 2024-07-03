package com.yupi.onlineojbackendcommon.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.onlineojbackendcommon.model.codesandbox.JudgeInfo;
import com.yupi.onlineojbackendcommon.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类，返回给前端的
 * @TableName question
 */
@TableName(value ="question_submit")
@Data
public class QuestionSubmitVO implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0-待判题、1-判题中、2-成功、3-失败）
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    private UserVO userVO;
    private QuestionVO questionVO;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * VO包装类转实体对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfo1 = questionSubmitVO.getJudgeInfo();

        if (judgeInfo1 != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo1));
        }


        return questionSubmit;
    }

    /**
     * 实体对象转VO包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);

        String judgeInfo1 = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfo1,JudgeInfo.class));

        return questionSubmitVO;
    }
}