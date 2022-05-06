<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>

<style>
	main {
        height: auto;
        min-height: 100%;
        padding-bottom: 300px;
    }
    th{
        width: 90px;
    }
    td, input{
        width: 80%;
    }
    
   
</style>
<script>
function autoHypenTel(str) {
    str = str.replace(/[^0-9]/g, '');
    var tmp = '';

    if (str.substring(0, 2) == 02) {
        // 서울 전화번호일 경우 10자리까지만 나타나고 그 이상의 자리수는 자동삭제
        if (str.length < 3) {
        return str;
        } else if (str.length < 6) {
        tmp += str.substr(0, 2);
        tmp += '-';
        tmp += str.substr(2);
        return tmp;
        } else if (str.length < 10) {
        tmp += str.substr(0, 2);
        tmp += '-';
        tmp += str.substr(2, 3);
        tmp += '-';
        tmp += str.substr(5);
        return tmp;
        } else {
        tmp += str.substr(0, 2);
        tmp += '-';
        tmp += str.substr(2, 4);
        tmp += '-';
        tmp += str.substr(6, 4);
        return tmp;
        }
    } else {
        // 핸드폰 및 다른 지역 전화번호 일 경우
        if (str.length < 4) {
        return str;
        } else if (str.length < 7) {
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3);
        return tmp;
        } else if (str.length < 11) {
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 3);
        tmp += '-';
        tmp += str.substr(6);
        return tmp;
        } else {
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 4);
        tmp += '-';
        tmp += str.substr(7);
        return tmp;
        }
    }
    return str;
}
$(function(){
    $(".telCheckSize").keyup(function (event) {
        event = event || window.event;
        var _val = this.value.trim();
        this.value = autoHypenTel(_val);
    });
})

//아이디 찾기
$(document).on('click','#findId',function(){
	console.log("아이디 찾기");
	console.log("이름 : "+$("#nameInput").val());
	console.log("번호 : "+$("#telInput").val());
	if($("#nameInput").val()==""||$("#telInput").val()==""){
		alert("이름과 연락처를 입력해주세요");
	}else{
		$.ajax({
			url : "findId.do",
			type : "post",
			data : {"member_name":$("#nameInput").val(),"member_phone":$("#telInput").val()},
	        success:function(data){
	        	if(data==""){
	        		$("#getCodeModal").modal("toggle");
	           	 	var html = "해당하는 회원 정보가 없습니다 다시 입력해주세요 <br>"+data;
	                $("#getCode").html(html);
	        	}else{
	        		$("#getCodeModal").modal("toggle");
	           	 	var html = "조회한 이메일은 다음과 같습니다 <br>"+data;
	                $("#getCode").html(html);
	        	}
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown){
	        	alert('이름과 전화번호가 같은 사람이 두명 이상입니다');
	        }
		});
	}
});

//비밀번호 찾기
$(document).on('click','#findPw',function(){

	console.log("비밀번호 찾기");
	console.log("이메일 : "+$("#emailInput2").val());
	console.log("이름 : "+$("#nameInput2").val());
	console.log("번호 : "+$("#telInput2").val());
	
	$.ajax({
		url : "findPw.do",
		type : "post",
		data : {"member_email":$("#emailInput2").val(),"member_name":$("#nameInput2").val(),"member_phone":$("#telInput2").val()},
        success:function(data){
        	console.log(data);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown){
        	alert('에이잭스 오류.' );
        }
	});
});

