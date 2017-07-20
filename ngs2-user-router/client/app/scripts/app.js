'use strict';

/**
 * @ngdoc overview
 * @name clientApp
 * @description
 * # clientApp
 *
 * Main module of the application.
 */
angular
  .module('clientApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'app.directives.experiment',
    'app.directives.users',
    'app.modals.create_experiment',
    'app.modals.export_users',
    'app.modals.import_users',
    'app.services.alert',
    'app.services.experiment',
    'app.services.users'
  ])
  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

   $locationProvider.html5Mode({ enabled: true, requireBase: false }).hashPrefix('*');
  })
  .constant('ApiConfig', {
    url: 'http://localhost:9090/'
  });

