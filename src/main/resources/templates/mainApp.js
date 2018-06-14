angular.module('app', [])
.controller('productionLines', function($scope, $http) {
    $http.get('http://localhost:8080/productionLines').
        then(function(response) {
            $scope.productionLines = response.data;
        });
});