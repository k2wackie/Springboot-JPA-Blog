const index = {
	init: function() {
		$("#btn-save").on("click", () => {  //this를 바인딩하기 위해서 화살표 함수 사용
			this.save();
		});
		$("#btn-delete").on("click", () => {  //this를 바인딩하기 위해서 화살표 함수 사용
			this.deleteById();
		});
		$("#btn-update").on("click", () => {  //this를 바인딩하기 위해서 화살표 함수 사용
			this.update();
		});
	},

	save: function() {
		//alert("user의 save함수 호출됨");
		const data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById: function() {
		const id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(res) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	update: function() {
		const id = $("#id").val();

		const data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			alert("수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
};

index.init();