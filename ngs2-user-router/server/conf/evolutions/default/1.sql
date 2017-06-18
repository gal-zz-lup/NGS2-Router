# Router schema
# --- !Ups

CREATE TABLE admin (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL AUTO_INCREMENT,
    password varchar(255) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE experiment (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    experiment_url varchar(255) NOT NULL,
    n_participants int(5) NOT NULL,
    status varchar(255) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE user_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  gallup_id bigint(20) NOT NULL,
  email varchar(255) NOT NULL,
  language varchat(255) NOT NULL,
  randomized_id bigint(20) NOT NULL,
  arrival_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE experiment_user_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_info_id bigint(20) NOT NULL,
  FOREIGN KEY (user_info_id) REFERENCES user_info(id),
  experiment_id bigint(20) NOT NULL,
  FOREIGN KEY (experiment_id) REFERENCES experiment(id),
  arrival_time TIMESTAMP NOT NULL,
  send_off_time TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE experiment;
DROP TABLE user_info;
DROP TABLE experiment_user_info;
DROP TABLE admin