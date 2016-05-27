(function(angular) {
    angular.module("accountManagerApp.controllers", []);
    angular.module("accountManagerApp.services", []);
    angular.module("accountManagerApp.directives", []);
    angular.module("accountManagerApp.filters", []);
    angular.module("accountManagerApp", ["ngResource", "ngRoute", "chart.js", "accountManagerApp.controllers", "accountManagerApp.services", "accountManagerApp.directives", "accountManagerApp.filters"]).config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
                when('/import', {
                    templateUrl: 'partials/import',
                    controller: 'ImportCtrl'
                })
                .when('/records', {
                    templateUrl: 'partials/records',
                    controller: 'RecordsCtrl'
                })
                .when('/category', {
                    templateUrl: 'partials/category',
                    controller: 'CategoryCtrl'
                })
                .when('/home', {
                    templateUrl: 'partials/home',
                    controller: 'HomeCtrl'
                })
                .when('/users', {
                    templateUrl: 'partials/users',
                    controller: 'UsersCtrl'
                })
                .otherwise({
                    redirectTo: '/home'
                });
        }]);
}(angular));