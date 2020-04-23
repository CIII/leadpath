var module = angular.module('admin');

module.factory('orderService', function($http) {
  var factory = {
    getOrders: function() {
      var url = '/admin/orders';
      var promise = $http.get(url).then(function (response) {
          return response.data;
        });
      return promise;
    }
  }
  
  return factory;
});