(function(){
	'use strict';

	var data = '{ "signIn": {"150451":[{"account" :"15045103", "name" :"??"} ] }, "disconnect" :{"150452":[{"account" :"15045203", "name" :"???"} ], "150451":[{"account" :"15045103", "name" :"??"}, {"account" :"15045102", "name" :"???"}, {"account" :"15045108", "name" :"??"}, {"account" :"15045104","name" :"???"}, {"account" :"15045117","name" :"???"}, {"account" :"15045105","name" :"???"}, {"account" :"15045127","name" :"???"}]}, "leave" :{}}';


	var base = {
		"URL" : "http://weixin2.ngrok.natapp.cn/weixin2.0/login",
		"inputs_id_of":{
			'name' : "username",
			'psd' : "passwd"
		},
		"cookie": false
	};

	var readCookie = function(){
		// 读取cookie
	};



	var login = function(){
		if (base.cookie) {
			// login
			// post cookie 
		}
	};
	var handler = function(){
		console.log('handler input');
		var value = this.value;
		console.log(filter.isEmpty(value));
		if (filter.isEmpty(value)) {
			if (! this.check(value)) {
				//错误标志 
				base.inputError = true;
				// err render
				// this.ffoucs();
				console.log(this);
				alert('inputError');
			}
		} else {
			// 输入为空提醒
			console.log(this);
			this.focus();
			alert("empty input");
		}
	};
	var filter = {
		'isChinese'      : function(){},
		'isNumber'       : function(){
			return false;
		},
		'isAlphanumeric' : function(){
			return false;
		},
		'isEmpty'        : function(data){
			if (data.trim()) {
				return false;
			} else {
				return true;
			}
		}
	};

	var eventBind = function(el, eventType, callback){
		if (el.addEventListener) {
			el.addEventListener(eventType, callback, false);
		} else if (el.attachEvent) {
			el.attachEvent(eventType, callback);
		}
	};

	var init = function(){
		// 1. store el
		console.log('test');
		base.username = document.getElementById(base.inputs_id_of.username);
		base.password = document.getElementById(base.inputs_id_of.password);
		base.username.check = filter.isNumber;
		base.password.check = filter.isAlphanumeric;
		console.log(base);
		// 2. Event listener
		eventBind(base.username, 'change', handler);
		eventBind(base.password, 'change', handler);


	};

	window.onload = init;

	// test function isEmpty
/*	var s = "ssss  ";
	filter.isEmpty(s);
*/
}());





