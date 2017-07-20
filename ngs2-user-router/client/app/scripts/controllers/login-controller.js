/**
 * Created by anuradha_uduwage on 6/23/17.
 */

'use strict';

angular.module('clientApp')
<<<<<<< HEAD
  .controller('LoginCtrl', function ($scope, userService, $location, $log, $http) {
=======
  .controller('LoginCtrl', function ($scope, adminService, $location, $log, $http, alertService) {
>>>>>>> 0b4b52aeb59ca8bb2afaad2bad38941110a7f3cd

    $scope.isAuthenticated = function() {
      if(adminService.username) {
        $log.debug(adminService.username);
        $location.path('/');
      } else {
        $http.get('/app/isauthenticated')
          .then(function(data) {
            if(data.hasOwnProperty('success')) {
              adminService.username = data.success.username;
              $location.path('/app/createExperiment');
            }
          }, function() {
            $location.path('/login');
          });
      }
    };

    $scope.isAuthenticated();

    $scope.login = function() {

      var payload = {
        email : this.email,
        password : this.password
      };

      $http.post('/app/login', payload)
<<<<<<< HEAD
        .error(function(data){
          $log.debug(data);
        })
        .success(function(data){
=======
        .then(function(data, status){
          if(status === 400) {
            angular.forEach(data, function(value, key) {
              if(key === 'email' || key === 'password') {
                alertService.add('danger', key + ' : ' + value);
              } else {
                alertService.add('danger', value.message);
              }
            });
          } else if(status === 401) {
            alertService.add('danger', 'Invalid login or password!');
          } else if(status === 500) {
            alertService.add('danger', 'Internal server error!');
          } else {
            alertService.add('danger', data);
          }
        }, function(data){
>>>>>>> 0b4b52aeb59ca8bb2afaad2bad38941110a7f3cd
          $log.debug(data);
          if(data.hasOwnProperty('success')) {
            adminService.username = data.success.username;
            $location.path('/');
          }
        });
    };
  });
