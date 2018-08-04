CREATE TABLE ele.tb_user
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username varchar(50) NOT NULL COMMENT '用户名',
    password varchar(32) NOT NULL COMMENT '密码，加密存储',
    phone varchar(20) COMMENT '注册手机号',
    email varchar(50) COMMENT '注册邮箱',
    created datetime NOT NULL COMMENT '创建时间',
    updated datetime NOT NULL COMMENT '修改时间',
    source_type varchar(1) COMMENT '会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat',
    nick_name varchar(50) COMMENT '昵称',
    name varchar(50) COMMENT '真实姓名',
    status varchar(1) COMMENT '使用状态（Y正常 N非正常）',
    head_pic varchar(150) COMMENT '头像地址',
    qq varchar(20) COMMENT 'QQ号码',
    account_balance decimal(10) COMMENT '账户余额',
    is_mobile_check varchar(1) DEFAULT '0' COMMENT '手机是否验证 （0否  1是）',
    is_email_check varchar(1) DEFAULT '0' COMMENT '邮箱是否检测（0否  1是）',
    sex varchar(1) DEFAULT '0' COMMENT '性别，1男，2女',
    user_level int(11) COMMENT '会员等级',
    points int(11) COMMENT '积分',
    experience_value int(11) COMMENT '经验值',
    birthday datetime COMMENT '生日',
    last_login_time datetime COMMENT '最后登录时间'
);
CREATE UNIQUE INDEX username ON pyg.tb_user (username);
