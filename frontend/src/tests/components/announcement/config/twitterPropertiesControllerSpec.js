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

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        TwitterPropertiesMock = jasmine.createSpyObj('TwitterProperties', ['get', 'update']);

        $controller('twitterPropertiesController', {
            $scope: scope,
            TwitterProperties: TwitterPropertiesMock
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

        expect(TwitterPropertiesMock.update).toHaveBeenCalledWith(twitterProperties);
    });

});