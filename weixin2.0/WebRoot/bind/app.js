(function(){
	var state = {
		'isValidate' : false,
		'baseURL': 'http://weixin2.ngrok.natapp.cn/weixin2.0/bind'
	};

	var initInputEvent = function () {
		var input = document.querySelectorAll("input[required]");
		for (var i = 0; i < input.length; i++) {
			input[i].addEventListener('focus',function () {
				 this.style.backgroundColor = ''; 
			})
		}
	}
	//提交之前判断是否有空白
	var isBlank = function () {
		var account = document.getElementById("account");
		var name = document.getElementById("name");
		var clazz = document.getElementById("class");
		if( account.value.trim() === "" || name.value.trim() === "" || clazz.value.trim() === "" ) {
			return true;
		}
		return false;
	}

	var errRender = function () {
		// render err msg
		console.log('render err msg');
		this.style.backgroundColor = "rgba(255,102,102,.4)";
		// this.style.backgroundColor = "#fff";
	}

	// 序列化表单
	var serialize = function(){
		var paras = {};
		var url = '';
		var input = document.querySelectorAll('input[required]');
		for (var i = 0; i < input.length; i++) {
			paras[input[i].getAttribute('name')] = input[i].value; 
		}
		for (el in paras) {
			if (paras.hasOwnProperty(el)) {
				url += el + "=" + paras[el] +"&";
			}
		}
		url = state.baseURL + "?" + url;
		var tempStr = getParam();
		url += tempStr;
		console.log(url);
//		return url.slice(0, -1);
		return url;
	}

	var send = function(){
		var xhr = new XMLHttpRequest();
		var url = serialize();
		xhr.open("get",url,true);
		xhr.send(null);
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if ((xhr.status >= 200 && xhr.status <= 300) || xhr.status == 304) {
					// console.log(xhr.responseText);
					//状态码
					var str = xhr.responseText;
					if( str == 200){
						// appointement success render
						alert("绑定成功！");
					} else {
						// has appointmented render
						alert("对不起，你已绑定！");
					}
				} else {
					// net err
					alert("网络出错，请重试！");
				}
			}
		};
	};

	var initSubmitEvent = function () {
		var btn = document.getElementById("submit");
		btn.addEventListener('click',function(e){
			e.preventDefault();
			if( !isBlank() ) {
				send();
			} else {
				alert("输入格式错误");
			}
		})
	}
	var init = function(){
		initInputEvent();
		// init submit event
		initSubmitEvent();
	};
	//获取传过来的参数
	function getParam() {
		var url = location.search;
		if(url.indexOf("?") != -1) {
			var str = url.substr(1);
			return str;
		}
		return "";
	}
	window.onload = init;
}())