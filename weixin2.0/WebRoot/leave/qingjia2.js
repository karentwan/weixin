   var name=document.getElementById("name").value;
	var account=document.getElementById("account").value;
	var startTime =document.getElementById("date-container").value;
	var dateCount =document.getElementById("dateCount").value;
	var reason=document.getElementById("reason").value;
	var reasonDetail=document.getElementById("reasonDetail").value;
	var s=startTime.slice(0,4);
	var s2=startTime.slice(6);
	var s3=s2.replace("月","-");
	var s4=s3.substring(0,s3.length-1);
	startTime=s+"-"+s4;
	startTime=startTime.replace(/\s/g,"");
	startTime=startTime.replace(/\s/g,"");
	poststr="accout="+account+"&name="+name+"&startTime="+startTime+"&dateCount="+dateCount+"&reason="+reason
	+"&reasonDetail="+reasonDetail;
	url="";
	var send = function(){
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("post",url,true);
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlhttp.send(poststr);
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4){
				if (xmlhttp.status == 200) {
					var last=document.getElementById("last");
					last.innerHTML="填写成功!";
					last.style.color=red;
					
				} else {
					
					alert("页面出错，请重试！");
				}
			}
		};
	};
	var Submit = function () {
		var submit = document.getElementById("submit");
		submit.addEventListener('click',function(){
			send();});
	}
	function init(){
		Submit();
	}
	init();