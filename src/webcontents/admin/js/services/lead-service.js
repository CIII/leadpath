angular.module('admin').factory('leadService', function($http, $q) {
	var factory = {
			getPartialLeads: function() {
				return $q(function(resolve, reject) {
					resolve([
						{id: 5, date: '2017-06-05'},
						{id: 6, date: '2017-06-06'}
					]);
				});
			},
			getInvalidLeads: function() {
				return $q(function(resolve, reject) {
					resolve([
						{id: 1, date: '2017-06-01'},
						{id: 2, date: '2017-06-02'}
					]);
				});
			},
			getLeads: function() {
				var url = '/admin/leads/leads';
				var promise = $http.get(url)
					.then(function(response) {
						return ingestLeads(response.data);
					});
				return promise;
			},
			resubmitLeadMatch: function(leadId, leadMatchId) {
				var url= '/repost?listid=solar_full_form&domtok=sunnyS0lar&ref=www.easiersolar.com&lead_ref=' + leadId + '&lead_match_id=' + leadMatchId;
				var req = {
						method: 'POST',
						url: url,
						headers: {
							'referer': 'www.easiersolar.com'
						}
				}
				var promise = $http(req)
					.then(function(response) {
					return response.data;
				});
				return promise;
			},
			resubmitLead: function(leadId) {
				var url = '/post?listid=solar_full_form&domtok=sunnyS0lar&ref=www.easiersolar.com&lead_ref=' + leadId;
				var req = {
						method: 'POST',
						url: url,
						headers: {
							'referer': 'www.easisersolar.com'
						}
				}
				var promise = $http(req)
					.then(function(response) {
						return response.data;
					});
				return promise;
			}
	};
	
	return factory;
});

function ingestLeads(leads) {
	var newLeads = [];
	leads.forEach(function(lead) {
		var newAttributes = [];
		lead.attributes.forEach(function(attribute, index) {
			var attributeName = "";
			newAttribute = {};
			if (attribute.name.startsWith("phone_checks")) {
				attributeName += "phone: ";
				attributeName += attribute.name.substring(attribute.name.lastIndexOf(".") + 1);
			} else if (attribute.name.startsWith("address_checks")) {
				attributeName += "addr: ";
				attributeName += attribute.name.substring(attribute.name.lastIndexOf(".") + 1);
			} else if (attribute.name.startsWith("email_address_checks")) {
				attributeName += "email: ";
				attributeName += attribute.name.substring(attribute.name.lastIndexOf(".") + 1);
			} else if (attribute.name.startsWith("ip_address_checks")) {
				attributeName += "ip: ";
				attributeName += attribute.name.substring(attribute.name.lastIndexOf(".") + 1);
			} else {
				attributeName = attribute.name;
			}
			newAttribute['name'] = attributeName;
			newAttribute['value'] = attribute.value.replace("Some(List())", "");
			newAttributes.push(newAttribute);
		});
		lead.attributes = newAttributes;
		newLeads.push(lead);
	});
	
	return newLeads;
}