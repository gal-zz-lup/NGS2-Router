(function () {
  'use strict';

  function ExperimentInstanceService(ApiConfig, $http, $q, AdminService) {
    return {
      createExperimentInstance: function (experimentId, experimentInstanceName, experimentInstanceUrl, nParticipants, priority) {
        var payload = {
          'experimentId': experimentId,
          'experimentInstanceName': experimentInstanceName,
          'experimentInstanceUrlActual': experimentInstanceUrl,
          'nParticipants': nParticipants,
          'priority': priority
        };
        return $http({
          method: 'PUT',
          url: ApiConfig.url + 'app/createExperimentInstance',
          data: payload,
          headers: {'X-AUTH-TOKEN': AdminService.authToken, 'Csrf-Token': 'nocheck'}
        }).then(function (response) {
            return $q.when(response);
          },
          function (response) {
            return $q.reject(response);
          });
      },
      updateExperimentInstance: function (experimentInstance) {
        return $http({
          method: 'POST',
          url: ApiConfig.url + 'app/updateExperimentInstance/' + experimentInstance.id,
          data: experimentInstance,
          headers: {'X-AUTH-TOKEN': AdminService.authToken, 'Csrf-Token': 'nocheck'}
        }).then(function (response) {
            return $q.when(response.body);
          },
          function (response) {
            return $q.reject(response.body);
          });
      },
      removeExperimentInstance: function (experimentInstanceId) {
        //console.log('removeExperimentInstance', experimentInstanceId);
        return $http({
          method: 'DELETE',
          url: ApiConfig.url + 'app/deleteExperimentInstance/' + experimentInstanceId,
          headers: {'X-AUTH-TOKEN': AdminService.authToken, 'Csrf-Token': 'nocheck'}
        }).then(function (response) {
            return $q.when(response);
          },
          function (response) {
            return $q.reject(response);
          });
      }
    };
  }

  angular
    .module('app.services.experiment_instance', [])
    .factory('ExperimentInstanceService', ExperimentInstanceService);

  ExperimentInstanceService.$inject = ['ApiConfig', '$http', '$q', 'AdminService'];
})();
