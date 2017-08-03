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

/*
Upload.upload({
  url: '/api/upload',
  method:'POST',
  data: {
    "file": user.profilePic, //file object

  }
}).then(function (resp) {
  //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);

}, function (resp) {
  console.log('Error status: ' + resp.status);
}, function (evt) {
  var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
  //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
});
*/
