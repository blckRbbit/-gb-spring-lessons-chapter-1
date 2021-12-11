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

    $scope.updateProduct = function (id) {
        $http.put(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts(currentPageIndex);
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