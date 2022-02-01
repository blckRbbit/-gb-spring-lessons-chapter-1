angular.module('app').controller('adminController', function ($scope, $http, $routeParams, $location) {
//    'http://localhost:8701/core/app/api/v1'
    const contextPath = 'http://localhost:8701/core/api/v1';

    $scope.prepareProductForUpdate = function() {
        $http.get(contextPath + '/products/' + $routeParams.productId)
            .then(function successCallback (response) {
                $scope.updated_product = response.data;
        }, function failureCallback (response) {
                alert(response.data.errorFieldsMessages);
                $location.path('/store');
        });
    }

    $scope.updateProduct = function () {
        if ($scope.updated_product == null) {
            alert('All form fields must be filled');
            return;
        }
        $http.put(contextPath + '/products', $scope.updated_product)
            .then(function successCallback (response) {
                $scope.updated_product = null;
                alert('Success!');
                $location.path('/store');
        }, function failureCallback (response) {
                alert(response.data.errorFieldsMessages);
        });
    }

    $scope.createProduct = function() {
        if ($scope.new_product == null) {
            alert('All form fields must be filled');
            return;
        }
        $http.post(contextPath + '/products/', $scope.new_product)
            .then(function successCallback (response) {
            console.log($scope.new_product);
                $scope.new_product = null;
                alert('Success!');
                $location.path('/store');
            }, function failureCallback (response) {
                alert(response.data.errorFieldsMessages);
            });
    }

    if($routeParams.productId != null) {
        $scope.prepareProductForUpdate();
    }

});
