
CREATE TABLE students (
    id NUMBER PRIMARY KEY,
    first_name VARCHAR2(50),
    last_name VARCHAR2(50),
    username VARCHAR2(50) UNIQUE,
    password VARCHAR2(100),
    city VARCHAR2(50),
    email VARCHAR2(100),
    mobile VARCHAR2(15)
);

CREATE SEQUENCE student_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER trg_students_id
BEFORE INSERT ON students
FOR EACH ROW
BEGIN
  :NEW.id := student_seq.NEXTVAL;
END;


CREATE TABLE questions (
    
    question_text Varchar2(200)
    option_a VARCHAR(60), 
    option_b VARCHAR(60),
    option_c VARCHAR(60),
    option_d VARCHAR(60),
    correct_option Number(35),
    
);

CREATE TABLE results (
    id INT,
    FOREIGN KEY (id) REFERENCES students(id),
    score int
);