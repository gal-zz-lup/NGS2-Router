# Requirements / Specs of NGS2-Router

## Email Participants
* Router should be able to generate URL(s) with an invitation to participate for the experiment. Backend will seperate users into multiple experiments and keep track of this information is a database structure. 

* Phase one of the implementation will support a mechanism to load list of users with their emails address along with `Gallup Panel ID` and router will generate necessary URL(s) for the experiments.

## URL Structure
* Content of the URL must have server(root of the application), experiment bucket id, experiment id, client id

## Ability to check minimum participant
* Create a timer (one minute window) to check if any given time router contains minimum participants needed to route them into an experiment.

## FIFO Queue
* To avoid starvation system should always give priority to time that the participant arrives to the router. Necessary pool of participants are selected randomly but given the time arrive as a pre-condition for the selection. 

## Database Structure
* Router functionalities are maintian in a database supported by three tables with following structure.

|Experiment_info |
|----------------|
|id              |
|actual_url      |
|shoten_url      |
|num_participants|
|status          |

|user_info    |
|-------------|
|id           |
|gallup_id    |
|randomized_id|
|arrival_time |

|user_experiment|
|---------------|
|user_id        |
|experiment_id  |
|arrival_time
|send_off_time  |
|waited_time    |

* When user arrives to the router we log the arrival time of the user in the `user_info` table and when user get send off to an experiment the arrival time for the `user_info` table get coppied to the `user_experiment` table's `arrival_time`. Along with that we log time the user was sent to an experiment along with the time that user waited from the arrival. 


