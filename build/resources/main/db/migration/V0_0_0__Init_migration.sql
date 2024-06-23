CREATE TABLE department
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255) NULL,
    local VARCHAR(255) NULL,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    version       BIGINT NULL,
    name          VARCHAR(255) NULL,
    address       VARCHAR(255) NULL,
    phone         VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    birth_date    date NULL,
    department_id BIGINT NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    id   BIGINT NOT NULL,
    name SMALLINT NULL,
    CONSTRAINT pk_userrole PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);