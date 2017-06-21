(function() {
  'use strict';

  function ImportUsersController($scope, $uibModalInstance, UsersService) {
    $scope.close = function() {
      $uibModalInstance.close();
    };

    $scope.importUsers = function() {
      UsersService.importUsers($scope.importCsv).then(function(resp) {
        console.log('resp', resp);
        $uibModalInstance.close(resp);
      });
    };
  }

  angular
    .module('app.modals.import_users', [])
    .controller('ImportUsersController', ImportUsersController);

  ImportUsersController.$inject = ['$scope', '$uibModalInstance', 'UsersService'];

})();
