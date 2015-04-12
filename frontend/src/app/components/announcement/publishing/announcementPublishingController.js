/**
 * Created by novy on 12.04.15.
 */

var announcementPublishingController = function ($scope, announcementPublishingService, ToastingService) {
    var toastData = {
        success: {
            title: 'Success',
            message: 'Announcement published!'
        },
        error: {
            title: 'Error', message: 'Couldn\'t publish announcement!'
        }
    };

    $scope.availablePublishers = [
        {name: "Facebook", ticked: "true", value: "facebook"},
        {name: "Twiter", ticked: "true", value: "twitter"},
        {name: "Google Group", ticked: "true", value: "google_group"},
        {name: "IIET Board", ticked: "true", value: "iiet_board"}
    ];

    $scope.announcement = {
        title: "",
        content: "",
        publishers: []
    };

    this.publishAnnouncement = function () {
        announcementPublishingService.publishAnnouncement({
                title: $scope.announcement.title,
                content: $scope.announcement.content,
                publishers: getPublisherValues($scope.announcement.publishers)
            },
            function(data) {
                ToastingService.showSuccessToast(toastData.success)
            },
            function (data) {
                ToastingService.showErrorToast(toastData.error)
            }
        )
    };

    function getPublisherValues(publishers) {
        return publishers.map(
            function(publisher) {
                return publisher.value;
            }
        )
    }

    $scope.publishAnnouncement = this.publishAnnouncement;
};

var announcementPublishingService = function ($http) {
    var publishingEndpoint = 'http://localhost:8080/announcements';

    this.publishAnnouncement = function (announcement, successCallback, failureCallback) {
        $http.post(publishingEndpoint, announcement)
            .success(function (data) {
                successCallback(data)
            })
            .error(function (data) {
                failureCallback(data)
            })
    }
};


angular.module('announcementPublishingController', ['toastingService'])
    .service('announcementPublishingService', announcementPublishingService)
    .controller('announcementPublishingController', announcementPublishingController);