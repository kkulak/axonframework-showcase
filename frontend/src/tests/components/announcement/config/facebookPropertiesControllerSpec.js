/**
 * Created by novy on 08.04.15.
 */

describe('facebookPropertiesController should', function () {

    var facebookProps = {
        clientId: 'anotherId',
        clientSecret: 'anotherSecret'
    };

    var scope;
    var FacebookPropertiesMock;
    var ToastingServiceMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        FacebookPropertiesMock = jasmine.createSpyObj('FacebookProperties', ['get', 'update']);
        ToastingServiceMock = jasmine.createSpyObj('ToastingService', ['showSuccessToast', 'showErrorToast']);

        $controller('facebookPropertiesController', {
            $scope: scope,
            FacebookProperties: FacebookPropertiesMock,
            ToastingService: ToastingServiceMock
        });

    }));

    it('fetch properties from resource', function () {

        FacebookPropertiesMock.get.and.callFake(function (successCallback) {
            successCallback(facebookProps);
        });

        scope.fetchProperties();
        expect(scope.properties).toBe(facebookProps);
    });

    it("update resource with scope properties", function () {

        scope.properties = facebookProps;

        scope.updateProperties();

        expect(FacebookPropertiesMock.update).toHaveBeenCalled();
    });

    it("should display success toast after successful update", function () {

        FacebookPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            successCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showSuccessToast).toHaveBeenCalled();
    });

    it("should display error toast when update fails", function () {

        FacebookPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            errorCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showErrorToast).toHaveBeenCalled();
    });

});