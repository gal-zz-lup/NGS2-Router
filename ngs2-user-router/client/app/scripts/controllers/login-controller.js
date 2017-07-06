/**
 * Created by anuradha_uduwage on 6/23/17.
 */
angular.module('clientApp')
  .controller('LoginCtrl', function ($scope, userService, $location, $log, $http, alertService) {

    $scope.isAuthenticated = function() {
      if(userService.username) {
        $log.debug(userService.username);
        $location.path('/main'); //not sure if this is the correct place to land
      } else {
        $http.get('/app/login')
          .error(function() {
            $location.path('/login');
          })
          .success(function(data) {
            if(data.hasOwnProperty('success')) {
              userService.username = data.success.user;
              // where should the user be landing CRUD service?
              $location.path('/main');
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
        .error(function(data, status){
          log.debug(data)
          if (status == 400) {
            angular.forEach(data, function(value, key) {
              if (key === 'email' || key === 'password') {
                alertService.add('danger', key + ' : ' + value);
              } else {
                alertService.add('danger', value.message);
              }
            })
          }
        })
        .success(function(data){
          $log.debug(data);
          if(data.hasOwnProperty('success')) {
            userService.username = data.success.user;
            $location.path('/main')
          }
        });
    };
  });
