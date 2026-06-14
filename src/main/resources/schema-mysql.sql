-- MySQL 生产环境 DDL — 无人机信息管理系统
-- 使用方式：
--   1. 创建数据库：CREATE DATABASE IF NOT EXISTS drone DEFAULT CHARSET utf8mb4;
--   2. 执行本脚本：mysql -u root -p drone < schema-mysql.sql
-- 注意：application-prod.yml 中 initialization-mode=never，不会自动执行本脚本

CREATE TABLE IF NOT EXISTS t_drone (
    id                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    model             VARCHAR(100) NOT NULL                COMMENT '型号',
    manufacturer      VARCHAR(100) NOT NULL                COMMENT '厂商',
    max_flight_time   INT          NULL                     COMMENT '最大飞行时间(分钟)',
    max_range         INT          NULL                     COMMENT '最大航程(米)',
    weight            DECIMAL(8,2) NULL                     COMMENT '重量(kg)',
    payload_capacity  DECIMAL(8,2) NULL                     COMMENT '载重(kg)',
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_drone_model (model),
    INDEX idx_drone_manufacturer (manufacturer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='无人机信息表';
