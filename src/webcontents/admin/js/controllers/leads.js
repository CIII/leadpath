// Parts of this file adapted from:
// https://bl.ocks.org/d3noob/43a860bc0024792f8803bba8ca0d5ecd
// https://codepen.io/netkuy/pen/qZGdoj

angular.module('admin').controller('leads', function($scope, $compile, leadService, $uibModal) {
	var treeData;
	var tree;
	var svg;
	var root;
	var i = 0;
	const WIDTH = 600;
	const HEIGHT = 600;
	const INDENT = 300;
	
	leadService.getLeads().then(function (data) {
		$scope.leadList = data;
		$scope.initializeLeadTree();
	});
	leadService.getInvalidLeads().then(function (data) {
		$scope.invalidLeadList = data;
	});
	leadService.getPartialLeads().then(function (data) {
		$scope.partialLeadList = data;
	});
	$scope.nodeClick = function(node) {
		console.log('Clicked');
	}
	$scope.resubmitLeadMatch = function($event) {
		leadService.resubmitLeadMatch($scope.selectedLead.leadId, $scope.selectedLead.id);
	}
	$scope.repostLead = function($event) {
		var modalInstance = $uibModal.open({
			animation: false,
		    ariaLabelledBy: 'modal-title',
		    ariaDescribedBy: 'modal-body',
		    templateUrl: '/lead/repost-lead-template',
		    controller: 'lead-modal',
		    controllerAs: '$ctrl',
		    size: 'sm',
		    appendTo: angular.element('#leads')
		})
		
		modalInstance.result.then(function (selectedItem) {
			if (selectedItem === 'repost') {
				leadService.resubmitLead($scope.selectedLead.id);
			}
		},
		function() {
			
		});
	}
		
	$scope.initializeLeadTree = function() {
		selection = d3.select("#lead-tree");
		if(selection.select("svg").empty()) {
			svg = selection.append("svg")
				.attr("width", "100%")
				.attr("height", "100%")
				.append("g")
				.attr('transform', 'translate(40,0)');

			treeData = getHierarchyData();
			root = d3.hierarchy(treeData, function(d) { return d.children; });
			root.x0 = WIDTH / 2;
			root.y0 = 0;
			tree = d3.tree().size([HEIGHT, WIDTH]);
			root.children.forEach(collapse);
			
			update(root);
		}
	}
	
	function update(source) {
		var treeData = tree(root);		
		
		var nodes = treeData.descendants();
		nodes.forEach(function(node) { node.y = node.depth * 300 });

		var link = svg.selectAll(".link")
			.data(treeData.descendants().slice(1), function(d) { return d.id; });
		var linkEnter = link.enter()
			.insert("path", "g")
			.attr("class", function(d) {
				return "link" + (d.parent.parent ? "" : " root");
			})
			.attr("d", function(d) {
				var o = {x: source.x0, y: (source.y0 - INDENT) }
				var q = {x: source.x0, y: source.y0 }
		        return diagonal(q, o);
			});
		
		var linkUpdate = linkEnter.merge(link);
		linkUpdate.transition()
			.duration(750)
			.attr('d', function(d){ 
				var parentCoords = { x: d.parent.x, y: (d.parent.y + 175 - INDENT) };
				return diagonal(d, parentCoords);
				});
		var linkExit = link.exit().transition()
			.duration(750)
			.attr('d', function(d) {
				var o = {x: source.x, y: (source.y - INDENT)};
				var q = { x: source.x, y: source.y }
				return diagonal(q, o);
			})
			.remove();
				
		var node = svg.selectAll(".node")
			.data(nodes, function(d) { return d.id || (d.id = ++i); });
		var nodeEnter = node.enter()
			.insert("g", "g")
			.attr("class", function(d) {
				return "node" + (d.children ? " node--internal" : " node--leaf")
					+ (d.parent ? "" : " root")
					+ " " + d.data.type;
			})
			.attr("transform", function(d) {
				return "translate(" + (source.y0 - INDENT) + "," + source.x0 + ")";
			})
			.on('click', click);

		nodeEnter.append("rect")
			.attr("width", 200)
			.attr("height", 40)
			.attr("x", "-25")
			.attr("y", "-20");
		
		nodeEnter.append("text")
			.attr("dy", 3)
			.attr("x", function(d) {
				return d.children ? -8 : 8;
			})
			.style("text-anchor", function(d) {
				return d.children ? "end" : "start";
			})
			.text(function(d) {
				return d.data.email || d.data.advertiser;
			});
		
		var nodeUpdate = nodeEnter.merge(node);
		nodeUpdate.transition().duration(750)
			.attr("transform", function(d) {
				return "translate(" + (d.y - INDENT) + "," + d.x + ")";
			});
		
		var nodeExit = node.exit().transition()
			.duration(750)
			.attr("transform", function(d) {
				return "translate(" + (source.y - INDENT) + "," + source.x + ")";
			})
			.remove();
		
		treeData.descendants().forEach(function(d) {
			d.x0 = d.x;
			d.y0 = d.y;
		});
	}
	
	//Collapse the node and all it's children
	function collapse(d) {
	  if(d.children) {
	    d._children = d.children
	    d._children.forEach(collapse)
	    d.children = null
	  }
	}

	// Toggle children on click.
	function click(d) {
	  if (d.children) { // Collapse
	      d._children = d.children;
	      d.children = null;
	      $scope.$apply(function() {
	    	  $scope.selectedLead = d.data;
	      });
	  } else if (d._children) { // Expand, select lead
	      d.children = d._children;
	      d._children = null;
	      $scope.$apply(function() {
	    	  $scope.selectedLead = d.data;
	      });
	  } else  { // Select leadmatch
		  $scope.$apply(function() {
			  $scope.selectedLead = d.data;
		  })
	  }
	  update(d);
	}
	
	// Creates a curved (diagonal) path from parent to the child nodes
	function diagonal(s, d) {
		path = `M ${s.y - INDENT} ${s.x}
			C ${((s.y - INDENT) + d.y) / 2} ${s.x},
			${((s.y - INDENT) + d.y) / 2} ${d.x},
			${d.y} ${d.x}`

	    return path
	}

	
	function getHierarchyData() {
		var returnValue = {
			'type': 'root',
			'children': []
		}
		$scope.leadList.forEach(function(lead) {
			lead.children = [];
			lead["lead-matches"].forEach(function(leadMatch) {
				leadMatch.leadId = lead.id;
				lead.children.push(leadMatch);
			});
			returnValue.children.push(lead);
		});
		return returnValue;
	}
});

angular.module('admin').controller('lead-modal', function($uibModalInstance) {
	  var $ctrl = this;
	  
	  $ctrl.repost = function () {
	    $uibModalInstance.close('repost');
	  };

	  $ctrl.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	  };
	});