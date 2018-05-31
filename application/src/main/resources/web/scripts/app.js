var productsApp = angular.module('productsApp', ['smart-table']);

/**
 * Main page controller
 */
productsApp.controller('ProductsTableController', ['Resource', function (service) {
    var ctrl = this;
    this.displayed = [];
    this.fetchData = function fetchData(tableState) {
        ctrl.isLoading = true;
        var pagination = tableState.pagination;
        var start = pagination.start || 0;
        var number = pagination.number || 10;

        service.getPage(start, number, tableState).then(function (result) {
            ctrl.displayed = result.data;
            tableState.pagination.numberOfPages = result.numberOfPages;
            ctrl.isLoading = false;
        });
    };
}]);

/**
 * Service
 */
productsApp.factory('Resource', ['$q', '$filter', '$http', function ($q, $filter, $http) {

    /**
     * Getting the page
     *
     * @param start start offset
     * @param number elements per page
     * @param params parameters (filters/sortings)
     * @returns data
     */
    function getPage(start, number, params) {
        var deferred = $q.defer();

        $http.get('/products')
            .then(function (response) {
                deferred.resolve({
                    data: response.data,
                    numberOfPages: Math.ceil(response.data.length / number)
                });
            }, function (response) {
                deferred.resolve({
                    data: [],
                    numberOfPages: 0
                });
            });
        return deferred.promise;
    }
    return {
        getPage: getPage
    };
}]);