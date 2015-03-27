'use strict';

var kanbanBoardService = angular.module('kanbanBoardService', []);

kanbanBoardService.service('boardService', function($http, $q) {
    this.getColumns = function() {
        return $http.get("mock-data/board.json")
            .then( success, error );
    };

    function success(response) {
        return response.data;
    }

    function error(message) {
        return $q.reject("ERROR: " + message);
    }

});