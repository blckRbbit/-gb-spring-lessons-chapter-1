angular.module('app').controller('adminController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8187/app/api/v1';

    $scope.prepareProductForUpdate = function() {
        $http.get(contextPath + '/products/' + $routeParams.productId)
            .then(function successCallback (response) {
                $scope.updated_product = response.data;
        }, function failureCallback (response) {
                alert(response.data.message);
                $location.path('/store');
        });
    }

    $scope.updateProduct = function () {
        $http.put(contextPath + '/products', $scope.updated_product)
            .then(function successCallback (response) {
                $scope.updated_product = null;
                alert('Success!');
                $location.path('/store');
        }, function failureCallback (response) {
                alert(response.data.message);
        });
    }

    if($routeParams.productId != null) {
        $scope.prepareProductForUpdate();
    }

});
//    $scope.createProduct = function() {
//        $http.post(contextPath + '/products/', $scope.new_product)
//            .then(function successCallback (response) {
//                $scope.new_product = null;
//                alert('Success!');
//                $location.path('/store');
//            }, function failureCallback(response) {
//                alert(response.data.message);
//            }
//    }