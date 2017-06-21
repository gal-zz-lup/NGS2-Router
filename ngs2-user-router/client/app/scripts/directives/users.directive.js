(function() {
  'use strict';

  function UsersController($scope, $uibModal, UsersService) {
    $scope.importUsers = function() {
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'views/modals/import-users.modals.html',
        controller: 'ImportUsersController',
        size: 'lg'
      });

      modalInstance.result.then(function (importedUsers) {
        console.log(importedUsers);
        $scope.users.push(importedUsers);
      });
    };

    $scope.exportUsers = function() {
      UsersService.exportUsers().then(function(resp) {
        $uibModal.open({
          animation: true,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'views/modals/export-users.modals.html',
          controller: 'ExportUsersController',
          size: 'lg',
          resolve: {
            exportedUsers: function() {
              return resp;
            }
          }
        });

      });
    };
  }

  UsersController.$inject = ['$scope', '$uibModal', 'UsersService'];

  function Users() {
    return {
      restrict: 'A',
      replace: true,
      templateUrl: 'views/templates/users.template.html',
      controller: 'UsersController',
      transclude: false,
      scope: true,
      bindToController: {
        users: '='
      }
    };
  }

  angular.module('app.directives.users', [])
    .directive('users', Users)
    .controller('UsersController', UsersController);

})();
