var module = angular.module('admin');

module.factory('publisherService', function($http, $q) {
  var factory = {
    getPublishers: function(includePaused = false) {
      var url = '/admin/publishers' + (includePaused ? '/true' : '');
      var promise = $http.get(url)
        .then(function (response) {
          return response.data.map(ingestPublisher);
        });
      return promise;
    },
    persistPublisher: function(publisher) {
    	var promise = null;
    	if (publisher.id == 0) {
    		promise = $http.post('/admin/publishers/create', publisher).then(
    				function(response) {
    					return ingestPublisher(response.data);
    				},
    				function(response) {
    					var errors = response.data.errors;
    					return $q.reject(errors);
    				});
    	} else {
    		promise = $http.put('/admin/publishers/' + publisher.id, publisher).then(
    				function(response) {
    					return ingestPublisher(response.data);
    				},
    				function(response) {
    					var errors = response.data.errors;
    					return $q.reject(errors);
    				});
    	}
    	
    	return promise;
    },
    newPublisher: function() {
    	return {
    		'id': 0,
    		'name': 'New Publisher',
    		'allow_duplicates': true,
    		'extended_validation': false,
    		'domain': '',
    		'user_name': ''
    	};
    }
  };
  
  return factory;
});

function ingestPublisher(publisher) {
	var newPublisher = $.extend({}, publisher);
	newPublisher.id = parseInt(newPublisher.id);
	newPublisher.allow_duplicates = newPublisher.allow_dulicates === '0' ? false : true;
	newPublisher.extended_validation = newPublisher.extended_validation === '0' ? false : true;
  return newPublisher;
}