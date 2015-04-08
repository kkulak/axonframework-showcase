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

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        FacebookPropertiesMock = jasmine.createSpyObj('FacebookProperties', ['get', 'update']);

        $controller('facebookPropertiesController', {
            $scope: scope,
            FacebookProperties: FacebookPropertiesMock
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

        expect(FacebookPropertiesMock.update).toHaveBeenCalledWith(facebookProps);
    });

});