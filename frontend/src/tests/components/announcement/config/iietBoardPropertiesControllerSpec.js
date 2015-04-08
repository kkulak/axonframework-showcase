/**
 * Created by novy on 08.04.15.
 */
/**
 * Created by novy on 08.04.15.
 */

describe('iietBoardPropertiesController should', function () {

    var iietBoardProperties = {
        username: "username",
        password: "password",
        loginUrl: "http://accounts.iiet.pl/students/sign_in",
        boardUrl: "https://forum.iiet.pl/",
        boardId: "57"
    };

    var scope;
    var IIETBoardPropertiesMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        IIETBoardPropertiesMock = jasmine.createSpyObj('IIETBoardProperties', ['get', 'update']);

        $controller('boardPropertiesController', {
            $scope: scope,
            IIETBoardProperties: IIETBoardPropertiesMock
        });

    }));

    it('fetch properties from resource', function () {

        IIETBoardPropertiesMock.get.and.callFake(function (successCallback) {
            successCallback(iietBoardProperties);
        });

        scope.fetchProperties();
        expect(scope.properties).toBe(iietBoardProperties);
    });

    it("update resource with scope properties", function () {

        scope.properties = iietBoardProperties;

        scope.updateProperties();

        expect(IIETBoardPropertiesMock.update).toHaveBeenCalledWith(iietBoardProperties);
    });

});