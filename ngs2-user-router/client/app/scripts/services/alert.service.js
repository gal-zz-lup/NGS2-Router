(function() {
  'use strict';

  function AlertService($scope, $timeout) {
    var ALERT_TIMEOUT = 3000;
    $scope.alerts = [];

    function add(type, msg, timeout) {
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
    }

    function closeAlert(alert) {
      return closeAlertIdx($scope.alerts.indexOf(alert));
    }

    function closeAlertIdx(index) {
      return $scope.alerts.splice(index, 1);
    }

    function clear() {
      $scope.alerts = [];
    }

    function get() {
      return $scope.alerts;
    }

    return {
      add: add,
      closeAlert: closeAlert,
      closeAlertIdx: closeAlertIdx,
      clear: clear,
      get: get
    };
  }

  angular
    .module('app.services.alert', [])
    .factory('AlertService', AlertService);

  AlertService.$inject = ['$rootScope', '$timeout'];
})();
