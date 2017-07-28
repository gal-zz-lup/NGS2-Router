(function() {
  'use strict';

  function ExperimentController($scope, ExperimentService, $uibModal, $timeout, AlertService) {
    $scope.dirty = false;
    $scope.saved = false;
    $scope.hover = false;
    $scope.editing = false;
    $scope.deleted = false;
    $scope.minimized = true;

    $scope.editExperiment = function(experiment) {
      if ($scope.editing) {
        $scope.dirty = true;
        //console.log('updateExperiment', experiment);
        ExperimentService.updateExperiment(experiment)
          .then(function() {
            $scope.dirty = false;
            $scope.saved = true;
            $timeout(function() { $scope.saved = false; }, 1000);
          });
      }
      $scope.editing = !$scope.editing;
    };

    $scope.deleteExperiment = function(experiment) {
      //console.log('deleteExperiment', experiment);
      if (window.confirm('Are you sure you want this delete this experiment? This cannot be undone.')) {
        ExperimentService.removeExperiment(experiment.id)
          .then(function () {
            $scope.deleted = true;
            AlertService.add('success', 'Experiment deleted');
          });
      }
    };

    $scope.createExperimentInstance = function(experiment) {
      //console.log('createExperimentInstance', experiment);

      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'views/modals/create-experiment_instance.modals.html',
        controller: 'CreateExperimentInstanceController',
        resolve: {
          experiment: experiment
        },
        size: 'lg'
      });

      modalInstance.result.then(function(resp) {
        //console.log('newExperimentInstance', newExperimentInstance);
        experiment.experimentInstances.push(resp.data.body);
        AlertService.add('success', 'Experiment instance created');
      });
    }
  }

  ExperimentController.$inject = ['$scope', 'ExperimentService', '$uibModal', '$timeout', 'AlertService'];

  function Experiment() {
    return {
      restrict: 'A',
      replace: true,
      templateUrl: 'views/templates/experiment.template.html',
      controller: 'ExperimentController',
      transclude: false,
      scope: true,
      bindToController: {
        experiment: '='
      }
    };
  }

  angular.module('app.directives.experiment', [])
    .directive('experiment', Experiment)
    .controller('ExperimentController', ExperimentController);

})();
