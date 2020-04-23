/**
 * 
 */
var module = angular.module('admin');

module.factory('advertiserService', function($http, $q) {
  var factory = {
    getAdvertisers: function(includeDeleted = false) { 
    	var url = '/admin/advertisers' + (includeDeleted ? '/true' : '');
    	var promise = $http.get(url)
    		.then(function (response) {
    			return response.data.map(ingestAdvertiser);
    		});
    	
    	return promise;
    },
    persistAdvertiser: function(advertiser) {
      var promise = null;
      if (advertiser.id == 0) {
    	  promise = $http.post('/admin/advertisers/create', advertiser).then(function(response) {
    		  return ingestAdvertiser(response.data);
    	  });
      } else {
    	  promise = $http.put('/admin/advertisers/' + advertiser.id, advertiser).then(function(response) {
    		  return ingestAdvertiser(response.data);
    	  });
      }
      
      return promise;
    },
    newAdvertiser: function() {
    	return {
    		'id': 0,
    		'name': 'New Advertiser',
    		'capMonthly': 0,
    		'capTotal': 0,
    		'weight': 100,
    		'leadType': '-1',
    		'order_id': 0,
    		'status': 1
    		};
    },
    deleteAdvertiser: function(advertiser) {
    	$http.delete('/admin/advertisers/' + advertiser.id);
    },
    postDispositions: function(advertiserId, rows, columns) {
    	var submitRows = [];
    	rows.forEach(function(row) {
    		var newRow = {};
    		row.data.forEach(function(value, index) {
    			newRow[columns[index]] = value;
    		});
    		newRow['guess'] = row.guess;
    		submitRows.push(newRow);
    	});
    	return $http.put('/admin/advertisers/' + advertiserId + '/upload-dispositions', submitRows);
    },
    getOptions: function(advertiserId) {
    	return [
			'',
			'appointment-1-set',
			'appointment-2-set',
			'bad-info',
			'contacted',
			'contract-signed',
			'duplicate',
			'installed',
			'invalid',
			'lost',
			'none'
    	]
    },
    	/*
    	return [
    		{'value': '', 'text': 'Unknown'},
			{'value': 'appointment-1-set', 'text': 'Appointment 1 Set'},
			{'value': 'appointment-2-set', 'text': 'Appointment 2 Set'},
    		{'value': 'bad-info', 'text': "Bad Info"},
			{'value': 'contacted', 'text': 'Contacted'},
			{'value': 'contract-signed', 'text': 'Contract Signed'},
			{'value': 'duplicate', 'text': 'Duplicate'},
			{'value': 'installed', 'text': 'Installed'},
			{'value': 'invalid', 'text': 'Invalid'},
			{'value': 'lost', 'text': 'Lost'},
			{'value': 'none', 'text': 'None'}
			];
			*/
    assessDispositions(advertiser, file) {
    	var fd = new FormData();
    	fd.append('file', file);
    	return $http.put('/admin/advertisers/' + advertiser.id + '/assess-dispositions', fd,
    			{
                'transformRequest': angular.identity,
        		'headers': {'Content-Type': undefined }
    	});

    		  /*$q(function(resolve, response) {
    	resolve(
    			{
    				'columns': ['column1', 'column2', 'column3'],
    				'rows': [
    					{
    						'data': ['value1', 'value2', 'value3'],
    						'status': 'success',
    						'guess': 'duplicate'
    					},
    					{
    						'data': ['value4', 'value5', 'value6'],
    						'status': 'conflict',
    						'error': 'No consensus between "something1" and "something2".'
    					},
    					{
    						'data': ['value7', 'value8', 'value9'],
    						'status': 'no-guess',
    						'error': 'No guess'
    					}
    				]
    			});
      });*/
    }
  };
    
  return factory;
});

function ingestAdvertiser(advertiser) {
	var newAdvertiser = $.extend({}, advertiser);
	newAdvertiser.vpl = parseFloat(newAdvertiser.vpl);
	newAdvertiser.cap_daily = parseInt(newAdvertiser.cap_daily);
	newAdvertiser.cap_monthly = parseInt(newAdvertiser.cap_monthly);
	newAdvertiser.cap_total = parseInt(newAdvertiser.cap_total);
	newAdvertiser.status = parseInt(newAdvertiser.status);
	newAdvertiser.weight = parseInt(newAdvertiser.weight);
	newAdvertiser.is_exclusive = newAdvertiser.is_exclusive === '0' ? false : true;
		
	return newAdvertiser;
}
