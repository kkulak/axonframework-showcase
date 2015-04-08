'use strict';

var appRouting = angular.module('appRouting', ['ui.router']);

appRouting.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('root', {
            url: '',
            abstract: true,
            views: {
                'menu-navbar': {
                    templateUrl: 'app/shared/menu-bar/menu-bar.html'
                },
                'jumbotron': {
                    templateUrl: 'app/shared/jumbotron/jumbotron.html'
                }
            }
        })
        .state('root.home', {
            url: '/',
            views: {
                'content@': {
                    templateUrl: 'app/components/kanbanboard/kanbanBoardView.html',
                    controller: 'boardController'
                }
            }
        })
        .state('root.announcement', {})
        .state('root.announcement.config', {
            url: '/announcement/config',
            views: {
                'content@': {
                    templateUrl: 'app/components/announcement/config/configTab.html',
                    controller: 'announcementConfigController'
                }
            }
        })
        .state('root.announcement.config.facebook', {
            url: '/announcement/config/facebook',
            templateUrl: 'app/components/announcement/config/facebook/facebook.html',
            controller: 'facebookPropertiesController'
        })
        .state('root.announcement.config.twitter', {
            url: '/announcement/config/twitter',
            templateUrl: 'app/components/announcement/config/twitter/twitter.html',
            controller: 'twitterPropertiesController'
        })
        .state('root.announcement.config.googlegroup', {
            url: '/announcement/config/googlegroup',
            templateUrl: 'app/components/announcement/config/googlegroup/googlegroup.html',
            controller: 'googlegroupPropertiesController'
        })
        .state('root.announcement.config.board', {
            url: '/announcement/config/board',
            templateUrl: 'app/components/announcement/config/board/board.html',
            controller: 'boardPropertiesController'
        })

});