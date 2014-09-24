function HtmlEncode(text) {
	return obj.list[0].topics[0].content;
}

$(function() {

	$('#convert').click(function(e) {
		e.preventDefault();
		post();
	});

	function post() {
		var url = "http://localhost:8015/tiku/service/topic/getTopicList";
		var params = {
			docId : 1
		};
		$.ajax({
			type : 'post',
			url : url,
			data : params,
			dataType : 'json',
			timeout : 30 * 1000
		}).fail(function(jqXHR, textStatus) {
			alert('接口请求失败:' + textStatus);
			return false;
		}).done(function(data, textStatus) {
			console.log("data", data);
			alert('请求成功:' + textStatus);
			$('#after').val(data);
		});
	}
});