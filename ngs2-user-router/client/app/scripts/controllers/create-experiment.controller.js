(function() {
  'use strict';

  function CreateExperimentController($scope, $uibModalInstance, ExperimentService) {
    $scope.close = function() {
      $uibModalInstance.close();
    };

    $scope.createExperiment = function(experimentName, experimentUrl, numParticipants, experimentPriority) {
      ExperimentService.createExperiment(experimentName, experimentUrl, numParticipants, experimentPriority).then(function(resp) {
        console.log('resp', resp);
        $uibModalInstance.close(resp);
      });
    };
  }

  angular
    .module('app.modals.create_experiment', [])
    .controller('CreateExperimentController', CreateExperimentController);

  CreateExperimentController.$inject = ['$scope', '$uibModalInstance', 'ExperimentService'];

})();

