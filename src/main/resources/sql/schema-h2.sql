-- H2용 DDL

DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    role     VARCHAR(20)  NOT NULL DEFAULT 'USER'
);

CREATE TABLE students (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(20)  NOT NULL UNIQUE,
    name           VARCHAR(50)  NOT NULL,
    grade          VARCHAR(20),
    department     VARCHAR(50),
    phone          VARCHAR(20),
    email          VARCHAR(100),
    status         VARCHAR(10) DEFAULT '재학',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subjects (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject     VARCHAR(50)  NOT NULL,
    department  VARCHAR(50)  NOT NULL,
    professor   VARCHAR(30)  NOT NULL
);

CREATE TABLE subject_grade_weights (
    subject_id          BIGINT PRIMARY KEY,
    attendance_weight   INT NOT NULL DEFAULT 10,
    midterm_weight      INT NOT NULL DEFAULT 30,
    final_weight        INT NOT NULL DEFAULT 40,
    assignment_weight   INT NOT NULL DEFAULT 20,
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
);

CREATE TABLE subject_grades (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id        BIGINT NOT NULL,
    student_id        BIGINT NOT NULL,
    attendance_score  DOUBLE DEFAULT 0,
    midterm_score     DOUBLE DEFAULT 0,
    final_score       DOUBLE DEFAULT 0,
    assignment_score  DOUBLE DEFAULT 0,
    UNIQUE (subject_id, student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(id),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

CREATE TABLE grades (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT      NOT NULL,
    subject    VARCHAR(50) NOT NULL,
    score      INT         NOT NULL CHECK (score >= 0 AND score <= 100),
    semester   VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE
);
