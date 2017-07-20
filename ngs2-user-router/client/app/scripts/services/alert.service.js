(function() {
  'use strict';

  function AlertService($scope, $timeout) {
    var ALERT_TIMEOUT = 3000;
    $scope.alerts = [];

    return {
      add: function (type, msg, timeout) {
        if (timeout) {
          $timeout(function(){
            closeAlert(this);
          }, timeout);
        } else {
          $timeout(function(){
            closeAlert(this);
          }, ALERT_TIMEOUT);
        }

        return $scope.alerts.push({
          type: type,
          msg: msg,
          close: function() {
            return closeAlert(this);
          }
        });
      },
      closeAlert: function(alert) {
        return this.closeAlertIdx($scope.alerts.indexOf(alert));
      },
      closeAlertIdx: function(index) {
        return $scope.alerts.splice(index, 1);
      },
      clear: function() {
        $scope.alerts = [];
      },
      get: function() {
        return $scope.alerts;
      }
    };
  }

  angular
    .module('app.services.alert', [])
    .factory('AlertService', AlertService);

  AlertService.$inject = ['$rootScope', '$timeout'];
})();
