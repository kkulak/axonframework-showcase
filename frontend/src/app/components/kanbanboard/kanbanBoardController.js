'use strict';

var kanbanBoardController = angular.module('kanbanBoardController', [
    'kanbanBoardService'
]);

kanbanBoardController.controller('boardController', function($scope, boardService) {
    $scope.columns = [];

    $scope.refreshBoard = function() {
        boardService.getColumns()
            .then(function (data) {
                $scope.columns = data;
            }, onError);
    };

    var onError = function (errorMessage) {
        console.log("ERROR: " + errorMessage);
    };

});