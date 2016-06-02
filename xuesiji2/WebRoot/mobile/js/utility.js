
/****************娴忚鍣ㄥ吋瀹�*******************/
// for xhr
(function(){
	if (window.XMLHttpRequest === undefined) {
		window.XMLHttpRequest = function () {
			try{
				return new ActiveXObject("Msml2.XMLHTTP.6.0");
			} catch(e1) {
				try {
					return new ActiveXObject("Msml2.XMLHTTP.3.0");
				} catch(e2) {
					throw new Error("XMLHttpRequest is not support");
				}
			}
		};
	}
}());
// for eventListen
var bind = function(el, eventType, callback){
	if (el.addEventListener) {
		el.addEventListener(eventType, callback, false);
	} else if (el.attachEvent) {
		el.attachEvent(eventType, callback);
	}
};
/****************End娴忚鍣ㄥ吋瀹�*******************/


/***************Cookies Managent**************************/
/**
 * isCookiesEnable
 * 纭畾娴忚鍣ㄦ槸鍚﹀紑鍚疌ookie
 * @return {Boolean} 寮�惎 ? ture : false
 */
var isCookiesEnable = function(){
	return window.navigator.cookieEnabled;
};

var getAllCookies = function(){
	return document.cookie;
};

var generateCookieObj = function(){
	if (!isCookiesEnable()) {
		return false;
	}
	var cookiesArr = getAllCookies();
	// TODO: 濡傛灉娌℃湁鑾峰彇鍒癱ookie鑷姩璺宠浆鍒扮櫥闄嗛〉闈�	
	var obj = {};
	var key = null, value = null;
	for (var i = 0; i < cookiesArr.length; i++) {
		key = cookiesArr[i].split('=')[0];
		value = cookiesArr[i].split('=')[1];
		obj[key] = value;
	}
	key = value = null;

	return obj;
};
/***************End Cookies Managent**************************/


/******* Ajax *************************************/
var obj2query = function(obj){
	var query = "";
	for (var item in obj) {
		if (obj.hasOwnProperty(item)) {
			// console.log("item is " + item + " and obj.item is " + obj[item]);
			query += item + "=" + obj[item] + "&";
		}
	}
	return query.substring(0, query.length-1);
};
var request = function(obj){

	// setting extend
	var url = obj.url || "";
	var method = obj.method || "GET";
	var data = obj.data || "";
	var callback = obj.callback || undefined;
	var json = obj.json || false;

	var xhr = new XMLHttpRequest();
	xhr.open(method, url);
	if (!json) xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); 
//	else xhr.setRequestHeader("Content-Type","text/json; charset=UTF-8");
	xhr.onreadystatechange = function(){
		if (xhr.readyState === 4 && xhr.status === 200) {
			callback ? callback(xhr.responseText) : "";  
		}
	};
	console.log(obj2query(data));
	json ? xhr.send(JSON.stringify(data)) : xhr.send(obj2query(data));
};
/******* End Ajax *************************************/