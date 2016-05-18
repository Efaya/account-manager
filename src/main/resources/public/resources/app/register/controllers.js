(function(angular) {
    var RegistrationController = function($scope, Registration, $timeout) {
        $scope.showRegisterForm = false;
        $scope.registrationSuccess = false;
        $scope.registrationError = false;
        $scope.registrationErrorMsg = '';

        function init() {
            $scope.register = {
                username: undefined,
                password: undefined,
                confirm: undefined,
                email: undefined
            };
        }

        init();

        $scope.registration = function() {
            Registration.save($scope.register, function(res) {
                init();
                $scope.registrationSuccess = true;
                $scope.showRegisterForm = false;
                $timeout(function() {
                    $scope.registrationSuccess = false;
                }, 5000);
            }, function(err) {
                if (err.status === 400) {
                    $scope.registrationError = true;
                    $scope.registrationErrorMsg = 'Une erreur s\'est produite, veuillez réessayer dans quelques instants';
                } else {
                    $scope.registrationError = true;
                    $scope.registrationErrorMsg = err.data.message;
                }
                $timeout(function() {
                    $scope.registrationError = false;
                    $scope.registrationErrorMsg = '';
                }, 5000);
            });
        };
    };
    RegistrationController.$inject = ['$scope', 'Registration', '$timeout'];

    angular.module("registerManagerApp.controllers").controller("RegistrationCtrl", RegistrationController);
}(angular));