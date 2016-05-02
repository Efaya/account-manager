(function(angular) {
    var AccountRecordFactory = function($resource) {
        return $resource('/accountRecord/:id', {id: '@id'});
    };
    AccountRecordFactory.$inject = ['$resource'];

    var CategoryFactory = function($resource) {
        return $resource('/category/:id', {id: '@id'});
    };
    CategoryFactory.$inject = ['$resource'];

    angular.module("accountManagerApp.services").factory("AccountRecord", AccountRecordFactory);
    angular.module("accountManagerApp.services").factory("Category", CategoryFactory);
}(angular));