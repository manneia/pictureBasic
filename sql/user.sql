-- picture_basic_backend

create table if not exists user_basic
(
    id            bigint                                 not null primary key comment '用户id',
    userAccount   varchar(256)                           not null comment '用户账号',
    userPassword  varchar(512)                           not null comment '用户密码',
    userName      varchar(256)                           null comment '用户姓名',
    userAvatar    varchar(1024)                          null comment '用户头像',
    userProfile   varchar(512)                           null comment '用户简介',
    userRole      varchar(256) default 'user'            null comment '用户角色: user/admin',
    vipExpireTime datetime                               null comment '会员过期时间',
    vipCode       varchar(128)                           null comment '会员兑换码',
    vipNumber     bigint                                 null comment '会员编号',
    shareCode     varchar(20)  DEFAULT NULL COMMENT '分享码',
    inviteUser    bigint       DEFAULT NULL COMMENT '邀请用户 id',
    editTime      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除: 0-未删除, 1-已删除',
    index idx_userName (userName),
    unique key uk_userAccount (userAccount)
)
    comment '用户基础信息表' collate = utf8mb4_unicode_ci;