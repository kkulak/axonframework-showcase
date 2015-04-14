/**
 * Created by novy on 13.04.15.
 */

var ToastingService = function (toaster) {

    this.showToast = function (toastType, toastData) {
        toaster.pop({
                type: toastType, title: toastData.title, body: toastData.message
            }
        )
    };

    this.showSuccessToast = function (toastData) {
        this.showToast('success', toastData)
    };

    this.showErrorToast = function (toastData) {
        this.showToast('error', toastData)
    };

};

angular.module('toastingService', ['toaster'])
    .service('ToastingService', ToastingService);