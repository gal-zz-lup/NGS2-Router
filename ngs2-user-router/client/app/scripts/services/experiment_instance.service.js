(function() {
  'use strict';

  function ExperimentInstanceService(ApiConfig, $http, $q) {
    return {
      createExperimentInstance: function(experimentId, experimentInstanceName, experimentInstanceUrl, nParticipants, priority) {
        var payload = {
          'experimentId': experimentId,
          'experimentInstanceName': experimentInstanceName,
          'experimentInstanceUrl': experimentInstanceUrl,
          'nParticipants': nParticipants,
          'priority': priority
        };
        return $http.put(ApiConfig.url + 'app/createExperimentInstance', payload)
          .then(function(response) {
              return $q.when(response.body);
            },
            function(response) {
              return $q.reject(response.body);
            });
      },
      updateExperimentInstance: function(experimentInstance) {
        return $http.post(ApiConfig.url + 'app/updateExperimentInstance/' + experimentInstance.id, experimentInstance)
          .then(function(response) {
              return $q.when(response.body);
            },
            function(response) {
              return $q.reject(response.body);
            });
      },
      removeExperimentInstance: function(experimentInstanceId) {
        //console.log('removeExperimentInstance', experimentInstanceId);
        return $q(function(resolve) {
          resolve({
          });
        });
      }
    };
  }

  angular
    .module('app.services.experiment_instance', [])
    .factory('ExperimentInstanceService', ExperimentInstanceService);

  ExperimentInstanceService.$inject = ['ApiConfig', '$http', '$q'];
})();
