(function() {
  'use strict';

  function CreateExperimentController($scope, $uibModalInstance, ExperimentService) {
    $scope.close = function() {
      $uibModalInstance.close();
    };

    $scope.createExperiment = function(experimentName) {
      ExperimentService.createExperiment(experimentName).then(function(resp) {
        console.log('resp', resp);
        $uibModalInstance.close(resp.data.body);
      });
    };
  }

  angular
    .module('app.modals.create_experiment', [])
    .controller('CreateExperimentController', CreateExperimentController);

  CreateExperimentController.$inject = ['$scope', '$uibModalInstance', 'ExperimentService'];

})();

