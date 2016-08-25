<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>后台生活 - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="../favicon.ico">
    <link href="../css/bootstrap.min14ed.css" rel="stylesheet">
    <link href="../css/font-awesome.min93e3.css" rel="stylesheet">

    <link href="../css/animate.min.css" rel="stylesheet">
    <link href="../css/style.min862f.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
</head>
<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">live</h1>

            </div>
            <h3>生活服务</h3>

            <form class="m-t" role="form" action="">
            	
                <div class="form-group">
                    <input type="email" class="account form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" class="pwd form-control" placeholder="密码" required="">
                </div>
                <input type="button" class="btn btn-primary block full-width m-b" value="登 录">

            </form>
        </div>
    </div>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/verify.js"></script>
    <script src="../js/util.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/stats.js" charset="UTF-8"></script>
    <script type="text/javascript">
    $(function(){
    	$(".btn").click(function(){
    		var account=$(".account").val();
    		var pwd=$(".pwd").val();
    		if(!verifys(account,"notNull")||account==''||account==null){
    			verifyClass(".account","账号不能为空和不能有空格符号");
    			return;
    		};
    		if(!verifys(pwd,"notNull")||pwd==''||pwd==null){
    			verifyClass(".pwd","密码不能为空和不能有空格符号");
    			return;
    		};
    		var url="../api/adminUser/login";
    		var reqDate={
    				"account":account,
    				"pwd":pwd
    		};
    		console.log(reqDate);
    		$.post(url,reqDate,function(data){
    			if(data.code!=0){
    				verifyClass(".account",data.errorMessage);
    				verifyClass(".pwd",data.errorMessage);
    				return;
    			}
    			httpJump("../404.html");
    		});
    	});
    });
    </script>

</body>
</html>