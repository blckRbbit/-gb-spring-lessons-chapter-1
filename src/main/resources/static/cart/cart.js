angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8187/app/api/v1';
         $scope.loadCartProducts = function () {
             $http({
                 method: 'GET',
                 url: contextPath + '/cart',
             }).then(function(response) {
                 $scope.cartProducts = response.data;
                 $scope.totalCost = $scope.getTotalCost();
             });
         };

         $scope.clearCart = function() {
            $http.delete(contextPath + '/cart')
                .then(function (response) {
                    alert('Cart is empty!');
                    $scope.loadCartProducts();
                    $location.path('/store');
                });
         }

         $scope.getTotalCost = function() {
            let totalCost = 0;
            for(let i = 0; i < $scope.cartProducts.length; i++) {
                let p = $scope.cartProducts[i];
                totalCost += p.cost;
            }
            return totalCost;
         }

         $scope.deleteProduct = function (id) {
         // todo удаление по id имеет неприятный баг - из корзины удаляются все, кроме одного,
         //todo элементы с одинаковым product.id, фиксить не стал - т.к такой реализации корзины на практике все равно не встретится
         //todo как вариант, нужно удалять не по id, а по индексу списка...
            $http.delete(contextPath + '/cart/' + id)
                .then(function (response) {
                    $scope.loadCartProducts();
                });
         }

         $scope.loadCartProducts();
});