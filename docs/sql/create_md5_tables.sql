-- 部门数据 MD5 校验表
CREATE TABLE IF NOT EXISTS `t_dept` (
    `id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `org_id`  VARCHAR(64)  NOT NULL COMMENT '部门唯一标识',
    `md5`     VARCHAR(32)  NOT NULL COMMENT '报文content的MD5值',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_org_id` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门同步数据MD5校验表';

-- 用户数据 MD5 校验表
CREATE TABLE IF NOT EXISTS `t_user` (
    `id`       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `emp_num`  VARCHAR(64)  NOT NULL COMMENT '用户唯一标识(工号)',
    `md5`      VARCHAR(32)  NOT NULL COMMENT '报文content的MD5值',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_emp_num` (`emp_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户同步数据MD5校验表';
