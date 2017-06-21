'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
function MainController($scope, ExperimentService, UsersService) {
  $scope.experiments = [];
  $scope.users = [];
  ExperimentService.getAllExperiments().then(function(resp) {
    $scope.experiments = resp;
  });

  UsersService.getAllUsers().then(function(resp) {
    $scope.users = resp;
  });

  $scope.addExperiment = function() {
    ExperimentService.addExperiment().then(function(resp) {
      $scope.experiments.push(resp);
    });
  };
}

MainController.$inject = ['$scope', 'ExperimentService', 'UsersService'];

angular.module('clientApp')
  .controller('MainCtrl', MainController);
