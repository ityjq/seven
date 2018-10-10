 //控制层 
app.controller('userController' ,function($scope,$controller,uploadService,userService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		userService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		userService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		userService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){

		//判断密码
		if($scope.entity.password != $scope.password){
			alert("密码不一致，请重新输入...");
			return ;
		}

		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=userService.update( $scope.entity ); //修改  
		}else{
			serviceObject=userService.add( $scope.entity ,$scope.smsCode );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//注册成功后，跳转到登录页面(修改：跳转到个人中心的首页)
					location.href="/home-index.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		userService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		userService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//发送短信的方法
	$scope.sendSms=function(){
        userService.sendSms($scope.entity.phone).success(function (response) {
            if(response.success){
            	alert("验证码已发送");
			} else{
            	alert(response.message);
			}
        })
	}

	//用户首页初始化，获取当前登录者用户信息
	$scope.showName=function(){
		userService.showName().success(function (response) {
			$scope.userMap = response;
        })
	}


    $scope.findSeckill=function () {
        userService.findSeckill().success(function (response) {
            $scope.list=response;
        })
    }


    //定义一个数组：把数组根据索引指定状态
    $scope.statusList=["未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];
    $scope.statusList2=["立即付款","等待发货","催商家发货","确认收货","评价"];
    $scope.findDingdan=function () {
        userService.findDingdan().success(function (response) {
            $scope.list=response;

        })
    }







    $scope.myInFo=function(){
        userService.myInFo().success(function (response) {
            $scope.userInFo = response;
            $scope.entityUser= $scope.userInFo;
        })
    }
    $scope.address=function(){
        userService.address().success(function (response) {
            $scope.addressList = response;
        })
    }
    $scope.edit1=function(addressedit1){

            $scope.addressedit1 = addressedit1;

    }


	$scope.updeteUser=function(){
        userService.updeteUser($scope.entityUser).success(function (response) {
            if(response.success){
                //注册成功后，跳转到登录页面(修改：跳转到个人中心的首页)
                location.href="/home-index.html";
            }else{
                alert(response.message);
            }
        })
    }


    $scope.uploadFile=function(){
        uploadService.uploadFile().success(function (response) {
            if(response.success){
                $scope.entityUser.user.headPic=response.message;
            }
        })
    }

});	
