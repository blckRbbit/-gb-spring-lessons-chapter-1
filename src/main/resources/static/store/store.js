angular.module('app').controller('storeController', function ($scope, $http, $location) {
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

         $scope.generatePagesIndexes = function(startPage, endPage) {
             let pages = [];
             for (let i = startPage; i < endPage + 1; i++) {
                 pages.push(i);
             }
             return pages;
         }

         $scope.navigateToAdminPage = function() {
            $location.path('/admin');
         }

         $scope.navigateToAdminPageFromProductId = function(productId) {
            $location.path('/admin/' + productId);
         }

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

         $scope.loadProducts();
});