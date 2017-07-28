(function() {
  'use strict';

  function ExperimentInstanceController($scope, ExperimentInstanceService) {
    $scope.hover = false;
    $scope.editing = false;
    $scope.deleted = false;

    $scope.editExperimentInstance = function() {
      $scope.editing = !$scope.editing;
    };

    $scope.toggleExperimentInstance = function(experimentInstance) {
      experimentInstance.status = (experimentInstance.status === 'ACTIVE') ? 'STOPPED' : 'ACTIVE';
    };

    $scope.deleteExperimentInstance = function(experimentInstance) {
      console.log('deleteExperimentInstance', experimentInstance);
      if (window.confirm('Are you sure you want this delete this experiment instance? This cannot be undone.')) {
        ExperimentInstanceService.removeExperimentInstance(experimentInstance.id)
          .then(function () {
            $scope.deleted = true;
          });
      }
    };
  }

  ExperimentInstanceController.$inject = ['$scope', 'ExperimentInstanceService'];

  function ExperimentInstance() {
    return {
      restrict: 'A',
      replace: true,
      templateUrl: 'views/templates/experiment_instance.template.html',
      controller: 'ExperimentInstanceController',
      transclude: false,
      scope: true,
      bindToController: {
        experimentInstance: '='
      }
    };
  }


  angular.module('app.directives.experiment_instance', [])
    .directive('experimentInstance', ExperimentInstance)
    .controller('ExperimentInstanceController', ExperimentInstanceController);

})();
