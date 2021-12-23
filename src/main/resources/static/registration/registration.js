angular.module('app').controller('registrationController', function ($scope, $http, $location) {

    $scope.registerUser = function() {
        if ($scope.user == null) {
            alert('All form fields must be filled');
            return;
        }
        console.log($scope.user);
        $http.post('http://localhost:8187/registration', $scope.user)
            .then(function successCallback (response) {
                $scope.user = null;
                alert('Success!');
                $location.path('/store');
            }, function failureCallback (response) {
                alert(response);
            });
    }

});


