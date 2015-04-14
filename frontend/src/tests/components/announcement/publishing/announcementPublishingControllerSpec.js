/**
 * Created by novy on 13.04.15.
 */

describe('announcementPublishingController should', function () {

    var scope;
    var announcementPublishingServiceMock;
    var ToastingServiceMock;

    beforeEach(module('announcementPublishingController'));
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();

        announcementPublishingServiceMock = jasmine.createSpyObj('announcementPublishingService', ['publishAnnouncement']);
        ToastingServiceMock = jasmine.createSpyObj('ToastingService', ['showSuccessToast', 'showErrorToast']);

        $controller('announcementPublishingController', {
            $scope: scope,
            announcementPublishingService: announcementPublishingServiceMock,
            ToastingService: ToastingServiceMock
        });

    }));

    it('display success toast if service call succeeded', function () {

        announcementPublishingServiceMock.publishAnnouncement.and.callFake(function (announcement, successCallback, errorCallback) {
            successCallback();
        });

        scope.publishAnnouncement();

        expect(ToastingServiceMock.showSuccessToast).toHaveBeenCalled();
    });

    it("display error toast if service call failed", function () {

        announcementPublishingServiceMock.publishAnnouncement.and.callFake(function (announcement, successCallback, errorCallback) {
            errorCallback();
        });

        scope.publishAnnouncement();

        expect(ToastingServiceMock.showErrorToast).toHaveBeenCalled();
    });

});
