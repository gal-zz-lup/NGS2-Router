(function() {
  'use strict';

  function ExperimentController($scope) {
    $scope.editExperiment = function() {
      $scope.editing = !$scope.editing;
    };
    $scope.toggleExperiment = function(experiment) {
      experiment.status = (experiment.status === 'ACTIVE') ? 'STOPPED' : 'ACTIVE';
    };
    $scope.editExperiment = function() {
      $scope.editing = !$scope.editing;
    };
    $scope.hover = false;
    $scope.editing = false;
  }

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
