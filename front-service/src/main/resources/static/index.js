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
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
         if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.springWebUser;
                    $http.defaults.headers.common.Authorization = '';
                    }
            } catch (e) {}
         }
        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }
        if (!$localStorage.springWebGuestCartId) {
            $http.get('http://localhost:8701/cart/api/v1/cart/generate')
                .then(function successCallback(response) {
                    $localStorage.springWebGuestCartId = response.data.value;
                });
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $rootScope, $http, $localStorage, $location, $window) {
    let tempForTopPerMonth = "";
    let tempForTopPerDay = "";

    $scope.tryToAuth = function () {
       $http.post('http://localhost:8701/auth/login', $scope.user)
           .then(function successCallback(response) {
               if (response.data.token) {
                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                 $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
                 $scope.user.username = null;
                 $scope.user.password = null;
                 $http.get('http://localhost:8701/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/merge')
                    .then(function successCallback(response) {
                    });
                 $window.location.reload();
           }
       }, function errorCallback(response) {
          });
    };

    $scope.tryToLogout = function () {
       $scope.clearUser();
       $scope.user = null;
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

    $scope.topProductsOfTheMonth = function() {
       $http.get('http://localhost:8701/recommendation/api/v1/recommendations/top/month')
           .then(function successCallback(response) {
              $scope.topProducts = response.data;
              for (let i = 0; i < $scope.topProducts.length -1; i++) {
                   if (i > 5) {break;}
                   let part = "  " + $scope.topProducts[i].productTitle + "(price: "
                   + $scope.topProducts[i].price + ", orders: "
                   + $scope.topProducts[i].quantity + ");  ";
                   tempForTopPerMonth += part;
              }
              $scope.topOfTheMonth = tempForTopPerMonth;
           });
   }

    $scope.topProductsOfTheDay = function() {
       $http.get('http://localhost:8701/recommendation/api/v1/recommendations/top/day')
           .then(function successCallback(response) {
              $scope.topProductsPerDay = response.data;
              for (let i = 0; i < $scope.topProductsPerDay.length - 1; i++) {
                if (i > 5) {break;}
                let part = "  " + $scope.topProductsPerDay[i].productTitle + "(price: "
                + $scope.topProductsPerDay[i].pricePerProduct + ", orders: "
                + $scope.topProductsPerDay[i].additionPerDay + ");  ";
                tempForTopPerDay += part;
              }
              $scope.topOfTheDay = tempForTopPerDay;
              $scope.window.reload();
           });
    }

    $scope.topProductsOfTheMonth();
    $scope.topProductsOfTheDay();
    let updateTopPerMonth = setInterval(() => $scope.topProductsOfTheMonth(), 60000);
    let updateTopPerDay = setInterval(() => $scope.topProductsOfTheDay(), 60000);
});
