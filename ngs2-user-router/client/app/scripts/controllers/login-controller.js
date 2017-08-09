'use strict';

function LoginCtrl($scope, AdminService, $location, $log, $http, AlertService, ApiConfig) {
  $scope.isAuthenticated = function() {
    if(AdminService.username) {
      $log.debug(AdminService.username);
      //$location.path('/');
    } else {
      $http.get('/app/isauthenticated')
        .then(function(data) {
          if(data.hasOwnProperty('success')) {
            AdminService.username = data.success.username;
            AdminService.loggedIn = true;
            alerts(data.success.username);
            //$location.path('/');
          }
        }, function() {
          //$location.path('#!login');
        });
    }
  };

  //$scope.isAuthenticated();

  $scope.login = function() {

    var payload = {
      email : this.email,
      password : this.password
    };

    $http.post(ApiConfig.url + 'app/login', payload)
      .then(function(response){
          if(response.data.hasOwnProperty('isSuccessful') && response.data.isSuccessful) {
            AdminService.username = payload.email;
            AdminService.authToken = response.data.body.authenticationToken;
            AdminService.loggedIn = true;
          }
        },
        function(data){
        console.log("failed login", data);
          if(data.status === 400) {
            angular.forEach(data, function(value, key) {
              if(key === 'email' || key === 'password') {
                AlertService.add('danger', key + ' : ' + value);
              } else {
                AlertService.add('danger', value.message);
              }
            });
          } else if(data.status === 401) {
            AlertService.add('danger', 'Invalid login or password!');
          } else if(data.status === 500) {
            AlertService.add('danger', 'Internal server error!');
          } else {
            AlertService.add('danger', data);
          }
        });
  };

}

angular.module('clientApp')
  .controller('LoginCtrl', LoginCtrl);

LoginCtrl.$inject = ['$scope', 'AdminService', '$location', '$log', '$http', 'AlertService', 'ApiConfig'];
