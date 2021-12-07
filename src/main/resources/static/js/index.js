angular.module('app', []).controller('productController', function ($scope, $http) {
     const contextPath = 'http://localhost:8187/app/api/v1';
     $scope.loadProducts = function (pageIndex = 1) {
            $http({
                method: 'GET',
                url: contextPath + '/products',
                params: {
                    min_cost: $scope.filter ? $scope.filter.min_cost : null,
                    max_cost: $scope.filter ? $scope.filter.max_cost : null,
                    id: $scope.filter ? $scope.filter.id : null,
                    title: $scope.filter ? $scope.filter.title : null
                }
            }).then(function(response) {
                console.log(response.data)
                $scope.ProductsPage = response.data.content;
            });
        };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }
    $scope.loadProducts();
});