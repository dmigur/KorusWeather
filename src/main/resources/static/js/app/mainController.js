'use strict';

taskApp.controller('mainController', function ($scope, $state, $timeout, $location, weatherService, $http, $q) {

    var Status = {
        Error: "Error",
        Success: "Success"
    }

    var TaskStatus = {
        Open: "Open",
        Completed: "Completed"
    }

    $scope.test = 'Hello';

    $scope.startIndex = 0;
    $scope.showNumber = 15;
    $scope.fileMessage = '';
    $scope.tasks = [];
    $scope.settings = [];
    var callbackId = "corus";

    $scope.user = null;

    $scope.task = {
        city: 'Saint Petersburg',
        longitude: 0.0,
        latitude: 0.0,
        callback: callbackId
    }

    $scope.settings = {
        weatherUrlTemplate: "",
        apikey: "",
        callback: "",
        inputCapacity: 0
    }

    $scope.weathers = [];
    $scope.result = [];


    $scope.isUploadDisabled = function () {
        return false;
    }


    $scope.isGetWeatherByCityDisabled = function () {

        if (!$scope.task.city || $scope.task.city.length < 2) {
            return true;
        }

        return false;
    }


    $scope.isGetWeatherByCoordsDisabled = function () {

        if ($scope.task.latitude == null || $scope.task.longitude == null){
            return true;
        }
        if ($scope.task.latitude < -90 || $scope.task.latitude > 90) {
            return true;
        }
        if ($scope.task.longitude < -180 || $scope.task.longitude > 180) {
            return true;
        }
        return false;
    }

    $scope.getWeatherByCoords = function () {

        console.log("getting weather for " + $scope.task);

        clearMessages();

        setWaitState(true);
        weatherService.getWeatherByCoords($scope.task).then(function (result) {

            console.log("getting weather success");

            $scope.result = result;

            $scope.weathers = result.data;

            stopLoading();

            if (result.status != Status.Success) {
                $scope.errorMessage = "Error getting weather";
                if (result.message) {
                    $scope.errorMessage += ": " + result.message;
                }
            } else {

                var query =
                    "(" + $scope.task.latitude + " , " + $scope.task.longitude + ")";

                $scope.goToResults(query);

            }


        }, function (err) {

            stopLoading();
            console.log("error getting weather");
            $scope.errorMessage = "Error getting weather";
            if (err.message) {
                $scope.errorMessage += ": " + err.message;
            }
        })


    }


    $scope.getWeatherByCity = function () {

        console.log("getting weather for " + $scope.task);

        clearMessages();

        setWaitState(true);
        weatherService.getWeatherByCity($scope.task).then(function (result) {

            console.log("getting weather success");

            $scope.result = result;

            $scope.weathers = result.data;

            stopLoading();

            if (result.status != Status.Success) {
                $scope.errorMessage = "Error getting weather";
                if (result.message) {
                    $scope.errorMessage += ": " + result.message;
                }
            } else {

                $scope.goToResults($scope.task.city);

            }


        }, function (err) {

            stopLoading();
            console.log("error getting weather");
            $scope.errorMessage = "Error getting weater";
            if (err.message) {
                $scope.errorMessage += ": " + err.message;
            }
        })
    }


    var clearMessages = function () {
        $scope.errorMessage = null;
        $scope.statusMessage = null;
    };


    var setWaitState = function (state) {

        if (state)
            $("#loading").css("display", "block");
        else
            $("#loading").css("display", "none");
    }


    var formatDate = function (date) {

        var mm = date.getMonth() + 1;
        var dd = date.getDate();
        var yyyy = date.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }
        var dt = yyyy + '-' + mm + '-' + dd;

        return dt;
    }


    function doConfirm(msg, yesFn, noFn) {
        var confirmBox = $("#confirmBox");
        confirmBox.find(".message").html(msg);
        confirmBox.find(".yes,.no").unbind().click(function () {
            confirmBox.hide();
        });
        confirmBox.find(".yes").click(yesFn);
        confirmBox.find(".no").click(noFn);
        confirmBox.show();
    }

    $scope.isCompleteAllowed = function (task) {

        return task != null && task.status != "Completed";

    }


    var setLoading = function () {

        $("#loading").css("display", "block");
    }

    var stopLoading = function () {
        $("#loading").css("display", "none");
    }


    $scope.goToResults = function (query) {

        $state.go("results", {query: query, weathers: JSON.stringify($scope.weathers)});

    };

    $scope.getSettings = function () {

        console.log("getting settings");

        weatherService.getSettings().then(function (result) {

            $scope.settings = result;

        });
    };

    $scope.getAuth = function () {

        console.log("getting settings");

        weatherService.getAuth().then(function (result) {

            $scope.user = result;

        });
    };
    $scope.getSettings();

    $scope.getAuth();

});
