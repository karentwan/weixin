

/*********************数据处理过程***************************************/
	// var requestConfig = {
	// 	"url" : "server.php",
	// 	"method": "POST"
	// };

	var data = {};
	
	// 查看详细情况，根据状态查数据
	var renderByStatus = function(status){
		console.log(status);
		// 1. table 上标注 签到 、请假、 失联
		var statusArr = {"signIn": "已签到学生", "leave": "已请假学生", "disconnect": "失联学生"};
		var o = data[status];
		var el = document.createElement("h3");
		el.innerHTML = statusArr[status];
		var container = document.getElementsByClassName("container")[0];
		var table = document.getElementById("table");
		container.insertBefore(el, table);
		

		// 2. 填充表格 班级 学号 姓名
		var html = "";
		var l = 0;
		// thead
		table.getElementsByTagName("thead")[0].innerHTML = 
			'<tr>' +
				'<th>班级</th>' +
				'<th>学号</th>' +
				'<th>姓名</th>' +
				'<th>备注</th>' +
			'</tr>';
		// tbody
		for (var item in o) {
			// item : classid
			if (o.hasOwnProperty(item)) {
				for (var i = 0; i < o[item].length; i++) {
					l++;
					html += 
						'<tr>' +
							'<th>'+ item +'</th>' +
							'<th>'+ o[item][i].account +'</th>' +
							'<th>'+ o[item][i].name +'</th>';
					if(o[item][i].reason) html += '<th>'+ o[item][i].reason +'</th>' ;
					else html += '<th></th>';
					html += '</tr>';
					
				}
			}
		}
		table.getElementsByTagName("tbody")[0].innerHTML = html;

		// tfoot
		table.getElementsByTagName("tfoot")[0].innerHTML = 
			'<tr>' +
				'<th>总计</th>' +
				'<th>' + l + '</th>' +
				'<th></th>' +
				'<th></th>' +
			'</tr>';
	};
	
	// 查看汇总情况
	var renderSummary = function(){
		var leaveNum = data.summary.leave;
		var signInNum = data.summary.signIn;
		var disconnectNum = data.summary.disconnect;
		
		document.getElementsByClassName("panel-primary")[0].getElementsByTagName("p")[0].innerHTML = signInNum;
		document.getElementsByClassName("panel-warning")[0].getElementsByTagName("p")[0].innerHTML = leaveNum;
		document.getElementsByClassName("panel-danger")[0].getElementsByTagName("p")[0].innerHTML = disconnectNum;
	};
	
	// 返回query对象
	var parseQuery = function(){
		var search = window.location.search;
		search = search.substr(1,search.length);
		var queryArr = search.split("&");
		var o = {};
		for (var i = 0; i < queryArr.length; i++) {
			var prop = queryArr[i].split("=")[0];
			var value = queryArr[i].split("=")[1];
			o[prop] = value;
		}
		queryArr = null;
		return o;
	};
	
	// 根据query来渲染
	var render = function(){
		var query = parseQuery();
		if (query.status) {
			renderByStatus(query.status);
		} else {
			renderSummary();
		}
	}
	// 获取服务器端原始数据，交给initialData处理
	var generateInitailData = function(){
		// initial setup data
		var cookies = generateCookieObj();
		var month = new Date().getMonth() + 1;
		month = month > 10 ?  month : '0' + month;
		var d = new Date().getDate();
		d = d > 10 ? d : '0' + d;
		var time = (new Date().getFullYear()) +"-"+ month +"-"+ d;
		requestConfig.data = {
			// name: cookies.name,
			name: parseQuery().name,
			time: time
		};
		// 响应数据后渲染汇总数据
		requestConfig.callback = initialData;
		request(requestConfig);
	};


	/**
	 * 初始化数据，形成可用的新数据格式，交给下一个函数分类
	 * @param  {JSON字符串} data [description]
	 * @return {null}      
	 */
	var initialData = function(initData){
		var json = JSON.parse(initData);

		// 1. 按签到情况生成分类数据和统计汇总
		dataSortByStatus(json);

		// 2. 检索班级，分离出班级列表
		classSeparate(json);

		// 3. 检索学生，按班级分离出学生表
		studentSeparate(json);

		// 4. 生成今日汇总
		getTodaySummary(json);

		render();
	};

	// 按签到状态存储至全局变量
	var dataSortByStatus = function(json){
		data.signIn = json.signIn;
		data.disconnect = json.disconnect;
		data.leave = json.leave;
	};

	// 获取班级列表存储至全局变量
	var classSeparate = function(json){
		// 将班级列表存储在全局对象中
		data.class = [];
		var classCache = [];
		// 1. 读取所有班级列表（存在重复项）
		for (var item in json) {
			if (json.hasOwnProperty(item)) {
				classCache = classCache.concat(Object.getOwnPropertyNames(json[item]));
			}
		}
		// 2. 排序， 为去除重复项做准备
		classCache = classCache.sort(function(left, right){return (left - right);});
		// 3. 遍历数组，为了支持IE8，不使用数组自带的遍历方法
		for (var i = 0; i < classCache.length; i++) {
			if (classCache[i] === classCache[i+1]) {
				classCache.splice(i, 1);
				i--;
			}
		}
		data.class = classCache;
		classCache = null;
	};

	// 获取班级对应的学生,存储至全局变量
	var studentSeparate = function(json){
		console.info("in func studentSeparate");
		if (!data.class) {
			classSeparate(json);
		}
		data.students = {};
		var students = {};
		for (var item in json) {
			// item : signIn / disconnect / leave
			if (json.hasOwnProperty(item)) {
				for (var prop in json[item]) {
					// prop : 班级号
					if (json[item].hasOwnProperty(prop)) {
						for (var i = 0; i < json[item][prop].length; i++) {
							json[item][prop][i].status = item;
						}
						students[prop] = students[prop] || [];
						students[prop] = students[prop].concat(json[item][prop]);
					}
				}
			}
		}
		// console.log(students);
		data.students = students;
		students = null;
		console.info("out func studentSeparate");
	};

	// 获取当天汇总数据
	var getTodaySummary = function(json){
		var signInNum = 0,
			 disconnectNum = 0,
		 	leaveNum = 0;

		for (var item in json) {
			;// item : status
			if (json.hasOwnProperty(item)) {
				for (var prop in json[item]) {
					// prop : classid
					if (json[item].hasOwnProperty(prop)) {
						// console.log(item);
						switch(item) {
							case "signIn":
								signInNum = signInNum + json[item][prop].length;
								// console.log("switch signIn and signInNum = " + signInNum);
								break;
							case "disconnect":
								disconnectNum = disconnectNum + json[item][prop].length;
								break;
							case "leave":
								leaveNum = leaveNum + json[item][prop].length;
								break;
						}
					}
				}	
			}
		}
		data.summary = {
			"signIn" : signInNum,
			"disconnect" : disconnectNum,
			"leave" : leaveNum
		};
	};
/*********************End 数据处理过程***************************************/
// 执行数据初始化
	var dataInit = function(){
		// 存储UserName
		//TODO : 从URL中读取userName
		data.username = parseQuery().name;
		generateInitailData();
	};

	dataInit();

/*************************************************************************/

