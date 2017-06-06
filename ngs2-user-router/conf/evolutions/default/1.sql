# Router schema
# --- !Ups

CREATE TABLE Experiment (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    actual_url varchar(255) NOT NULL,
    shorten_url varchar(255) NOT NULL,
    number_participants int(5) NOT NULL,
    status varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE User (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  gallup_id bigint(20) NOT NULL,
  randomized_id bigint(20) NOT NULL,
  arrival_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE UserExperiment_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  FOREIGN KEY (user_id) REFERENCES User(id),
  FOREIGN KEY (experiment_id) REFERENCES Experiment(id),
  arrival_time TIMESTAMP NOT NULL,
  send_off_time TIMESTAMP NOT NULL,
  wait_time int(50) NOT NULL
);

