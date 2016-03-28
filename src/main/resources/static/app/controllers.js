(function(angular) {
    var AccountManagerController = function($scope, AccountRecord, Category) {
        AccountRecord.query(function(response) {
            $scope.records = response ? response : [];
        });

        Category.query(function(response) {
            $scope.categories = response ? response : [];
        });
    };

    AccountManagerController.$inject = ['$scope', 'AccountRecord', 'Category'];
    angular.module("accountManagerApp.controllers").controller("AccountManagerController", AccountManagerController);
}(angular));