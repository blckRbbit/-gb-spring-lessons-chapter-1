(function() {
    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'main/main.html',
                controller: 'mainController'
            })
            .when('/auth', {
               templateUrl: '/',
               controller: 'indexController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/admin/:productId', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            }).when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            }).when('/orders', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http) {

    }
})();

angular.module('app').controller('indexController', function ($scope, $rootScope, $http, $localStorage, $location) {
    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.tryToAuth = function () {
       $http.post('http://localhost:8187/app/auth', $scope.user)
           .then(function successCallback(response) {
               if (response.data.token) {
                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                 $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
                 $scope.user.username = null;
                 $scope.user.password = null;
           }
       }, function errorCallback(response) {
            console.log($scope.user);
          });
    };

    $scope.tryToLogout = function () {
       $scope.clearUser();
       if ($scope.user.username) {
         $scope.user.username = null;
       }
       if ($scope.user.password) {
         $scope.user.password = null;
       }
       $location.path('/');
    };

   $scope.clearUser = function () {
      delete $localStorage.springWebUser;
      $http.defaults.headers.common.Authorization = '';

   };

   $rootScope.isUserLoggedIn = function () {
       if ($localStorage.springWebUser) {
         return true;
       } else {
         return false;
       }
   };

});
