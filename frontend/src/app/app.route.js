'use strict';

var appRouting = angular.module('appRouting', ['ui.router']);

appRouting.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url         :   '/',
            views       :   {
                'menu-navbar': {
                    templateUrl :  'app/shared/menu-bar/menu-bar.html'
                },
                'jumbotron': {
                    templateUrl :   'app/shared/jumbotron/jumbotron.html'
                },
                'content': {
                    templateUrl :  'app/components/kanbanboard/kanbanBoardView.html',
                    controller  :  'boardController'
                }
            }
        });

});