</script>
<title>Insert title here</title>
</head>
<body>
	<c:import url="/header.do"></c:import>
	<main>
    <div class="container" style="margin-top: 6%; margin-bottom: 6%;">
        <div class="row" style="width: 100%;">
            <div class="col-xs-12">
                <div><h5 style="margin-bottom: 30px; font-weight: 600; text-align: center;">이메일 / 비밀번호 찾기</h5></div>
            </div>
        </div>
        <div class="row" style="border: 1px solid lightgray; border-radius: 5px; padding: 20px;">
        <!-- 아이디 찾기  -->
            <div class="col-md-6">
                <div style="font-weight: 600; color: #22577E; padding-bottom: 5px; margin-bottom: 15px; border-bottom: 2px solid #22577E;">이메일 찾기</div>
                <table style="margin: 20px; border-collapse: separate; border-spacing: 0 10px;">
                    <tr>
                        <th>이름</th>
                        <td><input type="text" name="member_name" class="form-control" id="nameInput" placeholder="이름을 입력해주세요" ></td>
                    </tr>
                    <tr>
                        <th>연락처</th>
                        <td>
                            <div class="input-group mb-3" style="margin-top:20px">
                                    <input type="tel" class="form-control m-input telCheckSize" name="member_phone" id="telInput" required pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" maxlength="13"
                                    aria-label="Default" aria-describedby="inputGroup-sizing-default" value="${ member.member_phone }">
                            </div>
                        </td>
                    </tr>
                </table>
                <div style="text-align: center; margin-bottom: 15px;">
                    <button type="button" class="btn btn-primary" style="background-color: #5584AC; border: none;" id="findId">이메일 찾기</button>
                </div>
            </div>
            
        <!-- 비밀번호 찾기  -->
            <div class="col-md-6">
                <div style="font-weight: 600; color: #22577E; padding-bottom: 5px; margin-bottom: 15px; border-bottom: 2px solid #22577E;">비밀번호 찾기</div>
                <table style="margin: 20px; border-collapse: separate; border-spacing: 0 10px;">
                    <tr>
                        <th>이메일</th>
                        <td><input type="email" class="form-control" id="emailInput2" name="member_email" placeholder="이메일을 입력해주세요"></td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td><input type="text" class="form-control" id="nameInput2" name="member_name" placeholder="이름을 입력해주세요" ></td>
                    </tr>
                    <tr>
                        <th>연락처</th>
                        <td>
                            <div class="input-group mb-3">
                                    <input type="tel" class="form-control m-input telCheckSize" name="member_phone" id="telInput2" required pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" maxlength="13"
                                    aria-label="Default" aria-describedby="inputGroup-sizing-default" value="${ member.member_phone }">
                            </div>
                        </td>
                    </tr>
                </table>
                <div style="text-align: center; margin-bottom: 15px;">
                    <button type="button" class="btn btn-primary" style="background-color: #5584AC; border: none;" id="findPw">비밀번호 찾기</button>
                </div>
            </div>
        </div>
        <div class="row" style="margin-top: 50px;">
            <div class="col-xs-12" style="width: 100%;">
                <div style="font-weight: 600; margin-bottom: 10px;">이메일 / 비밀번호 조회결과</div>
                <div style="border: 1px solid lightgray; border-radius: 5px; padding: 20px; line-height: 3;">
                    &#183; 고객님께서 검색하신 이메일에 대한 결과값이 여기에 보여집니다.<br>
                    &#183; 검색하신 비밀번호는 회원가입 시 등록된 이메일 주소로 발송됩니다.<br>
                    &#183; 비밀번호 찾기는 이메일, 이름, 전화번호를 모두 입력하셔야 조회가 가능합니다.
                    	
					<a href="write.do">이메일 발송</a> 
                </div>
            </div>
        </div>
    </div>
    
    <!-- 모달 창  -->
    <!-- Modal -->
	 <div class="modal fade" id="getCodeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog modal-lg">
	      <div class="modal-content">
	       <div class="modal-header head">
	         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	         <h5 class="modal-title" id="myModalLabel"> 닫기 </h5>
	       </div>
	       <div class="modal-body content" id="getCode" style="overflow-x: scroll;">
	          ajax success content here.
	       </div>
	    </div>
	   </div>
	 </div>
    
    
    
    </main>
    <c:import url="/footer.do"></c:import>
</body>
</html>