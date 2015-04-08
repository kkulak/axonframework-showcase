/**
 * Created by novy on 08.04.15.
 */

describe('googleGroupPropertiesController should', function () {

    var googleGroupProperties = {
        username: "username",
        host: "smtp.google.com",
        password: "password",
        googleGroupAddress: "knbittestgroup@googlegroups.com"
    };

    var scope;
    var GoogleGroupPropertiesMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        GoogleGroupPropertiesMock = jasmine.createSpyObj('GoogleGroupProperties', ['get', 'update']);

        $controller('googlegroupPropertiesController', {
            $scope: scope,
            GoogleGroupProperties: GoogleGroupPropertiesMock
        });

    }));

    it('fetch properties from resource', function () {

        GoogleGroupPropertiesMock.get.and.callFake(function (successCallback) {
            successCallback(googleGroupProperties);
        });

        scope.fetchProperties();
        expect(scope.properties).toBe(googleGroupProperties);
    });

    it("update resource with scope properties", function () {

        scope.properties = googleGroupProperties;

        scope.updateProperties();

        expect(GoogleGroupPropertiesMock.update).toHaveBeenCalledWith(googleGroupProperties);
    });

});