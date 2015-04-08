/**
 * Created by novy on 07.04.15.
 */

var PublisherPropertiesController = function (scope, propertiesResource) {
    this.fetchProperties = function () {
        propertiesResource.get(function (fetchedProperties) {
            scope.properties = fetchedProperties;
        });
    };

    this.updateProperties = function () {
        propertiesResource.update(scope.properties)
    };

    scope.properties = {};

    scope.fetchProperties = this.fetchProperties;
    scope.updateProperties = this.updateProperties;

    scope.fetchProperties();
};

var facebookPropertiesController = function ($scope, FacebookProperties) {
    new PublisherPropertiesController($scope, FacebookProperties);
};

var twitterPropertiesController = function ($scope, TwitterProperties) {
    new PublisherPropertiesController($scope, TwitterProperties);
};

var googlegroupPropertiesController = function ($scope, GoogleGroupProperties) {
    new PublisherPropertiesController($scope, GoogleGroupProperties);
};

var boardPropertiesController = function ($scope, IIETBoardProperties) {
    new PublisherPropertiesController($scope, IIETBoardProperties);
};

angular.module('publisherConfigControllers', [])
    .controller('facebookPropertiesController', facebookPropertiesController)
    .controller('twitterPropertiesController', twitterPropertiesController)
    .controller('googlegroupPropertiesController', googlegroupPropertiesController)
    .controller('boardPropertiesController', boardPropertiesController);