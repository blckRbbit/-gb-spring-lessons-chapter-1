angular.module('app').controller('ordersController', function ($scope, $http) {
//'http://localhost:8701/app/core'
    const contextPath = 'http://localhost:8701/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    }

    $scope.loadOrders();
});