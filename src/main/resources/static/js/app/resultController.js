'use strict';

taskApp.controller('resultController', function ($scope, $stateParams) {

   $scope.weathers = JSON.parse($stateParams.weathers);

   $scope.query =  $stateParams.query;
})