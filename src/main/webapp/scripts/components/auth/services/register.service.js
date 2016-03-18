'use strict';

angular.module('algotimizacaoApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


