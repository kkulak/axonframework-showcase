/**
 * Created by novy on 07.04.15.
 */

// todo: could be set per controller
var toastData = {
    success: {
        title: 'Success',
        message: 'Successfully updated properties!'
    },
    error: {
        title: 'Error', message: 'Couldn\'t update properties!'
    }
};

var PublisherPropertiesController = function (scope, propertiesResource, ToastingService, toastData) {
    this.fetchProperties = function () {
        propertiesResource.get(function (fetchedProperties) {
            scope.properties = fetchedProperties;
        });
    };

    this.updateProperties = function () {
        propertiesResource.update(
            scope.properties,
            function (data) {
                ToastingService.showSuccessToast(toastData.success)
            },
            function (err) {
                ToastingService.showErrorToast(toastData.error)
            }
        );
    };

    scope.properties = {};

    scope.fetchProperties = this.fetchProperties;
    scope.updateProperties = this.updateProperties;

    scope.fetchProperties();
};

var facebookPropertiesController = function ($scope, FacebookProperties, ToastingService) {
    new PublisherPropertiesController($scope, FacebookProperties, ToastingService, toastData);
};

var twitterPropertiesController = function ($scope, TwitterProperties, ToastingService) {
    new PublisherPropertiesController($scope, TwitterProperties, ToastingService, toastData);
};

var googlegroupPropertiesController = function ($scope, GoogleGroupProperties, ToastingService) {
    new PublisherPropertiesController($scope, GoogleGroupProperties, ToastingService, toastData);
};

var boardPropertiesController = function ($scope, IIETBoardProperties, ToastingService) {
    new PublisherPropertiesController($scope, IIETBoardProperties, ToastingService, toastData);
};

angular.module('publisherConfigControllers', ['toastingService'])
    .controller('facebookPropertiesController', facebookPropertiesController)
    .controller('twitterPropertiesController', twitterPropertiesController)
    .controller('googlegroupPropertiesController', googlegroupPropertiesController)
    .controller('boardPropertiesController', boardPropertiesController)