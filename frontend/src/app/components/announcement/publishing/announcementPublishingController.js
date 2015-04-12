/**
 * Created by novy on 12.04.15.
 */


var announcementPublishingController = function ($scope) {
    $scope.availablePublishers = [
        {name: "Facebook", ticked: "true"},
        {name: "Twiter", ticked: "true"},
        {name: "Google Groupe", ticked: "true"},
        {name: "IIET Board", ticked: "true"}
    ];

    $scope.announcement = {
        title: "",
        content: "",
        publishers: {}
    };

    this.publishAnnouncement = function() {
        console.log($scope.announcement.publishers)
    };

    $scope.publishAnnouncement = this.publishAnnouncement;
};



    angular.module('announcementPublishingController', [])
        .controller('announcementPublishingController', announcementPublishingController);