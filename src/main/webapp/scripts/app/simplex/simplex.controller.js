'use strict';

angular.module('algotimizacaoApp')
    .controller('SimplexController', function ($scope, Principal) {
        $scope.indice = 0;
        $scope.variaveis = [];
        $scope.restricoes = [];

        $scope.init = function(){
            $scope.variaveis.push(0);
            $scope.restricoes.push(1)
        };

        $scope.addVar = function(){
            $scope.variaveis.push($scope.variaveis.length);
        };

        $scope.addRestricao = function(){
          $scope.restricoes.push($scope.restricoes.length + 1);
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
