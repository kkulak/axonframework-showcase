'use strict';

var kanbanBoardDirective = angular.module('kanbanBoardDirective', []);

kanbanBoardDirective.directive('kanbanBoardDragg', function () {
    return {
        link: function ($scope, element, attrs) {

            var dragData = "";
            $scope.$watch(attrs.kanbanBoardDragg, function (newValue) {
                dragData = newValue;
            });

            element.bind('dragstart', function (event) {
                event.originalEvent.dataTransfer.setData("Text", JSON.stringify(dragData));
            });
        }
    };
});

kanbanBoardDirective.directive('kanbanBoardDrop', function () {
    return {
        link: function ($scope, element, attrs) {

            var dragOverClass = attrs.kanbanBoardDrop;

            //  Prevent the default behavior. This has to be called in order for drob to work
            function cancel(event) {
                if (event.preventDefault) {
                    event.preventDefault();
                }

                if (event.stopPropigation) {
                    event.stopPropigation();
                }
                return false;
            };

            element.bind('dragover', function (event) {
                cancel(event);
                event.originalEvent.dataTransfer.dropEffect = 'move';
                element.addClass(dragOverClass);
            });

            element.bind('drop', function (event) {
                cancel(event);
                element.removeClass(dragOverClass);
                var droppedData = JSON.parse(event.originalEvent.dataTransfer.getData('Text'));
                //$scope.onDrop(droppedData, element.attr('id'));
            });

            element.bind('dragleave', function (event) {
                element.removeClass(dragOverClass);
            });
        }
    };
});