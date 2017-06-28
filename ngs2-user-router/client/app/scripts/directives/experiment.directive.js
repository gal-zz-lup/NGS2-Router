(function() {
  'use strict';

  function ExperimentController($scope, ExperimentService) {
    $scope.hover = false;
    $scope.editing = false;
    $scope.deleted = false;

    $scope.editExperiment = function() {
      $scope.editing = !$scope.editing;
    };

    $scope.toggleExperiment = function(experiment) {
      experiment.status = (experiment.status === 'ACTIVE') ? 'STOPPED' : 'ACTIVE';
    };

    $scope.deleteExperiment = function(experiment) {
      console.log("deleteExperiment", experiment);
      if (confirm("Are you sure you want this delete this experiment? This cannot be undone.")) {
        ExperimentService.removeExperiment(experiment.id)
          .then(function () {
            $scope.deleted = true;
          });
      }
    };
  }

  ExperimentController.$inject = ['$scope', 'ExperimentService'];

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
