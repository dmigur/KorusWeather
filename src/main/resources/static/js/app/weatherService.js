taskApp.factory('weatherService', function ($resource, $http, $q) {

    var service = {};

    var urls = {
        getWeather: "api/weather",
        getSettings: "api/settings",
        getAuth: "api/auth"
    };

    service.getWeatherByCoords = function (task) {

        var url = urls.getWeather;
        var defer = $q.defer();

        var response = $http({
            method: "get",
            url: url,
            params: {
                callback: task.callback,
                longitude: task.longitude,
                latitude: task.latitude
            },
            cache: false
        });

        response.success(function (responce) {
            defer.resolve(responce);
        }).error(function (responce) {
            defer.reject(responce);
        });

        return defer.promise;
    };

    service.getWeatherByCity = function (task) {

        var url = urls.getWeather;
        var defer = $q.defer();

        var response = $http({
            method: "get",
            url: url,
            params: {
                callback: task.callback,
                city: task.city
            },
            cache: false
        });

        response.success(function (responce) {
            defer.resolve(responce);
        }).error(function (responce) {
            defer.reject(responce);
        });

        return defer.promise;
    };


    service.getSettings = function () {
        var url = urls.getSettings;
        var defer = $q.defer();

        var response = $http({
            method: "get",
            url: url,
            cache: false
        });

        response.success(function (responce) {
            defer.resolve(responce);
        }).error(function (responce) {
            defer.reject(responce);
        });

        return defer.promise;
    };


    service.getAuth = function () {
        var url = urls.getAuth;
        var defer = $q.defer();

        var response = $http({
            method: "get",
            url: url,
            cache: false
        });

        response.success(function (responce) {
            defer.resolve(responce);
        }).error(function (responce) {
            defer.reject(responce);
        });

        return defer.promise;
    };


    return service;
});