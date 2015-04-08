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
    var ToastingServiceMock;

    beforeEach(module('publisherConfigControllers'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        IIETBoardPropertiesMock = jasmine.createSpyObj('IIETBoardProperties', ['get', 'update']);
        ToastingServiceMock = jasmine.createSpyObj('ToastingService', ['showSuccessToast', 'showErrorToast'])

        $controller('boardPropertiesController', {
            $scope: scope,
            IIETBoardProperties: IIETBoardPropertiesMock,
            ToastingService: ToastingServiceMock
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

        expect(IIETBoardPropertiesMock.update).toHaveBeenCalled();
    });

    it("should display success toast after successful update", function () {

        IIETBoardPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            successCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showSuccessToast).toHaveBeenCalled();
    });

    it("should display error toast when update fails", function () {

        IIETBoardPropertiesMock.update.and.callFake(function (properties, successCallback, errorCallback){
            errorCallback();
        });

        scope.updateProperties();

        expect(ToastingServiceMock.showErrorToast).toHaveBeenCalled();
    });

});