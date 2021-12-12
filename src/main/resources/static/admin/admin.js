angular.module('app').controller('adminController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8187/app/api/v1';

    $scope.prepareProductForUpdate = function() {
        $http.get(contextPath + '/products/' + $routeParams.productId)
            .then(function successCallback (response) {
                $scope.updated_product = response.data;
        }, function failureCallback (response) {
                alert(response.data.messages);
                $location.path('/store');
        });
    }

    $scope.updateProduct = function () {
        if ($scope.new_product == null) {
            alert('All form fields must be filled');
            return;
        }
        $http.put(contextPath + '/products', $scope.updated_product)
            .then(function successCallback (response) {
                $scope.updated_product = null;
                alert('Success!');
                $location.path('/store');
        }, function failureCallback (response) {
                alert(response.data.messages);
        });
    }

    $scope.createProduct = function() {
        if ($scope.new_product == null) {
            alert('All form fields must be filled');
            return;
        }
        $http.post(contextPath + '/products/', $scope.new_product)
            .then(function successCallback (response) {
                $scope.new_product = null;
                alert('Success!');
                $location.path('/store');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    }

    if($routeParams.productId != null) {
        $scope.prepareProductForUpdate();
    }

});
