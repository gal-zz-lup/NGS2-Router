# Requirements / Specs of NGS2-Router

## Email Participants
* Router should be able to generate URL(s) with an invitation to participate for the experiment. Back-end will separate users into multiple experiments and keep track of this information is a database structure. 

* Phase one of the implementation will support a mechanism to load list of users with their emails address along with `Gallup Panel ID` and router will generate necessary URL(s) for the experiments.

## URL Structure
* Content of the URL must have server(root of the application), experiment bucket id, experiment id, client id

## Ability to check minimum participant
* Create a timer (one minute window) to check if any given time router contains minimum participants needed to route them into an experiment.

## FIFO Queue
* To avoid starvation system should always give priority to time that the participant arrives to the router. Necessary pool of participants are selected randomly but given the time arrive as a pre-condition for the selection. 

## Database Structure
* Router functionalities are maintain in a database supported by three tables with following structure.

|admin               |
|--------------------|
|id                  |
|authentication_token|
|email               |
|sha_password        |

|experiment      |
|----------------|
|id              |
|experiment_name |
|created_time    |

|experiment_instance     |
|------------------------|
|id                      |
|experiment_id           |
|experiment_instance_name|
|experiment_url_actual   |
|experiment_url_short    |
|n_participants          |
|status                  |
|priority                |
|created_time            |

|user_info         |
|------------------|
|id                |
|gallup_id         |
|randomized_id     |
|last_arrival_time |
|status            |
|language          |

|user_info_experiment_instance|
|-----------------------------|
|id                           |
|user_info_id                 |
|experiment_instance_id       |
|arrival_time                 |
|send_off_time                |

* Wait time can be calculated by the difference between arrival time and send off time.
* When user arrives to the router we log the arrival time of the user in the `user_info` table and when user get send off to an experiment the arrival time for the `user_info` table get copied to the `user_experiment` table's `arrival_time`. Along with that we log time the user was sent to an experiment along with the time that user waited from the arrival. 
* The participants should be assigned to experiments based on priority level or randomly given equal priorities.

