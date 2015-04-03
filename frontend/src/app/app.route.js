'use strict';

var appRouting = angular.module('appRouting', ['ui.router']);

appRouting.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url         :   '/',
            templateUrl :   'app/components/kanbanboard/kanbanBoardView.html',
            controller  :   'boardController'
        });

});