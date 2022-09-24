
CREATE TABLE IF NOT EXISTS `course` (
    `id`             INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `name`           VARCHAR(50) NOT NULL,
    `status`         VARCHAR(50),
    `start_date`     DATETIME not null ,
    `end_date`       DATETIME not null ,
    `date_added`     DATETIME not null ,
    `last_modified`  DATETIME not null ,
    `deleted`        BOOLEAN not null
);