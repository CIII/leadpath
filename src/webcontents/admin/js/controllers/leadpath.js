var module = angular.module('admin', ['ngRoute', 'ui.bootstrap', 'angular.filter']);

module.controller('TabController', function($scope, $route) {
	
});

module.config(function($routeProvider, $locationProvider) {
	$routeProvider.when('/admin/advertiser-page', {
		templateUrl : '/admin/advertiser-page.html',
		controller : 'advertisers',
	}).when('/admin/lead-page', {
		templateUrl : '/admin/lead-page.html',
		controller : 'leads'
	}).when('/admin/publisher-page', {
		templateUrl : '/admin/publisher-page.html',
		controller : 'publishers'
	}).when('/admin/publisher-list-page', {
		templateUrl : '/admin/publisher-list-page.html',
		controller : 'publisher-lists'
	});

	// configure html5 to get links working on jsfiddle
	$locationProvider.html5Mode(true);
});
