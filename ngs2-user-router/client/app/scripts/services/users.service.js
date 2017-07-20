(function() {
  'use strict';

  function UsersService($q) {
    return {
      getAllUsers: function () {
        return $q(function(resolve) {
          resolve([
            {'id': 1,
              'gallup_id': 'ABCD1234',
              'language': 'en',
              'url': 'https://brdbrd.net/A4B5C6'
            },
            {'id': 2,
              'gallup_id': 'EFGH5678',
              'language': 'es',
              'url': 'https://brdbrd.net/B4C6D8'
            },
            {'id': 3,
              'gallup_id': 'IJKL9101',
              'language': 'sw',
              'url': 'https://brdbrd.net/C8D0E2'
            }
          ]);
        });
      },
      exportUsers: function() {
        return $q(function(resolve) {
          resolve(
            '\"gallup_id\",\"url\"\n' +
            '\"ABCD1234\",\"https://brdbrd.net/A4B5C6\"\n' +
            '\"EFGH5678\",\"https://brdbrd.net/B4C6D8\"\n' +
            '\"IJKL9101\",\"https://brdbrd.net/C8D0E2\"\n'
          );
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

  UsersService.$inject = ['$q'];
})();
