'use strict';

angular.module('algotimizacaoApp')
    .controller('SimplexController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
