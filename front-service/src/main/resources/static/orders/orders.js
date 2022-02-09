angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8701/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    }

    $scope.loadOrders();
});