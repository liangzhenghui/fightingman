config = {
	pageNum : 1,
	error : {
		network : "网络通讯出现问题，请检查网络",
		unauthorized : "真抱歉,您还没权限访问该资源"
	},
	success : {
		no_result : "查无结果"
	}
}

function form2Json(id) {
	var arr = $("#" + id).serializeArray();
	console.log("form2Json=" + arr);
	var jsonStr = "";
	jsonStr += '{';
	for (var i = 0; i < arr.length; i++) {
		jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	}
	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
	jsonStr += '}';
	var json;
	if (typeof (JSON) == 'undefined') {
		json = eval("(" + jsonStr + ")");
	} else {
		json = JSON.parse(jsonStr);
	}
	console.log(json);
	return json;
	// return jsonStr;
};

function form2JsonStr(id) {
	var arr = $("#" + id).serializeArray();
	var jsonStr = "";
	jsonStr += '{';
	for (var i = 0; i < arr.length; i++) {
		jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	}
	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
	jsonStr += '}';
	console.log("form2JsonStr=" + jsonStr);
	return jsonStr;
};

function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	return year + "-" + month + "-" + date;
}

// ajax封装
function commonAjax(opt, successFun, errorFun) {
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var type = opt.type || 'post';// 请求类型
	var dataType = opt.dataType || 'json';// 接收数据类型
	var success = function(data) {
		if (data.errorType == "0") {
			successFun(data);
		} else {
			$.messager.show({
                title: '提示',
                msg: data.errorMessage
            });
		}
	}
	var error = error || function(data) {
		$.messager.show({
            title: '提示',
            msg: config.error.network
        });
	};
	$.ajax({
		'url' : opt.url,
		'data' : opt.data,
		'type' : type,
		'dataType' : dataType,
		'success' : success,
		'error' : errorFun,
		'beforeSend' : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
	});
}
