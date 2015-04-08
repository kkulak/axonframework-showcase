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
    var ToastingServiceMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        GoogleGroupPropertiesMock = jasmine.createSpyObj('GoogleGroupProperties', ['get', 'update']);
        ToastingServiceMock = jasmine.createSpyObj('ToastingService', ['showSuccessToast', 'showErrorToast'])

        $controller('googlegroupPropertiesController', {
            $scope: scope,
            GoogleGroupProperties: GoogleGroupPropertiesMock,
            ToastingService: ToastingServiceMock
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

        expect(GoogleGroupPropertiesMock.update).toHaveBeenCalled();
    });

    it("should display success toast after successful update", function () {

        GoogleGroupPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            successCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showSuccessToast).toHaveBeenCalled();
    });

    it("should display error toast when update fails", function () {

        GoogleGroupPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            errorCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showErrorToast).toHaveBeenCalled();
    });

});