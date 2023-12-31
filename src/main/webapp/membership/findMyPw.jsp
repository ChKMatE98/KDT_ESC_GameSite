<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>

    <link href="https://fonts.googleapis.com/earlyaccess/nanumpenscript.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Oswald&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@3.0.5/dist/js.cookie.min.js"></script>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        h1 {
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .btn-group {
            display: flex;
            justify-content: center;
            text-align: center;
        }

        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 10px;
            text-decoration: none;
            color: #007bff;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>비밀번호 찾기</h1>
        <form id="frm">
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="text" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="id">아이디:</label>
                <input type="text" id="id" name="id" required>
            </div>
            <div class="btn-group">
                <button type="submit">비밀번호 찾기</button>
            </div>
        </form>
        <a href="/index.jsp">홈으로 돌아가기</a>
    </div>

    <script>
	    $(document).ready(function () {
	        // 폼 전송 이벤트 리스너 등록
	        $("#frm").submit(function (e) {
	            e.preventDefault(); // 기본 폼 전송 방지
	
	            // 버튼 비활성화 및 텍스트 변경
	            var submitButton = $("button[type='submit']");
	            submitButton.prop("disabled", true);
	            submitButton.css("background-color", "black"); // 색상을 검은색으로 변경
	            submitButton.text("Wait..."); // 텍스트를 'Wait...'로 변경
	
	            // 입력된 데이터 가져오기
	            var name = $("#name").val();
	            var email = $("#email").val();
	            var id = $("#id").val();
	
	            // AJAX 요청 설정
	            $.ajax({
	                type: "POST",
	                url: "/tempPwRelease.members",
	                data: {
	                    name: name,
	                    email: email,
	                    id: id
	                },
	                success: function (response) {
	                    // 버튼 활성화 및 원래대로 텍스트 변경
	                    submitButton.prop("disabled", false);
	                    submitButton.css("background-color", "#007bff"); // 기본 색상으로 변경
	                    submitButton.text("비밀번호 찾기");
	
	                    if (response === "1") {
	                        alert("적어주신 이메일로 임시 비밀번호를 발급해드렸습니다. 추후에 변경해주시길 바랍니다.");
	                        location.href = "/updateBack.members";
	                    } else {
	                        alert("이메일이 도착하지 않았습니다. 입력하신 이름과 이메일, 아이디를 확인해주세요");
	                    }
	                },
	                error: function (error) {
	                    // 에러 처리
	                    alert("An error occurred: " + error.responseText);
	
	                    // 버튼 활성화 및 원래대로 텍스트 변경
	                    submitButton.prop("disabled", false);
	                    submitButton.css("background-color", "#007bff"); // 기본 색상으로 변경
	                    submitButton.text("비밀번호 찾기");
	                }
	            });
	        });
	    });
	</script>
</body>

</html>