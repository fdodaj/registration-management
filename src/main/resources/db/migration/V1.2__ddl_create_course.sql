
CREATE TABLE IF NOT EXISTS course (
    id             INTEGER  PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    status         VARCHAR(50),
    start_date     timestamp not null ,
    end_date       timestamp not null ,
    date_added     timestamp not null ,
    last_modified  timestamp not null ,
    deleted        BOOLEAN not null
);