angular.module('app').controller('cartController', function ($scope, $http, $location, $window) {
    const contextPath = 'http://localhost:8187/app/api/v1';

         $scope.loadCartProducts = function () {
             $http.get(contextPath + '/cart')
                .then(function(response) {
                     var temp = 0;
                     $scope.items = response.data;
                     var obj = $scope.items.items;
                     angular.forEach(obj, function(val, key) {
                        temp += val.quantity;
                     });
                     $scope.totalItems = temp;
                });
         };

         $scope.disabledCheckout = function() {
            alert('To place an order, you need to log in to your account!');
         }

         $scope.clearCart = function() {
            $http.get(contextPath + '/cart/clear')
                .then(function (response) {
                    $scope.loadCartProducts();
                    $location.path('/store');
                });
         }

         $scope.createOrder = function() {
            if ($scope.orderDetails == null) {
                alert('All form fields must be filled');
                return;
            }
            $http({
                url: contextPath + '/orders',
                method: 'POST',
                data: $scope.orderDetails
            }).then(function(response) {
                $scope.loadOrders();
                $scope.clearCart();
                $scope.orderDetails = null;
                $window.location.reload();
            });
         };

         $scope.loadOrders = function() {
            $http.get(contextPath + '/orders')
                .then(function(response) {
                    $scope.myOrders = response.data;
                });
         }
         $scope.loadCartProducts();
         $scope.loadOrders();
});