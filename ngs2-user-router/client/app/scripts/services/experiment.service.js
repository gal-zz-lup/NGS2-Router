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
      createExperiment: function(experimentName) {
        var payload = {
          'experimentName': experimentName
        };
        return $http.put(ApiConfig.url + 'app/createExperiment', payload)
          .then(function(response) {
              return $q.when(response);
            },
            function(response) {
              return $q.reject(response);
            });
      },
      updateExperiment: function(experiment) {
        return $http.post(ApiConfig.url + 'app/updateExperiment/' + experiment.id, experiment)
          .then(function(response) {
              return $q.when(response);
            },
            function(response) {
              return $q.reject(response);
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
