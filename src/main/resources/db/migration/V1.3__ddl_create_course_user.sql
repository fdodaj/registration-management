CREATE TABLE course_user
(
    course_id     INTEGER   NOT NULL,
    user_id       INTEGER   NOT NULL,
    created_date  timestamp not null,
    modified_date timestamp not null,
    deleted       BOOLEAN   not null,
    status        VARCHAR(50) NOT NULL,
    reference     VARCHAR(50),
    price_reduction  INTEGER,
    price_paid      INTEGER,
    comment     VARCHAR(50),
    PRIMARY KEY (course_id, user_id)
);