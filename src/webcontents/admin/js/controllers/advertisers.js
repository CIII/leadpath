angular.module('admin').controller('advertiser-modal', function($uibModalInstance) {
  var $ctrl = this;
  
  $ctrl.ok = function () {
    $uibModalInstance.close('ok');
  };

  $ctrl.cancel = function () {
	$uibModalInstance.dismiss('cancel');
  };
});

angular.module('admin').controller('advertiser-overlay', function($scope, $uibModalInstance, advertiserService) {
	var $ctrl = this;
	$scope.classes = {
			'NO_GUESS': 'alert alert-danger',
			'SUCCESS': 'alert alert-success',
			'CONFLICT': 'alert alert-warning'
	}
    $scope.optionNames = 
	{
		'': 'Unknown',
		'appointment-1-set': 'Appointment 1 Set',
		'appointment-2-set': 'Appointment 2 Set',
		'name': 'bad-info',
		'bad-info': "Bad Info",
		'contacted': 'Contacted',
		'contract-signed': 'Contract Signed',
		'duplicate': 'Duplicate',
		'installed': 'Installed',
		'invalid': 'Invalid',
		'lost': 'Lost',
		'none': 'None'
	};
		
	$ctrl.ok = function() {
		$scope.rows.forEach(function(item) {
			var group1 = item.data[4] || '';
			var group2 = item.data[5] || '';
			var group3 = item.data[6] || '';
			item['guess'] = $scope.guesses[group1][group2][group3].guess || "";
		})
		advertiserService.postDispositions($scope.advertiserList[$scope.editingIndex].id, $scope.rows, $scope.columns).then(
				  function(response) {
					if (response.data.success) {
						delete $scope.$parent.dispositionError;
						$uibModalInstance.dismiss('ok');
					} else {
					  $scope.$parent.dispositionError = response.data.errors;
					  $uibModalInstance.dismiss('cancel');
					}
				  },
				  function(response) {
					$scope.$parent.dispositionError = "Unknown server error: " + response.message;
					$uibModalInstance.dismiss('cancel');
				  });
	}
	
	$ctrl.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	}
	
  $scope.countRows = function(data, subIndexes) {
	var counts = {};
	$scope.constructGroups(data, counts, subIndexes);
	// TODO: Crawl the new structure and add everything up.
	var count = $scope.countRowsInternal(counts, subIndexes.length);
		  
	return count;
  }
	  
  $scope.countRowsInternal = function(data, depth) {
	var count = 0;
	var guess = null;
	if (data['xxCount']) {
	  count = count + data['xxCount'];
	} else {
	  for(var group in data) {
		if (group !== 'data' && group !== 'xxCount' && group !== 'dataItem') {
          count = count + $scope.countRowsInternal(data[group], depth - 1);
		}
	  }
	  var multiplier = Object.keys(data).length - 1 - ('data' in data ? 1 : 0) - ('xxCount' in data ? 1 : 0) - ('dataItem' in data ? 1 : 0);
	  count = count + 1 + (depth * multiplier);
	  data['xxCount'] = count;
	}
	return count;
  };
	  
  $scope.constructGroups = function(data, counts, subIndexes) {
	if(subIndexes == null) {
		return {
			count: 1,
			guess: data['guess']
		};
	} else if(subIndexes.length > 0) {
      var newSubIndexes = subIndexes.slice(0);
	  var groupingIndex = newSubIndexes.shift();
	  for(var i = 0; i < data.length; i++) {
		data[i].dataItem = data[i].data[groupingIndex];
		counts[data[i].data[groupingIndex]] = (counts[data[i].data[groupingIndex]] || []).concat([data[i]]);
	  }
	  for(var group in counts) {
		if (group !== "data") {
		  var newGroup = {};
		  var groupData = counts[group];
		  newGroup['data'] = $.extend(true, [], groupData);
		  $scope.constructGroups(newGroup['data'], newGroup, newSubIndexes);
		  counts[group] = newGroup;
		}
	  }
	} else {
	  var constructedData;
	  var count = 0;
	  var guess = null;
	  for (var i = 0; i < data.length > 0; i++) {
		constructedData = $scope.constructGroups(data[i], null, null);
		count = count + constructedData['count'];
		if (guess == null) {
			guess = constructedData['guess'];
		} else if (guess !== constructedData['guess']) {
			guess = '';
		}
	  }
      count = count + 1;
	  counts['xxCount'] = count;
	  counts['guess'] = guess;
	  counts['data'] = data;
	  
	  return {'count': count, 'guess': guess};
	}
  };
  
  $scope.rowCollapse = function($event) {
	  var classList = $event.target.parentNode.classList;
	  var className;
	  classList.forEach(function(item) {
		  if (item.startsWith('row-')) {
			  className = item;
		  }
	  });
	  var rowsToCollapse = angular.element($event.target).parent().parent().find('.' + className);
	  var firstRow = rowsToCollapse.first();
	  var attr = angular.element($event.target).parent().children().first().attr('tq-rowspan');
	  if (typeof attr !== 'undefined' || attr === false) {
		  var rowspan = parseInt(angular.element($event.target).parent().children().first().attr('tq-rowspan'));
		  angular.element($event.target).parent().children().first().attr('rowspan', rowspan)
		  angular.element($event.target).parent().children().last().attr('rowspan', rowspan);
		  angular.element($event.target).parent().children().first().removeAttr('tq-rowspan');
		  rowsToCollapse.slice(1).removeClass('hide');
		  // TODO: Set rowspan of group2
		  var groupRow = angular.element($event.target).parent().prevAll('.group2:first').last();
		  var groupRowspan = parseInt(groupRow.find(':first-child').attr('rowspan'));
		  var newGroupRowspan = groupRowspan + (rowspan - 1);
		  groupRow.find(':first-child').attr('rowspan', newGroupRowspan);
		  // TODO: Set rowspan of group1
		  groupRow = groupRow.prevAll('.group1:first').last();
		  groupRowspan = parseInt(groupRow.find(':first-child').attr('rowspan'));
		  newGroupRowspan = groupRowspan + (rowspan - 1);
		  groupRow.find(':first-child').attr('rowspan', newGroupRowspan);
	  } else {
		  var rowspan = parseInt(angular.element($event.target).parent().children().first().attr('rowspan'));
		  var groupRow = angular.element($event.target).parent().prevAll('.group2:first').last();
		  var groupRowspan = parseInt(groupRow.find(':first-child').attr('rowspan'));
		  var newGroupRowspan = groupRowspan - (rowspan - 1);
		  groupRow.find(':first-child').attr('rowspan', newGroupRowspan);
		  groupRow = groupRow.prevAll('.group1:first').last();
		  groupRowspan = parseInt(groupRow.find(':first-child').attr('rowspan'));
		  newGroupRowspan = groupRowspan - (rowspan - 1);
		  groupRow.find(':first-child').attr('rowspan', newGroupRowspan);
		  angular.element($event.target).parent().children().first().attr('tq-rowspan', rowspan);
		  angular.element($event.target).parent().children().first().attr('rowspan', 1);
		  angular.element($event.target).parent().children().last().attr('rowspan', 1);
		  rowsToCollapse.slice(1).addClass('hide');
	  }
  }

  $scope.keys = Object.keys;
  
  $scope.guesses = {};
  // TODO: This array needs to be retrieved from the server.
  $scope.constructGroups($scope.rows, $scope.guesses, $scope.indexedColumns);
  $scope.countRowsInternal($scope.guesses, 3);
});

