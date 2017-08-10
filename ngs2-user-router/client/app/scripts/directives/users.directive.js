(function() {
  'use strict';

  function UsersController($scope, $uibModal, UsersService) {
    console.log("users", $scope.users);
    $scope.hover = false;

    $scope.deleteUser = function(user) {
      if (window.confirm('Are you sure you want this delete this user? This cannot be undone.')) {
        UsersService.removeUser(user.userId)
          .then(function () {
            $scope.users.splice($scope.users.indexOf(user), 1);
          });
      }
    };

    $scope.importUsers = function() {
      var modalInstance = $uibModal.open({
        animation: true,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'views/modals/import-users.modals.html',
        controller: 'UploadCSVFileController',
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
              return resp.user_csv;
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
