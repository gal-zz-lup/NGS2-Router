# Router schema
# --- !Ups

CREATE TABLE admin (
    id bigint(20) AUTO_INCREMENT NOT NULL,
    authentication_token VARCHAR (255) NULL,
    email varchar(255) NOT NULL,
    sha_password varbinary(64) NOT NULL,
    CONSTRAINT uq_admin_user_email UNIQUE (email),
    CONSTRAINT pk_admin PRIMARY KEY (id)
);

CREATE TABLE experiment (
    id bigint(20) AUTO_INCREMENT NOT NULL,
    experiment_name VARCHAR (255) NOT NULL,
    CONSTRAINT pk_experiment PRIMARY KEY (id)
);

CREATE TABLE experiment_instance (
  id bigint(20) AUTO_INCREMENT NOT NULL,
  experiment_id bigint(20) NOT NULL,
  FOREIGN KEY (experiment_id) REFERENCES experiment(id),
  experiment_instance_name VARCHAR (255) NOT NULL,
  experiment_instance_url_actual VARCHAR(255) NOT NULL,
  experiment_instance_url_short VARCHAR(255) NOT NULL,
  n_participants INT(5) UNSIGNED NOT NULL,
  status varchar(255) NOT NULL,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  priority INT(5) UNSIGNED NOT NULL,
  CONSTRAINT pk_experiment_instance PRIMARY KEY (id)
);


CREATE TABLE user_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  gallup_id VARCHAR(255) NOT NULL,
  language VARCHAR(16) NOT NULL,
  randomized_id VARCHAR(255) NOT NULL,
  arrival_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(255) NOT NULL,
  sample_group VARCHAR(255) NOT NULL,
  CONSTRAINT pk_user_info PRIMARY KEY (id)
);

CREATE TABLE user_info_experiment_instance (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_info_id bigint(20) NOT NULL,
  FOREIGN KEY (user_info_id) REFERENCES user_info(id),
  experiment_instance_id bigint(20) NOT NULL,
  FOREIGN KEY (experiment_instance_id) REFERENCES experiment_instance(id),
  arrival_time TIMESTAMP NOT NULL,
  send_off_time TIMESTAMP NOT NULL,
  CONSTRAINT pk_experiment_user_info PRIMARY KEY (id)
);

SELECT count(*)
FROM user_info ui
WHERE ui.status = 'WAITING'
AND ui.id NOT IN (SELECT uiei.user_info_id FROM user_info_experiment_instance uiei
WHERE uiei.experiment_instance_id in (SELECT ei.id FROM experiment_instance ei WHERE ei.experiment_id = '234'));


# --- !Downs
DROP TABLE experiment;
DROP TABLE experiment_instance;
DROP TABLE user_info;
DROP TABLE user_info_experiment_instance;
DROP TABLE admin;