angular.module('admin').controller('publisher-modal', function($uibModalInstance) {
	var $ctrl = this;
	
	$ctrl.ok = function() {
		$uibModalInstance.close('ok');
	};
	
	$ctrl.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
});

angular.module('admin').controller('publishers', function($scope, $uibModal, publisherService, orderService) {
	var $ctrl = this;

	publisherService.getPublishers().then(function (data) {
	  $scope.publisherList = data;
	});
	$scope.editingIndex = -1;	
	$scope.valueClick = function(event, index) {
		$scope.editingIndex = index;
	}
	$scope.cancel = function() {
		$scope.editingIndex = -1;
	}
	$scope.savePublisher = function() {
		// TODO: Validate form
		var modalInstance = $uibModal.open({
			animation: false,
			ariaLabeledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/publisher/save-template',
			controller: 'publisher-modal',
			controllerAs: '$ctrl',
			size: 'sm',
			appendTo: angular.element("#publishers")
		});
		
		modalInstance.result.then(function (selectedItem) {
			if (selectedItem === 'ok') {
				var publisher = $scope.publisherList[$scope.editingIndex];
				publisherService.persistPublisher(publisher).then(function(newPublisher) {
					$scope.publisherList[$scope.editingIndex] = newPublisher;
					$scope.editingIndex = -1;
				}, function(data) {
					$scope.postErrors = data;
				});
			} else {
				$scope.editingIndex = -1;
			}
		}, function() {
			//$scope.editingIndex = -1;
		});
	};
	$scope.newPublisher = function() {
		var newPublisher = publisherService.newPublisher();
		$scope.publisherList.push(newPublisher);
		$scope.editingIndex = $scope.publisherList.length - 1;
	}
});