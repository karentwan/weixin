/*	window.onload = function(){
	var obj_select = document.getElementById("slct");
	var obj_div = document.getElementById("reasonDetail");
	obj_select.onchange = function(){
	obj_div.style.display = this.value==3? "block" : "none";
	}
}*/
	function ischinese(e){
		var exp=/[\u4e00-\u9fa5]+/;
		str=e.match(exp);
		if(str!=null){
			return true;
		}
		else 
			return false;
	}
	function isvalid(name,account,startTime,dateCount){ 
		alert("isValid start....");
		alert("name:" + name+"\naccount:" + account + "\nstartTime:" + startTime+ "\ndateCount:" + dateCount);
		if(name!="" && account!="" && startTime!="" && dateCount !=""){
        //alert("isNaN:" + (!isNaN(account)) + "isChinese:" + ischinese(name) + "dateCOunt" + (!isNaN(dateCount)));
			if(!isNaN(account) && ischinese(name) && !isNaN(dateCount)){
				return true;
			}
			else
				return false;
		}
		else 
			return false;
	}
	    function send(){
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("get",url,true);
		//alert("url:" + url);
		xmlhttp.send(null);
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4){
				if ((xhr.status >= 200 && xhr.status <= 300) || xhr.status == 304) {
					// console.log(xhr.responseText);
					if(xhr.responseText == 200){
						// appointement success render
						alert("填写成功!");
					} else {
						// has appointmented render
						alert("填写失败!");
					}
				} else {
					
					alert("页面出错，请重试！");
				}
			}
		}
	}
		
	function init(){
		var name=document.getElementById("name").value;
		alert(name);
		var account=document.getElementById("account").value;
		var startTime =document.getElementById("startTime").value;
		var dateCount =document.getElementById("dateCount").value;
		var reasonDetail=document.getElementById("reasonDetail").value;
		var reason=document.getElementById("reason").value;
		alert(reason+dateCount);
		if(reason=="其他"){
			reason=reasonDetail;
		}

		/*var s=startTime.slice(0,4);
		var s2=startTime.slice(6);
		var s3=s2.replace("月","-");
		var s4=s3.substring(0,s3.length-1);
		startTime=s+"-"+s4;
		startTime=startTime.replace(/\s/g,"");
		startTime=startTime.replace(/\s/g,"");*/
		var boolean=isvalid(name,account,startTime,dateCount);
		if(boolean){
			poststr="account="+account+"&name="+name+"&startTime="+startTime+"&dateCount="+dateCount+"&reason="+reason;
			url="http://weixin2.ngrok.natapp.cn/weixin2.0/leave?"+poststr;
			//alert(url);
			send();
		}
		else{
			alert("输入错误!");
			}
	}
		var submit = document.getElementById("submit");
		submit.addEventListener('click',init);