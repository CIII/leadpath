angular.module('admin').controller('publisher-list-modal', function($uibModalInstance) {
	var $ctrl = this;
	
	$ctrl.ok = function () {
		$uibModalInstance.close('ok');
	};
	
	$ctrl.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	}
});

angular.module('admin').controller('publisher-lists', function($scope, $uibModal, publisherListService, publisherService, orderService) {
	var $ctrl = this;
	
	publisherListService.getPublisherLists().then(function (data) {
		$scope.publisherListList = data;
	});
	$scope.editingIndex = -1;
	orderService.getOrders().then(function(data) {
		$scope.orderList = data;
	});
	publisherService.getPublishers().then(function (data) {
		$scope.publisherList = data;
	});
	$scope.valueClick = function(event, index) {
		$scope.editingIndex = index;
	};
	$scope.cancel = function() {
		$scope.editingIndex = -1;
	}
	$scope.savePublisherList = function() {
		var modalInstance = $uibModal.open({
			animation: false,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/publisher-list/save-template',
			controller: 'publisher-list-modal',
			controllerAs: '$ctrl',
			size: 'sm',
			appendTo: angular.element('#publisher-lists')
		});
		
		modalInstance.result.then(function (selectedItem) {
			if (selectedItem === 'ok') {
				var publisherList = $scope.publisherListList[$scope.editingIndex];
				publisherListService.persistPublisherList(publisherList).then(
						function (newPublisherList) {
							$scope.publisherListList[$scope.editingIndex] = newPublisherList;
							$scope.editingIndex = -1;
						},
						function (errors) {
							$scope.postErrors = errors;
						});
			} else {
				$scope.editingIndex = -1;
			}
		}, function() {});
	};
	$scope.newPublisherList = function() {
		var newPublisherList = publisherListService.newPublisherList();
		$scope.publisherListList.push(newPublisherList);
		$scope.editingIndex = $scope.publisherListList.length - 1;
	}
});