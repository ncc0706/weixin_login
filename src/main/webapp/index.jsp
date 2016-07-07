<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<script src="//cdn.bootcss.com/jquery/1.12.0/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    var uuid;
	    
	    $.get("/qrgen?cmd=showQrGen", function(data, status) {
	        var obj = eval("(" + data + ")");
	        //设置该uuid值
	        uuid = obj.uuid;
	        //设置二维码图片地址
	        $("#QrGen").attr("src", obj.img);
	        //检查验证登录
	        checkScan();
	    });
	    function checkScan() {
	        setInterval(function() {
	            $.get("/check?cmd=checkScan&uuid=" + uuid,
	                    function(data, status) {
	                        if (data == "ok") {
	                            //验证成功并重定向到welcome页面
	                            window.location = "welcome.jsp";
	                        }
	                    });
	        },4000)
	    }
	});
</script>
</head>
<body>
<h2>Hello World!</h2>

<img id="QrGen" src="" />
</body>
</html>
