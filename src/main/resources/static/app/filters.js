(function(angular) {
    var categoryName = function () {
        return function(input, scope) {

            for (var key in scope.categories) {
                if (scope.categories[key].id === input) {
                    return scope.categories[key].label;
                }
            }

            return '';

        }

    };

    var reverse = function() {
        return function(items) {
            return items.slice().reverse();
        };
    };

    angular.module("accountManagerApp.filters").filter('categoryName', categoryName);
    angular.module("accountManagerApp.filters").filter('reverse', reverse);
}(angular));