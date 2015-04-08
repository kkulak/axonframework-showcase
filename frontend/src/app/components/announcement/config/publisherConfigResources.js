/**
 * Created by novy on 07.04.15.
 */

var publisherConfigResources = angular.module('publisherConfigResources', ['ngResource']);

var ConfigurationResource = function ($resource, endpointUrl) {
    var allowedMethods = {
        'get': {
            method: 'GET'
        },
        'update': {
            method: 'PUT'
        }
    };

    return $resource(endpointUrl, {}, allowedMethods);
};

var FacebookProperties = function ($resource) {
    return ConfigurationResource($resource, 'http://localhost:8080/announcements/config/facebook');
};

var TwitterProperties = function ($resource) {
    return ConfigurationResource($resource, 'http://localhost:8080/announcements/config/twitter');
};

var GoogleGroupProperties = function ($resource) {
    return ConfigurationResource($resource, 'http://localhost:8080/announcements/config/googlegroup');
};

var IIETBoardProperties = function ($resource) {
    return ConfigurationResource($resource, 'http://localhost:8080/announcements/config/iietboard');
};

publisherConfigResources
    .factory('FacebookProperties', FacebookProperties)
    .factory('TwitterProperties', TwitterProperties)
    .factory('GoogleGroupProperties', GoogleGroupProperties)
    .factory('IIETBoardProperties', IIETBoardProperties);
