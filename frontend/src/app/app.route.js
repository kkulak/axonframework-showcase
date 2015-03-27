'use strict';

var appRouting = angular.module('appRouting', ['ngRoute']);

appRouting.config(function($routeProvider) {
    $routeProvider
        .when('/kanban-board', {
            templateUrl : 'app/components/kanbanboard/kanbanBoardView.html',
            controller  : 'boardController'
        })
        .otherwise({
            redirectTo  : '/kanban-board'
        })
});