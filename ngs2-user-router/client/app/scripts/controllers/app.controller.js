'use strict';

function AppController($scope, AdminService) {
  $scope.AdminService = AdminService;
}

AppController.$inject = ['$scope', 'AdminService'];

angular.module('clientApp')
  .controller('AppCtrl', AppController);
