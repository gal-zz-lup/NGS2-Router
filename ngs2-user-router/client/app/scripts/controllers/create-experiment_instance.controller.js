(function() {
  'use strict';

  function CreateExperimentInstanceController($scope, $uibModalInstance, ExperimentInstanceService, experiment) {
    $scope.close = function() {
      $uibModalInstance.close();
    };

    $scope.createExperimentInstance = function(experimentInstanceName, experimentInstanceUrlActual, nParticipants, priority) {
      console.log("createExperimentInstance", experimentInstanceName, experimentInstanceUrlActual, nParticipants, priority, experiment);
      ExperimentInstanceService
        .createExperimentInstance(experiment.id, experimentInstanceName, experimentInstanceUrlActual, nParticipants, priority)
        .then(function(resp) {
          console.log('resp', resp);
          $uibModalInstance.close(resp);
      });
    };
  }

  angular
    .module('app.modals.create_experiment_instance', [])
    .controller('CreateExperimentInstanceController', CreateExperimentInstanceController);

  CreateExperimentInstanceController.$inject = ['$scope', '$uibModalInstance', 'ExperimentInstanceService', 'experiment'];

})();

