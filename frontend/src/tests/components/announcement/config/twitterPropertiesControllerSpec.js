/**
 * Created by novy on 08.04.15.
 */

/**
 * Created by novy on 08.04.15.
 */

describe('twitterPropertiesController should', function () {

    var twitterProperties = {
        consumerKey: 'consumerKey',
        consumerSecret: 'consumerSecret'
    };

    var scope;
    var TwitterPropertiesMock;
    var ToastingServiceMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        TwitterPropertiesMock = jasmine.createSpyObj('TwitterProperties', ['get', 'update']);
        ToastingServiceMock = jasmine.createSpyObj('ToastingService', ['showSuccessToast', 'showErrorToast'])

        $controller('twitterPropertiesController', {
            $scope: scope,
            TwitterProperties: TwitterPropertiesMock,
            ToastingService: ToastingServiceMock
        });

    }));

    it('fetch properties from resource', function () {

        TwitterPropertiesMock.get.and.callFake(function (successCallback) {
            successCallback(twitterProperties);
        });

        scope.fetchProperties();
        expect(scope.properties).toBe(twitterProperties);
    });

    it("update resource with scope properties", function () {

        scope.properties = twitterProperties;

        scope.updateProperties();

        expect(TwitterPropertiesMock.update).toHaveBeenCalled();
    });

    it("should display success toast after successful update", function () {

        TwitterPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
           successCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showSuccessToast).toHaveBeenCalled();
    });

    it("should display error toast when update fails", function () {

        TwitterPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            errorCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showErrorToast).toHaveBeenCalled();
    });

});