/**
 * Created by anuradha_uduwage on 7/6/17.
 */
'use strict';

angular.module('clientApp').controller('AlertsCtrl', function($scope, alertService) {
  $scope.alerts = alertService.get();
});
