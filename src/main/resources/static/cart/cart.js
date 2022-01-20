angular.module('app').controller('cartController', function ($scope, $http, $location, $window, $localStorage) {
    const contextPath = 'http://localhost:8187/app/';

         $scope.loadCartProducts = function () {
             $http.get(contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId)
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
            $http.get(contextPath + 'cart/' + $localStorage.springWebGuestCartId + '/clear')
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
                url: contextPath + 'api/v1/orders',
                method: 'POST',
                data: $scope.orderDetails
            }).then(function(response) {
                $scope.clearCart();
                $scope.orderDetails = null;
                $window.location.reload();
            });
         };
         $scope.loadCartProducts();
});