/**
 * Created by novy on 07.04.15.
 */

'use strict';

var announcementConfigController = function($state, $scope) {

    $scope.initialize = function () {

        $scope.tabData = [
            {
                heading: 'Facebook',
                route: 'root.announcement.config.facebook'
            },
            {
                heading: 'Twitter',
                route: 'root.announcement.config.twitter'
            },
            {
                heading: 'Google Group',
                route: 'root.announcement.config.googlegroup'
            },
            {
                heading: 'IIET Board',
                route: 'root.announcement.config.board'
            }
        ];
    };

    $scope.initialize();
};

angular.module('announcementConfigController', [])
    .controller('announcementConfigController', announcementConfigController);
