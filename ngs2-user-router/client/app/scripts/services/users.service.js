(function() {
  'use strict';

  function UsersService($q, $http, ApiConfig, AdminService) {
    return {
      getAllUsers: function () {
        return $http({
          method: 'GET',
          url: ApiConfig.url + 'app/allUsers',
          headers: {'X-AUTH-TOKEN':AdminService.authToken}
        }).then(function(response) {
          console.log("response", response);
            return $q.when(response.data);
          },
          function(response) {
            return $q.reject(response.data);
          });
      },
      exportUsers: function() {
        return $http({
          method: 'GET',
          url: ApiConfig.url + 'app/exportUsers',
          headers: {'X-AUTH-TOKEN':AdminService.authToken}
        }).then(function(response) {
            console.log("response", response);
            return $q.when(response.data);
          },
          function(response) {
            return $q.reject(response.data);
          });
      },
      importUsers: function(importCsv) {
        return $q(function(resolve) {
          console.log('importCsv', importCsv);
          resolve({
            'id': 4,
            'gallup_id': 'TUVW1236',
            'language': 'ru',
            'url': 'https://brdbrd.net/D9E1F3'
          });
        });
      },
      removeUser: function(userId) {
        return $q(function(resolve) {
          console.log('Remove user', userId);
          resolve({
          });
        });
      }
    };
  }

  angular
    .module('app.services.users', [])
    .factory('UsersService', UsersService);

  UsersService.$inject = ['$q', '$http', 'ApiConfig', 'AdminService'];
})();
