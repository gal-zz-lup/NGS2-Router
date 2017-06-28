'use strict';

/**
 * @ngdoc service
 * @name clientApp.alerts
 * @description
 * # alerts
 * Service in the clientApp.
 */
angular.module('clientApp')
  .factory('AlertService', ['$rootScope', '$timeout', function($scope, $timeout) {

      var ALERT_TIMEOUT = 3000;

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

      function clear(){
        $scope.alerts = [];
      }

      function get() {
        return $scope.alerts;
      }

      var service = {
          add: add,
          closeAlert: closeAlert,
          closeAlertIdx: closeAlertIdx,
          clear: clear,
          get: get
        };

      $scope.alerts = [];

      return service;
    }
  ]);
