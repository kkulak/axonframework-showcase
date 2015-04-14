'use strict';

var knbitEventsBc = angular.module('knbitEventsBc', [
    'ui.bootstrap',
    'appRouting',
    'ui.router.tabs',
    'kanbanBoardController',
    'kanbanBoardDirective',
    'announcementConfigController',
    'publisherConfigResources',
    'toastingService',
    'publisherConfigControllers',
    'announcementPublishingController',
    'isteven-multi-select'
]);