var module = angular.module('admin');

module.factory('publisherListService', function($http, $q) {
	var factory = {
		getPublisherLists: function(includeDeleted = false) {
			var url = '/admin/publisher-lists';
			var promise = $http.get(url)
					.then(function(response) {
				return ingestPublisherLists(response.data);
			});
			return promise;
		},
		persistPublisherList: function(publisherList) {
			var promise = null;
			if(publisherList.id == 0) {
				promise = $http.post('/admin/publisher-lists/create', publisherList).then(
						function(response) {
							return ingestPublisherList(response.data);
						},
						function(response) {
							var errors = response.data.errors;
							return $q.reject(errors);
						});
			} else {
				promise = $http.put('/admin/publisher-lists/' + publisherList.id, publisherList).then(
						function(response) {
							return ingestPublisherList(response.data);
						},
						function(response) {
							var errors = response.data.errors;
							return $q.reject(errors);
						});
			}
			
			return promise;
		},
		newPublisherList: function() {
			return {
				'id': 0,
				'name': 'New Publisher List',
				'is_direct': true,
				'max_lead_units': 0,
				'status': 1,
				'ext_list_id': '',
				'is_direct': false,
			};
		}
	};
	
	return factory;
});

function ingestPublisherLists(publisherLists) {
	var lists = [];
	publisherLists.forEach(function(publisherListItem) {
		var item = ingestPublisherList(publisherListItem);
		lists.push(item);
	});
	return lists;
}

function ingestPublisherList(publisherListItem) {
	publisherListItem.data.orders = [];
	publisherListItem.orders.forEach(function(item) {
		publisherListItem.data.orders.push(item.order_id);
	});
	publisherListItem.data.publishers = [];
	publisherListItem.publishers.forEach(function(item) {
		publisherListItem.data.publishers.push(item.publisher_id);
	});
	return publisherListItem.data;
}