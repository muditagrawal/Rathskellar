var RathApp = angular.module('RathApp', ['ngRoute' ,'ngMaterial', 'mdDataTable','ngMdIcons']);
RathApp.config(['$routeProvider', '$locationProvider',
  function($routeProvider, $locationProvider) {
    $routeProvider
    	.when('/menu', {
        templateUrl: 'menu.html',
        controller: 'MenuCtrl',
        controllerAs: 'Menu'
      })
      .when('/admin', {
        templateUrl: 'admin.html',
        controller: 'AdminCtrl',
        controllerAs: 'Admin'
      })
      .when('/staff', {
        templateUrl: 'staff.html',
        controller: 'StaffCtrl',
        controllerAs: 'Staff'
      })
      .when('/customer', {
        templateUrl: 'customer.html',
        controller: 'CustomerCtrl',
        controllerAs: 'Customer'
      })
      .when('/checkemail', {
        templateUrl: 'checkemail.html',
        controller: 'CheckEmailCtrl',
        controllerAs: 'CheckEmail'
      })
      	.when('/register', {
        templateUrl: 'register.html',
        controller: 'RegisterCtrl',
        controllerAs: 'Register'
      })
      	.when('/login', {
      		templateUrl: 'userlogin.html',
            controller: 'LoginCtrl',
            controllerAs: 'Login'
      });

    $locationProvider.html5Mode(true);
}])
.controller('MainController', ['$scope', '$rootScope','$http','$location','$route','$routeParams',
  function($scope,$rootScope,$http,$location,$route , $routeParams) {
    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
    $rootScope.panel = function(){
    	if($rootScope.user.role == 'CUSTOMER'){
			  $location.path("/customer");

    	}else if($rootScope.user.role =='STAFF'){
			  $location.path("/staff");

    	}else if($rootScope.user.role == 'ADMIN'){
			  $location.path("/admin");

    	}
    }
    $rootScope.logout = function(){
    	$http({
            method: 'POST',
            url: 'http://localhost:8080/rath/webapi/logout/',
            headers: {
          	  'Content-Type': 'application/json'
          	 },
  	  		data : $rootScope.access_token
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $rootScope.message = response.data;
            if(response.status==200){
            	$rootScope.user = null;
            	$rootScope.access_token = null;
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
      	  $scope.message = response.data;
      	  });
    }
}])
.controller('AdminCtrl', ['$scope','$rootScope','$http','$route','$mdSidenav', '$location', '$routeParams',
  function($scope,$rootScope,$http,$route,$mdSidenav, $location, $routeParams) {
    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
    $rootScope.changeview = function(page){
    	  $rootScope.tool = page;
      }
}])
.controller('UserManagementCtrl',['$scope','$rootScope','$http',function($scope,$rootScope,$http, $mdToast){
	angular.element(document).ready(function(){
		$rootScope.allusers=null;
		$http({
            method: 'GET',
            url: 'http://localhost:8080/rath/webapi/user/',
      	  headers: {
          	  'Content-Type': 'application/json' ,
          	  'Authorization': 'Bearer '+$rootScope.access_token
          	 }
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $rootScope.allusers = response.data;
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
	
	});
}])
.controller('OrderManagementCtrl',['$scope','$rootScope','$http',function($scope,$rootScope,$http, $mdToast){
	angular.element(document).ready(function(){
		$http({
            method: 'GET',
            url: 'http://localhost:8080/rath/webapi/order/',
      	  headers: {
          	  'Content-Type': 'application/json' ,
          	  'Authorization': 'Bearer '+$rootScope.access_token
          	 }
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $rootScope.allorders = response.data;
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
	
	});
}])
.controller('MenuManagementCtrl', ['$scope','$rootScope','$http','$routeParams', function($scope,$rootScope,$http,$routeParams,$mdToast) {
  this.name = "MenuManagementCtrl";
  this.params = $routeParams;
  $scope.addmenuitem = function(){
	  $scope.newmenuitem.category = JSON.parse($scope.newmenuitem.category);
	  $http({
          method: 'POST',
          url: 'http://localhost:8080/rath/webapi/menuitem/',
          headers: {
          	  'Content-Type': 'application/json' ,
          	  'Authorization': 'Bearer '+$rootScope.access_token
          	 },
         data: $scope.newmenuitem
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $scope.newmenuitem = response.data;
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
      });
  }
  angular.element(document).ready(function(){
	  $rootScope.menuitems = null;
	  $rootScope.categories = null;
	  $http({
          method: 'GET',
          url: 'http://localhost:8080/rath/webapi/menuitem/'
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.menuitems = response.data;
          alert(JSON.stringify($rootScope.menuitems));
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
      });
	  $http({
          method: 'GET',
          url: 'http://localhost:8080/rath/webapi/category/'
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.categories = response.data;
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
      });
  });
}])
.controller('CategoryManagementCtrl',['$scope','$rootScope','$http','$route','$routeParams','$location'
                            ,function($scope,$rootScope,$http,$route,$routeParams,$location){
	this.$route =$route;
	this.$location = $location;
	this.$routeParams = $routeParams;
	$scope.addcategory = function(){
		  $http({
	          method: 'POST',
	          url: 'http://localhost:8080/rath/webapi/category/',
	          headers: {
	          	  'Content-Type': 'application/json' ,
	          	  'Authorization': 'Bearer '+$rootScope.access_token
	          	 },
	         data: $scope.newcategory
	      }).then(function successCallback(response) {
	          // this callback will be called asynchronously
	          // when the response is available
	          $scope.newcategory = response.data;
	      }, function errorCallback(response) {
	          // called asynchronously if an error occurs
	          // or server returns response with an error status.
	      });
	  }
	angular.element(document).ready(function(){
		$rootScope.categories=null;
		$http({
	        method: 'GET',
	        url: 'http://localhost:8080/rath/webapi/category/'
	    }).then(function successCallback(response) {
	        // this callback will be called asynchronously
	        // when the response is available
	        $rootScope.categories = response.data;
	    }, function errorCallback(response) {
	        // called asynchronously if an error occurs
	        // or server returns response with an error status.
	    });	
	});
}])
.controller('UserProfileCtrl', ['$scope','$rootScope','$http','$routeParams', function($scope,$rootScope,$http,$routeParams,$mdToast) {
  this.name = "UserProfileCtrl";
  this.params = $routeParams;
  angular.element(document).ready(function(){
	  $http({
          method: 'GET',
          url: 'http://localhost:8080/rath/webapi/userdetails/me',
          headers: {
          	  'Content-Type': 'application/json' ,
          	  'Authorization': 'Bearer '+ $rootScope.access_token
          	 }
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.userdetails = response.data;
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
      });
  });
  $scope.updateuserdetails=function(){
	  $http({
          method: 'PUT',
          url: 'http://localhost:8080/rath/webapi/userdetails/me',
          headers: {
        	  'Content-Type': 'application/json',
        	  'Authorization' : 'Bearer '+$rootScope.access_token
        	 },
	  		data : $rootScope.userdetails
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.message = response.data;
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
    	  $rootScope.message = response.data;
      });
  }
}])
.controller('UserSettingsCtrl', ['$scope','$rootScope','$http','$routeParams', function($scope,$rootScope,$http,$routeParams,$mdToast) {
  this.name = "UserSettingsCtrl";
  this.params = $routeParams;
  $scope.password = "";
  $scope.rpassword = "";
  $scope.changepassword = function(){
	  if(($scope.password == $scope.rpassword)&&($scope.password.length>6)){
		  $scope.user.password = $scope.password;
		  $http({
	          method: 'PUT',
	          url: 'http://localhost:8080/rath/webapi/user/me',
	          headers: {
	        	  'Content-Type': 'application/json',
	        	  'Authorization' : 'Bearer '+$rootScope.access_token
	        	 },
		  		data : $scope.user
	      }).then(function successCallback(response) {
	          // this callback will be called asynchronously
	          // when the response is available
	          $rootScope.message = response.data;
	          alert(JSON.stringify($rootScope.message));
	      }, function errorCallback(response) {
	          // called asynchronously if an error occurs
	          // or server returns response with an error status.
	    	  $rootScope.message = response.data;
	      });
	  }
	  else{
		  alert("Passwords do not match the criterion");
	  }
  }
}])
.controller('StaffCtrl', ['$route', '$routeParams', '$location',
  function($scope,$http,$route, $routeParams, $location) {
    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
    $rootScope.changeview = function(page){
  	  $rootScope.tool = page;
    }
}])
.controller('CustomerCtrl', ['$route','$rootScope', '$routeParams', '$location',
  function($scope,$rootScope,$http,$route, $routeParams, $location) {
    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
    $rootScope.changeview = function(page){
  	  $rootScope.tool = page;
    }
}])
.controller('NewOrderCtrl',['$scope','$rootScope','$http','$route','$routeParams','$location'
                            ,function($scope,$rootScope,$http,$route,$routeParams,$location){
	this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
    $rootScope.ordereditems={};
    $scope.selectedIndex = null;
    $scope.quantities = [{value:0},{value:1},{value:2}];
    $scope.orderSummary= function(){
    	$rootScope.changeview('ordersummary.html');
    }
    $scope.checkfirsttab=function(){
    	if($scope.selectedIndex != 0){
    		return true;
    	}else{
    		return false;
    	}
    }
    $scope.checklasttab = function(){
    	if ($scope.selectedIndex==$rootScope.categories.length-1){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    $scope.previousTab = function(){
    	alert($scope.selectedIndex);
    	if ($scope.selectedIndex>0){
    		$scope.selectedIndex = $scope.selectedIndex -1;
    	}
    }
    $scope.nextTab = function(){
    	if ($scope.selectedIndex<$rootScope.categories.length){
    		$scope.selectedIndex = $scope.selectedIndex + 1;
    	}
    }
    $scope.calculatetotal = function(menuitem){
    	$rootScope.ordereditems[menuitem.id]=menuitem;
    	menuitem.total = menuitem.quantity*menuitem.price;
    }
    
    $scope.hello = function(){
    	$scope.currentcategory = $rootScope.categories[$scope.selectedIndex];
    	$rootScope.currentcategorymenu = $scope.currentcategory.menuitems;
    }
    angular.element(document).ready(function(){
		$rootScope.categories=null;
		$http({
	        method: 'GET',
	        url: 'http://localhost:8080/rath/webapi/category/'
	    }).then(function successCallback(response) {
	        // this callback will be called asynchronously
	        // when the response is available
	        $rootScope.categories = response.data;
	        angular.forEach($rootScope.categories,function(category){
	        	category.menuitems=null;
	        	$http({
			        method: 'GET',
			        url: 'http://localhost:8080/rath/webapi/menuitem/'+category.id
			    }).then(function successCallback(response) {
			        // this callback will be called asynchronously
			        // when the response is available
			        category.menuitems = response.data;
			    }, function errorCallback(response) {
			        // called asynchronously if an error occurs
			        // or server returns response with an error status.
			    });
	        });
	    }, function errorCallback(response) {
	        // called asynchronously if an error occurs
	        // or server returns response with an error status.
	    });	
	});
}])
.controller('OrderSummaryCtrl',['$scope','$rootScope',function($scope,$rootScope){
	$rootScope.grandtotal = 0;
	$rootScope.orders=[];
	$scope.orderitem={};
	$rootScope.gotopayment = function(){
		angular.forEach($rootScope.ordereditems,function(value,key){
			$scope.orderitem.menuItem = value;
			delete $scope.orderitem.menuItem.quantity;
			delete $scope.orderitem.menuItem.total;
			$scope.orderitem.quantity = value.quantity;
			$rootScope.orders.push($scope.orderitem);
		});
		$rootScope.changeview('payment.html');
	}
	angular.element(document).ready(function(){
		angular.forEach($rootScope.ordereditems,function(value,key){
			$rootScope.grandtotal=$rootScope.grandtotal+value.total;
		})
    })	
}])
.controller('PaymentCtrl',['$scope','$rootScope','$http',function($scope,$rootScope,$http){
	$scope.processpayment = function(){
		$scope.userorder={};
		$rootScope.transaction={};
		$scope.userorder.items = $rootScope.orders;
		$scope.userorder.user = $rootScope.user;
		$scope.userorder.orderType = 'DELIVERY';
		alert(JSON.stringify($scope.userorder));
		$http({
	          method: 'POST',
	          url: 'http://localhost:8080/rath/webapi/order/',
	          headers: {
	        	  'Content-Type': 'application/json'
	        	 },
		  		data : $scope.userorder
	      }).then(function successCallback(response) {
	          // this callback will be called asynchronously
	          // when the response is available
	          $rootScope.transaction.order = response.data;
	          $rootScope.transaction.amount = $rootScope.grandtotal;
	  		$rootScope.transaction.cardDetails = $scope.carddetails;
	  		$http({
	  	          method: 'POST',
	  	          url: 'http://localhost:8080/rath/webapi/paymentgateway/',
	  	          headers: {
	  	        	  'Content-Type': 'application/json'
	  	        	 },
	  		  		data : $rootScope.transaction
	  	      }).then(function successCallback(response) {
	  	          // this callback will be called asynchronously
	  	          // when the response is available
	  	          $rootScope.transactioncode = response.data;
	  	          if($rootScope.transactioncode==200){
	  	        	$rootScope.changeview('orderplaced.html');
	  	          }
	  	          else{
	  	        	  alert("Your Card Details are wrong");
	  	          }
	  	      }, function errorCallback(response) {
	  	          // called asynchronously if an error occurs
	  	          // or server returns response with an error status.
	  	    	  $rootScope.message = response.data;
	  	    	  alert($rootScope.message);
	  	      });
	      }, function errorCallback(response) {
	          // called asynchronously if an error occurs
	          // or server returns response with an error status.
	    	  $rootScope.message = response.data;
	    	  alert($rootScope.message);
	      });
		
	}
	
}])
.controller('AddressManagementCtrl',['$scope','$rootScope','$http','$route', '$routeParams', '$location'
                                     ,function($scope,$rootScope,$http,$route, $routeParams, $location){
	
}])
.controller('CheckEmailCtrl', ['$route', '$routeParams', '$location',
  function($scope,$http,$route, $routeParams, $location) {
    this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
}])
.controller('RegisterCtrl', ['$scope','$rootScope','$http','$location','$routeParams', function($scope,$rootScope,$http,$location,$routeParams) {
  this.name = "RegisterCtrl";
  this.params = $routeParams;
  $scope.register = function(user){
	  $http({
          method: 'POST',
          url: 'http://localhost:8080/rath/webapi/register/',
          headers: {
        	  'Content-Type': 'application/json'
        	 },
	  		data : $scope.user
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.currentuser = response.data;
          if (($scope.createduser.role == 'CUSTOMER')&&($scope.createduser.id != null)){
        	  $location.path("/checkemail");
          }

      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
    	  $rootScope.message = response.data;
    	  alert($rootScope.message);
      });
  }
}])
.controller('MenuCtrl', ['$scope','$rootScope','$http','$routeParams', function($scope,$rootScope,$http,$routeParams,$mdToast) {
  this.name = "MenuCtrl";
  this.params = $routeParams;
  angular.element(document).ready(function(){
	  $http({
          method: 'GET',
          url: 'http://localhost:8080/rath/webapi/menuitem/'
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
          $rootScope.menuitems = response.data;
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
      });
  });
}])


.controller('LoginCtrl', ['$scope','$rootScope','$http','$location','$routeParams', function($scope,$rootScope,$http,$location,$routeParams) {
  this.name = "LoginCtrl";
  this.params = $routeParams;
  $scope.login = function(usercredentials){
	  $http({
          method: 'POST',
          url: 'http://localhost:8080/rath/webapi/login/',
          headers: {
        	  'Content-Type': 'application/json' 		  
        	 },
    	  data : usercredentials
      }).then(function successCallback(response) {
          // this callback will be called asynchronously
          // when the response is available
    	  $rootScope.access_token = response.data.access_token;
    	  $rootScope.user = response.data.user;
    	  if (response.status==200){
    		  $rootScope.error='';
    		  if (response.data.user.role=='ADMIN'){
    			  $location.path("/admin");
    		  }else if(response.data.user.role=='STAFF'){
    			  $location.path("/staff");
    		  }else if (response.data.user.role=='CUSTOMER'){
    			  $location.path("/customer");
    		  }
    	  }
    	  else{
    		  $location.path("/login");
    	  }
      }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
    	  $location.path("/login");
    	  if(response.status==401){
    		  $rootScope.error="Your username and password combination is wrong"
    	  }
    	  
      });
  }
}]);

