'use strict';

/**
 * @ngdoc service
 * @name clientApp.admin
 * @description
 * # admin
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('adminService', function () {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var username = '';

    return {
      username : username
    };
  });
