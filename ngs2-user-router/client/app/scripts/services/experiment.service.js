(function() {
  'use strict';

  function ExperimentService(ApiConfig, $http, $q) {
    return {
      getAllExperiments: function () {
        return $http.get(ApiConfig.url + 'app/allExperiments')
          .then(function(response) {
            return $q.when(response.data);
          },
          function(response) {
            return $q.reject(response.data);
          });
      },
      createExperiment: function(experimentName, experimentUrl, numParticipants, experimentPriority) {
        var payload = {
          'experimentName': experimentName,
          'actualURL': experimentUrl,
          'numberOfParticipants': numParticipants,
          'priority': experimentPriority
        };
        return $http.put(ApiConfig.url + 'app/createExperiment', payload)
          .then(function(response) {
              return $q.when(response.body);
            },
            function(response) {
              return $q.reject(response.body);
            });
      },
      removeExperiment: function(experimentId) {
        console.log('removeExperiment', experimentId);
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

  ExperimentService.$inject = ['ApiConfig', '$http', '$q'];
})();
