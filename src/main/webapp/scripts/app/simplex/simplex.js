'use strict';

angular.module('algotimizacaoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('simplex', {
                parent: 'site',
                url: '/simplex',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/simplex/simplex.html',
                        controller: 'SimplexController'
                    }
                },
                resolve: {

                }
            });
    });
