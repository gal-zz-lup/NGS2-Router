(function() {
  'use strict';

  function ExperimentService($q) {
    return {
      getAllExperiments: function () {
        return $q(function(resolve) {
          resolve([
            {'id': 1,
              'experiment_name': 'Experiment 1',
              'actual_url': 'http://www.google.com',
              'num_participants': 20,
              'status': 'ACTIVE',
              'priority': 0},
            {'id': 2,
              'experiment_name': 'Experiment 2',
              'actual_url': 'http://www.xkcd.com',
              'num_participants': 25,
              'status': 'ACTIVE',
              'priority': 1},
            {'id': 3,
              'experiment_name': 'Experiment 3',
              'actual_url': 'http://www.stackoverflow.com',
              'num_participants': 30,
              'status': 'STOPPED',
              'priority': 2}
          ]);
        });
      },
      createExperiment: function(experimentName, experimentUrl, numParticipants, experimentPriority) {
        return $q(function(resolve) {
          resolve({
            'id': 4,
            'experiment_name': experimentName,
            'actual_url': experimentUrl,
            'num_participants': numParticipants,
            'status': 'ACTIVE',
            'priority': experimentPriority
          });
        });
      },
      removeExperiment: function(experimentId) {
        return $q(function(resolve) {
          resolve({
          });
        });
      }
    };
  }

  angular
    .module('app.services.experiment', [])
    .factory('ExperimentService', ExperimentService);

  ExperimentService.$inject = ['$q'];
})();
