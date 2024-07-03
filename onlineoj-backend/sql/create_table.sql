
-- 创建库
create database if not exists onlineoj;

-- 切换库
use onlineoj;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 题目表
CREATE TABLE IF NOT EXISTS `question` (
    `id` BIGINT AUTO_INCREMENT COMMENT 'id' PRIMARY KEY,
    `title` VARCHAR(512) NULL COMMENT '标题',
    `content` TEXT NULL COMMENT '内容',
    `tags` VARCHAR(1024) NULL COMMENT '标签列表（json数组）',
    `answer` TEXT NULL COMMENT '题目答案',
    `submitNum` INT DEFAULT 0 NOT NULL COMMENT '题目提交数',
    `acceptedNum` INT DEFAULT 0 NOT NULL COMMENT '题目通过数',
    `judgeCase` TEXT NULL COMMENT '判题用例（json数组）',
    `judgeConfig` TEXT NULL COMMENT '判题配置（json对象）',
    `thumbNum` INT DEFAULT 0 NOT NULL COMMENT '点赞数',
    `favourNum` INT DEFAULT 0 NOT NULL COMMENT '收藏数',
    `userId` BIGINT NOT NULL COMMENT '创建用户 id',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete` TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
    INDEX `idx_userId` (`userId`)
) COMMENT '题目' COLLATE = utf8mb4_unicode_ci;

-- 题目提交表
CREATE TABLE IF NOT EXISTS `question_submit` (
    `id` BIGINT AUTO_INCREMENT COMMENT 'id' PRIMARY KEY,
    `language` VARCHAR(128) NOT NULL COMMENT '编程语言',
    `code` TEXT NOT NULL COMMENT '用户代码',
    `judgeInfo` TEXT NULL COMMENT '判题信息（json对象）',
    `status` INT DEFAULT 0 NOT NULL COMMENT '判题状态（0-待判题、1-判题中、2-成功、3-失败）',
    `questionId` BIGINT NOT NULL COMMENT '题目id',
    `userId` BIGINT NOT NULL COMMENT '创建用户id',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete` TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除',
    INDEX `idx_questionId` (`questionId`),
    INDEX `idx_userId` (`userId`)
)comment '题目提交';


-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';
