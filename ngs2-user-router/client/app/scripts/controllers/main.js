'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
function MainController($scope, ExperimentService, UsersService, $uibModal, AlertService) {
  $scope.experiments = [];
  $scope.users = [];
  ExperimentService.getAllExperiments().then(function(resp) {
    $scope.experiments = resp;
    console.log('experiments', resp);
  });

  UsersService.getAllUsers().then(function(resp) {
    $scope.users = resp;
  });

  $scope.createExperiment = function() {
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

  $scope.addAlert = function() {
    AlertService.add('warning', 'Hello world!');
  };

  $scope.closeAlert = function(alert) {
    console.log('closeAlert');
    AlertService.closeAlert(alert);
  };
}

MainController.$inject = ['$scope', 'ExperimentService', 'UsersService', '$uibModal', 'AlertService'];

angular.module('clientApp')
  .controller('MainCtrl', MainController);
