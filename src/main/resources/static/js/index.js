angular.module('app', []).controller('productController', function ($scope, $http) {
     const contextPath = 'http://localhost:8187/app/api/v1';
     let currentPageIndex = 1;
     $scope.loadProducts = function (pageIndex = 1) {
     currentPageIndex = pageIndex;
            $http({
                method: 'GET',
                url: contextPath + '/products',
                params: {
                    min_cost: $scope.filter ? $scope.filter.min_cost : null,
                    max_cost: $scope.filter ? $scope.filter.max_cost : null,
                    id: $scope.filter ? $scope.filter.id : null,
                    title: $scope.filter ? $scope.filter.title : null,
                    page: pageIndex
                }
            }).then(function(response) {
                $scope.pages = response.data;
                $scope.productsPage = response.data.content;
                $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.pages.totalPages);
            });
        };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts(currentPageIndex);
            });
    }

    $scope.saveProduct = function () {
        $http.post(contextPath + '/products')
            .then(function (response) {
                $scope.loadProducts(currentPageIndex);
            });
    }

    $scope.prepareProductForUpdate = function(productId) {
        $http.get(contextPath + '/products/' + productId)
            .then(function successCallback (response) {
                $scope.updated_product = response.data;
            }, function failureCallback (response) {
                alert(response.data.message);
            });
    }

    $scope.updateProduct = function () {
        $http.put(contextPath + '/products', $scope.updated_product)
            .then(function successCallback (response) {
                $scope.loadProducts(currentPageIndex);
                $scope.updated_product = null;
            }, function failureCallback (response) {
                alert(response.data.message);
            });
    }


    $scope.generatePagesIndexes = function(startPage, endPage) {
        let pages = [];
        for (let i = startPage; i < endPage + 1; i++) {
            pages.push(i);
        }
        return pages;
    }

    $scope.loadProducts();
});