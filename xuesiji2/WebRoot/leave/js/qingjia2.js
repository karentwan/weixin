	function ischinese(e){
		var exp=/([\u4e00-\u9fa5]|[a-zA-Z])+/;
		str=e.match(exp);
		if(str!=null){
			return true;
		}
		else 
			return false;
	}
	function isvalid(name,account,startTime,endTime){ 
		if(name!="" && account!="" && startTime!="" && endTime !=""){
			if(!isNaN(account) && ischinese(name)){
				if(startTime<=endTime){
					return true;
				}else{
					alert("时间输入错误!");
					return false;
				}
				
			}
			else
				return false;
		}
		else 
			return false;
	}
	    function send(){
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("post",url,true);
		//alert("url:" + url);
		xmlhttp.setRequestHeader("content-length",poststr.length);
		xmlhttp.setRequestHeader("CONTENT-TYPE", "application/x-www-form-urlencoded");
		xmlhttp.send(poststr);
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4){
				if ((xmlhttp.status >= 200 && xmlhttp.status <= 300) || xmlhttp.status == 304) {
					// console.log(xhr.responseText);
					if(xmlhttp.responseText == 200){
						// appointement success render
						alert("填写成功!");
					} else {
						// has appointmented render
						alert("填写失败!");
					}
				} else {
					document.write(xmlhttp.status);
					alert("页面出错，请重试！");
				}
			}
		}
	}
		
	function init(){
		var name=document.getElementById("name").value;
		var account=document.getElementById("account").value;
		var startTime =document.getElementById("startTime").value;
		var reasonDetail=document.getElementById("reasonDetail").value;
		var endTime=document.getElementById("endTime").value;
		//alert(reason);
		var s=startTime.split("-");
		str=s[2]+"-"+s[1]+"-"+s[0];
		startTime=str;
		var e=endTime.split("-");
		e2=e[2]+"-"+e[1]+"-"+e[0];
		endTime=e2;
		/*var s=startTime.slice(0,4);
		var s2=startTime.slice(6);
		var s3=s2.replace("月","-");
		var s4=s3.substring(0,s3.length-1);
		startTime=s+"-"+s4;
		startTime=startTime.replace(/\s/g,"");
		startTime=startTime.replace(/\s/g,"");*/
		var boolean=isvalid(name,account,startTime,endTime);
		if(boolean){
			poststr="account="+account+"&name="+name+"&startTime="+startTime+"&endTime="+endTime+"&reason="+reasonDetail;
			url="http://weixin2.ngrok.natapp.cn/weixin2.0/leave";
			//alert(url);
			send();
		}
		else{
			alert("输入错误!");
			}
	}
		var submit = document.getElementById("submit");
		submit.addEventListener('click',init);