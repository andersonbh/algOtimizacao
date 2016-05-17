'use strict';

angular.module('algotimizacaoApp')
    .controller('SimplexController', function ($scope, Principal) {
        $scope.indice = 0;
        $scope.numvariaveis = [];
        $scope.funcaoObjetivo = [];
        $scope.valoresRestricoes = [$scope.numvariaveis];
        $scope.restricoes = [];
        $scope.valoresVariaveis = [];

        $scope.init = function(){
            $scope.numvariaveis.push(0);
            $scope.restricoes.push(1);

        };

        $scope.addVar = function(){
            $scope.numvariaveis.push($scope.numvariaveis.length);
            $scope.funcaoObjetivo[$scope.numvariaveis.length] = 0;
            $scope.valoresRestricoes[$scope.restricoes.length][$scope.numvariaveis.length] = 0;
        };

        $scope.addRestricao = function(){
          $scope.restricoes.push($scope.restricoes.length + 1);
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });


    });
