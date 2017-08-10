(function() {
  'use strict';

  function ExperimentService(ApiConfig, $http, $q, AdminService) {
    return {
      getAllExperiments: function () {
        return $http({
          method: 'GET',
          url: ApiConfig.url + 'app/allExperiments',
          headers: {'X-AUTH-TOKEN':AdminService.authToken}
        }).then(function(response) {
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
        return $http({
          method: 'PUT',
          url: ApiConfig.url + 'app/createExperiment',
          data: payload,
          headers: {'X-AUTH-TOKEN':AdminService.authToken, 'Csrf-Token':'nocheck'}
        }).then(function(response) {
              return $q.when(response);
            },
            function(response) {
              return $q.reject(response);
            });
      },
      updateExperiment: function(experiment) {
        return $http({
          method: 'POST',
          url: ApiConfig.url + 'app/updateExperiment/' + experiment.id,
          data: experiment,
          headers: {'X-AUTH-TOKEN':AdminService.authToken, 'Csrf-Token':'nocheck'}
        }).then(function(response) {
              return $q.when(response);
            },
            function(response) {
              return $q.reject(response);
            });
      },
      removeExperiment: function(experimentId) {
        console.log('removeExperiment', experimentId);
        return $http({
          method: 'DELETE',
          url: ApiConfig.url + 'app/deleteExperiment/' + experimentId,
          headers: {'X-AUTH-TOKEN':AdminService.authToken, 'Csrf-Token':'nocheck'}
        }).then(function(response) {
            return $q.when(response);
          },
          function(response) {
            return $q.reject(response);
          });
      }
    };
  }

  angular
    .module('app.services.experiment', [])
    .factory('ExperimentService', ExperimentService);

  ExperimentService.$inject = ['ApiConfig', '$http', '$q', 'AdminService'];
})();
