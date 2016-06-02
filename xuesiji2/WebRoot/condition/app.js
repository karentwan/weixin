(function(){
	var state = {
		'isValidate' : false,
		'errCount' : 0,
//		 'baseURL': 'http://070411.com/xuesiji/condition'
//		'baseURL' : 'http://localhost:8080/xuesiji/condition'
		'baseURL' : "http://weixin.izayl.com/test/condition"
	};

	// init validate
	var validate = function () {
		// clear 
		state.isValidate = false;

		// base validate
		var current = this.value.length;
		var min = parseInt(this.getAttribute("minlength"));
		var max = parseInt(this.getAttribute("maxlength"));
		if ((current >= min) && (current <= max)) {
			console.log('OK');
		} else {
			console.log(typeof min);
			console.log('error');
			// render err in this input 
			errRender.apply(this);
			state.isValidate = false;
			state.errCount += 1;
			return;
		}

		// specific validate 
		switch(this.getAttribute('id')) {
			case 'floor': // number only
			case 'dormitory': // number only
			case 'personCount': // number only
			case 'tel':
				// number only
				var reg = /^\d+$/;
				// console.log(reg.test(this.value));
				if(reg.test(this.value)){
					state.isValidate = true;
				} else {
					// input err render
					state.errCount += 1;
					errRender.apply(this);
				}
				break;
			case 'contacts':
				// chinese chracters only
				// var reg = new RegExp(' /^[\u4E00-\u9FA3]{1,}$/ ');
				// console.log("娑擃厽鏋�Match "+reg.test(this.value));
				break;
		}
	}
	// blank test
	var isblank = function () {
		var input = document.querySelectorAll('input[required]');
		for (var i = 0; i < input.length; i++) {
			if (input[i].value.trim() === "") {return false;}
		}

		return true;
	}
	var initInputEvent = function () {
		var input = document.querySelectorAll("input[required]");
		for (var i = 0; i < input.length; i++) {
			input[i].addEventListener('change', validate);
			input[i].addEventListener('focus',function () {
				 this.style.backgroundColor = ''; 
			});
		}
	}

	var errRender = function () {
		// render err msg
		console.log('render err msg');
		this.style.backgroundColor = "rgba(255,102,102,.4)";
		// this.style.backgroundColor = "#fff";
	}

	// 鎼村繐鍨崠鏍�閸楋拷	
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
		console.log(url);
		return encodeURI(encodeURI(url.slice(0, -1)));
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
					if(xhr.responseText == "200"){
						// appointement success render
						alert("提交成功！");
					} else {
						// has appointmented render
						alert("当前寝室已预约！");
					}
				} else {
					// net err
					alert("网络错误！请重试！");
				}
			}
		};
	};

	var initSubmitEvent = function () {
		var btn = document.getElementById("submit");
		btn.addEventListener('click',function(e){
			e.preventDefault();
			if(!isblank()){
				alert("当前内容不能为空！");
				return false;
			}
			if (state.errCount === 0) {
				// send data
				send();
			} else {
				alert("填写格式错误！");
				state.errCount = 0;
			}
		});
	}
	// var initMsg = function(){
	// 	var msg = document.getElementsByClassName("detail")[0];
	// 	msg.addEventListener('click', extend);
	// 	var extend = function(){
	// 		console.log(this);
	// 		this.addClass('click');
	// 	}
	// }
	var init = function(){
		// init msg
		// initMsg();
		// init input validate event
		
//		var ua = navigator.userAgent.toLowerCase();
//		if(! /micromessenger/.test(ua)){
//		  var body = document.getElementsByTagName("body")[0];
//		  body.innerHTML = '<img src="QR.png"/>';
//		  console.log(!/micromessenger/.test(ua));
//		  return false;
//		}
		
		
		initInputEvent();
		// init submit event
		initSubmitEvent();
	};

	window.onload = init;
}())