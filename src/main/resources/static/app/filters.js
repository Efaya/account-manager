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

    angular.module("accountManagerApp.filters").filter('categoryName', categoryName);
}(angular));