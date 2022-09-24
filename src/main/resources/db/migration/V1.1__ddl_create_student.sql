CREATE TABLE IF NOT EXISTS `student` (
    `id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name`  VARCHAR(50)  NOT NULL,
    `phone_number`  VARCHAR(50)  NOT NULL,
    `email`      VARCHAR(50)  NOT NULL,
    `status`     VARCHAR(50),
    `reference`  VARCHAR(50),
    `price_reduction`  DOUBLE,
    `price_paid` DOUBLE,
    `comment`    VARCHAR(100),
    `date_added` DATETIME not null ,
    `last_modified`    DATETIME not null ,
    `deleted`    BOOLEAN not null ,
    `student_course`    INT not null
);