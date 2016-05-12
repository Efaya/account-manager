(function(angular) {
    angular.module("accountManagerApp.controllers", []);
    angular.module("accountManagerApp.services", []);
    angular.module("accountManagerApp.directives", []);
    angular.module("accountManagerApp.filters", []);
    angular.module("accountManagerApp", ["ngResource", "ngRoute", "chart.js", "accountManagerApp.controllers", "accountManagerApp.services", "accountManagerApp.directives", "accountManagerApp.filters"]).config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
                when('/import', {
                    templateUrl: 'resources/partials/import.html',
                    controller: 'ImportCtrl'
                })
                .when('/records', {
                    templateUrl: 'resources/partials/records.html',
                    controller: 'RecordsCtrl'
                })
                .when('/category', {
                    templateUrl: 'resources/partials/category.html',
                    controller: 'CategoryCtrl'
                })
                .when('/home', {
                    templateUrl: 'resources/partials/home.html',
                    controller: 'HomeCtrl'
                })
                .otherwise({
                    redirectTo: '/home'
                });
        }]);
}(angular));