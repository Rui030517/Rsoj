package com.yupi.onlineojbackendcommon.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.onlineojbackendcommon.model.dto.question.JudgeCase;
import com.yupi.onlineojbackendcommon.model.dto.question.JudgeConfig;
import com.yupi.onlineojbackendcommon.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目封装类，返回给前端的
 * @TableName question
 */
@TableName(value ="question")
@Data
public class QuestionVO implements Serializable {

    private final static Gson GSON = new Gson();
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题用例（json对象）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /*
    * 题目创建人信息
    * */
    private UserVO userVO;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * VO包装类转实体对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(GSON.toJson(tagList));
        }
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if(judgeConfig != null){
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }

        return question;
    }

    /**
     * 实体对象转VO包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(GSON.fromJson(question.getTags(), new TypeToken<List<String>>() {
        }.getType()));
        String judgeConfig = question.getJudgeConfig();
        questionVO.setJudgeConfig(GSON.fromJson(judgeConfig, new TypeToken<JudgeConfig>() {
        }.getType()));

        return questionVO;
    }
}