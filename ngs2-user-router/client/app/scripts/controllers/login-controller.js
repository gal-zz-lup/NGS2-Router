/**
 * Created by anuradha_uduwage on 6/23/17.
 */

'use strict';

angular.module('clientApp')
  .controller('LoginCtrl', function ($scope, userService, $location, $log, $http) {

    $scope.isAuthenticated = function() {
      if(userService.username) {
        $log.debug(userService.username);
        // where should user be landing
      } else {
        $http.get('/app/isauthenticated')
          .error(function() {
            $location.path('/login');
          })
          .success(function(data) {
            if(data.hasOwnProperty('success')) {
              userService.username = data.success.user;
              // where should the user be landing CRUD service?
            }
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
        .error(function(data){
          $log.debug(data);
        })
        .success(function(data){
          $log.debug(data);
        });
    };
  });
