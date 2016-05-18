(function(angular) {
    var RegistrationFactory = function($resource) {
        return $resource('/registration/:id', {id: '@id'});
    };
    RegistrationFactory.$inject = ['$resource'];

    angular.module("registerManagerApp.services").factory("Registration", RegistrationFactory);
}(angular));