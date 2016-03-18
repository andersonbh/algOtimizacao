 'use strict';

angular.module('algotimizacaoApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-algotimizacaoApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-algotimizacaoApp-params')});
                }
                return response;
            }
        };
    });
