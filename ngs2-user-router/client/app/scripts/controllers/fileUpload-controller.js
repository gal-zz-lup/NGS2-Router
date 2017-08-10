(function() {
  'use strict';

  function UploadCSVFileController($scope, Upload, $timeout, AdminService, ApiConfig) {
    $scope.uploadFiles = function (file, errFiles) {
      $scope.f = file;
      $scope.errFile = errFiles && errFiles[0];
      if (file) {
        file.upload = Upload.upload({
          url: ApiConfig.url + 'app/upload',
          data : {file: file},
          headers: {'X-AUTH-TOKEN':AdminService.authToken, 'Csrf-Token':'nocheck'}
        });

        file.upload.then(function (response) {
          $timeout(function () {
            file.result = response.data;
          });
        }, function (response) {
          if (response.status > 0)
            $scope.errorMsg = response.status + ': ' + response.data;
        }, function (evt) {
          file.progress = Math.min(100, parseInt(100.0 *
            evt.loaded / evt.total));
        });
      }
    }
  }

  angular
    .module('app.modals.file_upload', [])
    .controller('UploadCSVFileController', UploadCSVFileController);

  UploadCSVFileController.$inject = ['$scope', 'Upload', '$timeout', 'AdminService', 'ApiConfig'];
})();
