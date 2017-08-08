/**
 * Created by anuradha_uduwage on 6/23/17.
 */
'use strict';

angular.module('clientApp')
  .controller('LoginCtrl', function ($scope, adminService, $location, $log, $http, alertService) {

    $scope.isAuthenticated = function() {
      if(adminService.username) {
        $log.debug(adminService.username);
        $location.path('/');
      } else {
        $http.get('/app/isauthenticated')
          .then(function(data) {
            if(data.hasOwnProperty('success')) {
              adminService.username = data.success.username;
              alerts(data.success.username);
              $location.path('/');
            }
          }, function() {
            $location.path('/login');
          });
      }
    };

    //$scope.isAuthenticated();

    $scope.login = function() {

      var payload = {
        email : this.email,
        password : this.password
      };

      adminService.

      $http.post('/app/login', payload)
        .then(function(data){
          $log.debug(data);
          if(data.hasOwnProperty('success')) {
            adminService.username = payload.email;
            adminService.authToken = data.body.authenticationToken
            $location.path('/');
          }
        },
          function(data){
            if(data.status === 400) {
              angular.forEach(data, function(value, key) {
                if(key === 'email' || key === 'password') {
                  alertService.add('danger', key + ' : ' + value);
                } else {
                  alertService.add('danger', value.message);
                }
              });
            } else if(data.status === 401) {
              alertService.add('danger', 'Invalid login or password!');
            } else if(data.status === 500) {
              alertService.add('danger', 'Internal server error!');
            } else {
              alertService.add('danger', data);
            }
          });
    };
  });
