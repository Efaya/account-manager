(function(angular) {
    var AccountManagerController = function($scope, AccountRecord, Category) {
        $scope.months = ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"];
        $scope.years = ["2016"];
        AccountRecord.query(function(response) {
            $scope.records = [];
            for (var recordKey in response) {
                var record = response[recordKey];
                var recordDate = new Date(record.date);
                if (!$scope.records[recordDate.getFullYear()]) {
                    $scope.records[recordDate.getFullYear()] = [];
                }
                if (!$scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]]) {
                    $scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]] = [];
                }
                $scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]].push(record);
            }
        });

        Category.query(function(response) {
            $scope.categories = response ? response : [];
        });
    };
    AccountManagerController.$inject = ['$scope', 'AccountRecord', 'Category'];

    var ImportCtrl = function($scope, $http, AccountRecord) {
        $scope.ctx = {
            categoryNewLabel: '',
            categoryNewValue: '',
            csvFile: undefined

        };
        $scope.selectedCategory = {};

        $scope.import = function () {
            var fd = new FormData();
            fd.append('file', $scope.ctx.csvFile);


            $http.post('/accountRecord', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).success(function () {
                console.log('Import success!');
                AccountRecord.query(function(response) {
                    $scope.records = [];
                    for (var recordKey in response) {
                        var record = response[recordKey];
                        var recordDate = new Date(record.date);
                        if (!$scope.records[recordDate.getFullYear()]) {
                            $scope.records[recordDate.getFullYear()] = [];
                        }
                        if (!$scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]]) {
                            $scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]] = [];
                        }
                        $scope.records[recordDate.getFullYear()][$scope.months[recordDate.getMonth()]].push(record);
                    }
                });
            }).error(function () {
                console.log('ERROR : Import failed!');
            });
        };
    };

    ImportCtrl.$inject = ['$scope', '$http', 'AccountRecord'];

    var CategoryCtrl = function($scope, $http, Category) {
        $scope.selectCategory = function(category) {
            $scope.selectedCategory = category;
        };

        $scope.addNewCategory = function() {
            var category = {values:[], label: $scope.ctx.categoryNewLabel};
            $scope.ctx.categoryNewLabel = '';
            saveCategory(category, function(category) {
                console.log('Category created ' + category.label + ' (' + category.id + ') saved');
                $scope.categories.push(category);
            });
        };

        $scope.removeCategory = function(idx) {
            var removedCategory = $scope.categories.splice(idx, 1);
            $scope.selectedCategory = {};
            removeCategory(removedCategory[0].id);
        };

        $scope.removeCategoryValue = function(idx) {
            $scope.selectedCategory.values.splice(idx, 1);
            saveCategory($scope.selectedCategory, function() {
                console.log('Category saved ' + category.label + ' (' + category.id + ') saved');
            });
        };

        $scope.addNewCategoryValue = function() {
            $scope.selectedCategory.values.push($scope.ctx.categoryNewValue);
            $scope.ctx.categoryNewValue = '';
            saveCategory($scope.selectedCategory, function() {
                console.log('Category saved ' + category.label + ' (' + category.id + ') saved');
            });
        };

        function saveCategory(category, callback) {
            Category.save({'id':category.id}, category, callback);
        }

        function removeCategory(id) {
            Category.remove({'id':id}, function() {
                console.log('Category removed ' + id + ' saved');
            });
        }
    };
    CategoryCtrl.$inject = ['$scope', '$http', 'Category'];

    var HomeCtrl = function($scope, AccountRecord) {

    };
    HomeCtrl.$inject = ['$scope', 'AccountRecord'];

    var RecordsCtrl = function($scope, AccountRecord) {
        $scope.removeRecord = function(idx) {
            var removedRecord = $scope.records.splice(idx, 1);
            AccountRecord.remove({'id':removedRecord[0].id}, function() {
                console.log('Record removed ' + removedRecord[0].id + ' saved');
            });
        };

        $scope.updateCategory = function(record) {
            AccountRecord.save({'id':record.id}, record,  function() {
                console.log('Record updated : ' + record.id);
            });
        };
    };
    RecordsCtrl.$inject = ['$scope', 'AccountRecord'];

    angular.module("accountManagerApp.controllers").controller("AccountManagerController", AccountManagerController);
    angular.module("accountManagerApp.controllers").controller("HomeCtrl", HomeCtrl);
    angular.module("accountManagerApp.controllers").controller("ImportCtrl", ImportCtrl);
    angular.module("accountManagerApp.controllers").controller("CategoryCtrl", CategoryCtrl);
    angular.module("accountManagerApp.controllers").controller("RecordsCtrl", RecordsCtrl);
}(angular));