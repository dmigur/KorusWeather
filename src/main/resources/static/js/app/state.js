'use strict';

taskApp.constant('constants', {

    version: '${project.version}',
    availableState: {
        workspace: {
            name: 'workspace',
            url: '/'
        }
    }
});

taskApp.config(function (constants, $stateProvider, $urlRouterProvider) {

    var availableState = constants.availableState;
    $stateProvider
        .state(availableState.workspace.name, {
                url: availableState.workspace.url,
                views: {
                    '': {
                        templateUrl: 'views/weather.html',
                        controller: 'mainController'
                    }
                }
            }
        ).state('results', {
        templateUrl: "/views/results.html",
        url: "/results/:query&:weathers",
        controller: 'resultController'
    })
    ;
    $urlRouterProvider.otherwise(availableState.workspace.url);
});

