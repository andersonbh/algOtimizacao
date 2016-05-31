'use strict';

angular.module('algotimizacaoApp')
    .controller('SimplexController', function ($scope, Principal, $http) {
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

        $scope.resolver = function () {
            //copia os valores para remover a posicao 0 random
            var restricoes = angular.copy($scope.valoresRestricoes);
            //Remove a posicao 0 do vetor
            restricoes.shift();
            //passa para json cabulosamente
            var jsonTexto = JSON.stringify(restricoes);

            $http.post("/simplex/resolver",
                {
                    fo: $scope.funcaoObjetivo,
                    res: jsonTexto,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                console.log(response.data);
                console.log('aeeeee');
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });
        };

    });
