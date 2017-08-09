'use strict';

/**
 * @ngdoc service
 * @name clientApp.admin
 * @description
 * # admin
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('AdminService', function () {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var username = '';
    var authToken = '';
    var loggedIn = false;

    return {
      username : username,
      authToken : authToken,
      loggedIn : loggedIn
    };
  });
