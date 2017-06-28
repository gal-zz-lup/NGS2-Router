'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
function MainController($scope, ExperimentService, UsersService, $uibModal) {
  $scope.experiments = [];
  $scope.users = [];
  ExperimentService.getAllExperiments().then(function(resp) {
    $scope.experiments = resp;
  });

  UsersService.getAllUsers().then(function(resp) {
    $scope.users = resp;
  });

  $scope.addExperiment = function() {
    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'views/modals/create-experiment.modals.html',
      controller: 'CreateExperimentController',
      size: 'lg'
    });

    modalInstance.result.then(function(newExperiment) {
      $scope.experiments.push(newExperiment);
    });
  };
}

MainController.$inject = ['$scope', 'ExperimentService', 'UsersService', '$uibModal'];

angular.module('clientApp')
  .controller('MainCtrl', MainController);
