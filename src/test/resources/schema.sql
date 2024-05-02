CREATE TABLE code_mst(
id INT  NOT NULL ,
version INT,
code CHAR(50),
code_desc CHAR(50),
enabled CHAR(1),
created_by CHAR(50),
created_date TIMESTAMP,
last_modify_by CHAR(50),
last_modify_date TIMESTAMP,
PRIMARY KEY (id)
);
CREATE TABLE code_dtl (
id INT,
version INT,
code CHAR(50),
code_desc CHAR(50),
enabled CHAR(1),
code_mst_id INT,
created_by CHAR(50),
created_date TIMESTAMP,
last_modify_by CHAR(50),
last_modify_date TIMESTAMP,
PRIMARY KEY (id),
CONSTRAINT code_mst_id FOREIGN KEY (code_mst_id) REFERENCES code_mst (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE TABLE user_basic (
id INT,
version INT,
user_name CHAR(10),
account CHAR(20),
password CHAR(80),
birthday DATE,
country CHAR(10),
sex CHAR(1),
created_by CHAR(10),
created_date TIMESTAMP,
last_modify_by CHAR(10),
last_modify_date TIMESTAMP,
authority CHAR(10),
enabled BOOLEAN,
expiry_date DATE,
token CHAR(200),
PRIMARY KEY (id)
);