(function() {
  'use strict';

  function ExportUsersController($scope, $uibModalInstance, exportedUsers) {
    $scope.exportedUsers = exportedUsers;
    $scope.close = function() {
      $uibModalInstance.close();
    };
  }

  angular
    .module('app.modals.export_users', [])
    .controller('ExportUsersController', ExportUsersController);


})();
