(function() {
  'use strict';

  function UploadCSVFileController($uibModalInstance, $scope, Upload, $timeout, AdminService, ApiConfig) {
    $scope.uploadFiles = function (file, errFiles) {
      $scope.f = file;
      $scope.errFile = errFiles && errFiles[0];
      if (file) {
        file.upload = Upload.upload({
          url: ApiConfig.url + 'app/upload',
          data : {'file': file},
          method: 'POST',
          headers: {'X-AUTH-TOKEN':AdminService.authToken, 'Csrf-Token':'nocheck'}
        }).then(function(response) {
          $uibModalInstance.close(response);
        },function (response) {
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

  UploadCSVFileController.$inject = ['$uibModalInstance', '$scope', 'Upload', '$timeout', 'AdminService', 'ApiConfig'];
})();
