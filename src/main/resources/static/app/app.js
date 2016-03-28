(function(angular) {
    angular.module("accountManagerApp.controllers", []);
    angular.module("accountManagerApp.services", []);
    angular.module("accountManagerApp", ["ngResource", "accountManagerApp.controllers", "accountManagerApp.services"]);
}(angular));