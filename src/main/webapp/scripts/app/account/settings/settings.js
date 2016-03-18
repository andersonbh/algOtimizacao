'use strict';

angular.module('algotimizacaoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settings', {
                parent: 'account',
                url: '/settings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Configurações'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/settings/settings.html',
                        controller: 'SettingsController'
                    }
                },
                resolve: {

                }
            });
    });