angular.module('admin').controller('advertisers', function($scope, $uibModal, advertiserService) {
  var $ctrl = this;
  
  advertiserService.getAdvertisers().then(function (data) {
	$scope.advertiserList = data;
  })
  $scope.includePaused = false;
  $scope.editingIndex = -1;
  $scope.valueClick = function (event, index) {
    $scope.editingIndex = index;
  }
  $scope.cancel = function() {
	$scope.editingIndex = -1;
  }
  $scope.saveAdvertiser = function() {
    var modalInstance = $uibModal.open({
	    animation: false,
		ariaLabelledBy: 'modal-title',
		ariaDescribedBy: 'modal-body',
		templateUrl: '/advertiser/save-template',
		controller: 'advertiser-modal',
		controllerAs: '$ctrl',
		size: 'sm',
		appendTo: angular.element('#advertisers')
	  });
	    
	modalInstance.result.then(function (selectedItem) {
		if (selectedItem === 'ok') {
		    var advertiser = $scope.advertiserList[$scope.editingIndex];
			advertiserService.persistAdvertiser(advertiser).then(function(newAdvertiser) {
			  $scope.advertiserList[$scope.editingIndex] = newAdvertiser;
		      $scope.editingIndex = -1;
			});
		} else {
			$scope.editingIndex = -1;
		}
	  }, function() {
		  //$scope.editingIndex = -1;
	  });

  }
  $scope.pauseAdvertiser = function(event, index) {
	  advertiserService.deleteAdvertiser($scope.advertiserList[index]);
	  if($scope.advertiserList[index].status && $scope.includePaused) {
		$scope.advertiserList[index].status = false;
	  } else if($scope.advertiserList[index].status && !$scope.includePaused) {
		$scope.advertiserList.splice(index, 1);  
	  } else {
		$scope.advertiserList[index].status = true;
	  }
  }
  $scope.newAdvertiser = function() {
	  var newAdvertiser = advertiserService.newAdvertiser();
	  $scope.advertiserList.push(newAdvertiser);
	  $scope.editingIndex = $scope.advertiserList.length - 1;
  }
  $scope.includePausedClicked = function() {
	  advertiserService.getAdvertisers($scope.includePaused).then(function (data) {
		  $scope.advertiserList = data;
	  })
	  $scope.advertiserList 
  }
  
  $scope.postDispositions = function() {
	// TODO: Post the dispositions here, and set up the rows.
	advertiserService.assessDispositions($scope.advertiserList[$scope.editingIndex], $('#dispositions').prop('files')[0]).then(
	    function(response) {
	      $scope.columns = response.data.columns;
	      $scope.indexedColumns = [];
	      $scope.columns.forEach( function(item, i) {
	    	  if (item.isIndexed) {
	    		  $scope.indexedColumns.push(i);
	    	  }
	      });
	      $scope.options = advertiserService.getOptions();
	      $scope.rows = response.data.rows;
		  var modalInstance = $uibModal.open({
		      animation: false,
			  ariaLabelledBy: 'modal-title',
			  ariaDescribedBy: 'modal-body',
			  templateUrl: '/advertiser/overlay-template',
			  controller: 'advertiser-overlay',
			  controllerAs: '$ctrl',
			  size: 'lg',
			  appendTo: angular.element('#advertisers'),
			  scope: $scope
		  });
		  modalInstance.result.then(function (selectedItem) {
		    if (selectedItem === 'ok') {
			  $scope.editingIndex = -1;
			} else {
			  $scope.editingIndex = -1; 
			}
		  }, function() {});
	    },
	    function(error) {});	  
  };
  
  $scope.cleanString = function(string) {
		var newString = string.toLowerCase().replace(/[\s#?\/():]/g, '-');
		return newString;
  }
});
