<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="회원가입" name="title"/>
</jsp:include>
<style>
  .title {
    text-align: center;
    margin-bottom: 20px;
  }
  .btn_join {
    margin-top: 10px;
    margin-bottom: 20px;
  }
</style>

<div class="wrap wrap_7">

  <h1 class="title">회원가입</h1>

  <form id="frm_join" method="post" action="${contextPath}/user/join.do">
    
    <div class="row mb-2">
      <label for="email" class="col-sm-3 col-form-label">이메일</label>
      <div class="col-sm-6"><input type="text" name="email" id="email" placeholder="이메일" class="form-control"></div>
      <div class="col-sm-3 d-grid gap-2"><button type="button" id="btn_get_code" class="btn btn-outline-success">인증코드받기</button></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9" id="msg_email"></div>
    </div>
    
    <div class="row mb-2">
      <div class="col-sm-9"><input type="text" id="code" class="form-control" placeholder="인증코드입력" disabled></div>
      <div class="col-sm-3 d-grid gap-2"><button type="button" class="btn btn-outline-secondary" id="btn_verify_code" disabled>인증하기</button></div>
    </div>
    
    <hr class="my-3">
    
    <div class="row mb-2">
      <label for="pw" class="col-sm-3 col-form-label">비밀번호</label>
      <div class="col-sm-9"><input type="password" name="pw" id="pw" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9 mb-3" id="msg_pw"></div>
    </div>
    
    <div class="row mb-2">
      <label for="pw2" class="col-sm-3 col-form-label">비밀번호 확인</label>
      <div class="col-sm-9"><input type="password" id="pw2" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9 mb-3" id="msg_pw2"></div>
    </div>
    
    <hr class="my-3">
    
    <div class="row mb-2">
      <label for="name" class="col-sm-3 col-form-label">이름</label>
      <div class="col-sm-9"><input type="text" name="name" id="name" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9 mb-3" id="msg_name"></div>
    </div>

    <div class="row mb-2">
      <label for="phone" class="col-sm-3 col-form-label">휴대전화번호</label>
      <div class="col-sm-9"><input type="text" name="phone" id="phone" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9 mb-3" id="msg_phone"></div>
    </div>

    <div class="row mb-2">
      <label class="col-sm-3 form-label">성별</label>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="NO" id="none" class="form-check-input" checked>
        <label class="form-check-label" for="none">선택안함</label>
      </div>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="M" id="man" class="form-check-input">
        <label class="form-check-label" for="man">남자</label>
      </div>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="F" id="woman" class="form-check-input">
        <label class="form-check-label" for="woman">여자</label>
      </div>
    </div>
    
    <hr class="my-3">
    
    <div>
      <label for="address" class="col-sm-3 col-form-label">주소</label>
      <select class="col-sm-2" name="sido" id="sido"></select>
      <select class="col-sm-2" name="sigungu" id="sigungu"></select>
    </div>
    <div>
      <label for="address" class="col-sm-3 col-form-label">관심지역 (선택)</label>
      <select class="col-sm-2" name="interestSido" id="interestSido"></select>
      <select class="col-sm-2" name="interestSigungu" id="interestSigungu"></select>
    </div>
    
    <script>
    </script>
    
    <div class="d-grid gap-2 col-6 mx-auto text-center">
      <input type="hidden" name="event" value="${event}">
      <button type="submit" class="btn btn_join btn-primary">회원가입하기</button>
    </div>
    
  </form>

</div>
<script>

$(() => {
	  fnCheckEmail();
	  fnCheckPassword();
	  fnCheckPassword2();
	  fnCheckName();
	  fnCheckphone();
	  fnJoin();
	  fnAddress();
	  fnResetAddress();
	  fnResetInterestAddress();
	  fnInterestAddress();
	})


	/* 전역변수 선언 */
	var emailPassed = false;
	var pwPassed = false;
	var pw2Passed = false;
	var namePassed = false;
	var phonePassed = false;
	var area0 = ["시/도 선택","서울특별시","인천광역시","대전광역시","광주광역시","대구광역시","울산광역시","부산광역시","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도"];
	var area1 = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
    var area2 = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
    var area3 = ["대덕구","동구","서구","유성구","중구"];
    var area4 = ["광산구","남구","동구",     "북구","서구"];
    var area5 = ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
    var area6 = ["남구","동구","북구","중구","울주군"];
    var area7 = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
    var area8 = ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"];
    var area9 = ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"];
    var area10 = ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"];
    var area11 = ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"];
    var area12 = ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"];
    var area13 = ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"];
    var area14 = ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"];
    var area15 = ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"];
    var area16 = ["서귀포시","제주시","남제주군","북제주군"];


	/* 함수 정의 */

	const getContextPath = () => {
	  let begin = location.href.indexOf(location.host) + location.host.length;
	  let end = location.href.indexOf('/', begin + 1);
	  return location.href.substring(begin, end);
	}

	const fnCheckEmail = () => {
	  $('#btn_get_code').one('click', () => {
	    let email = $('#email').val();
	    // 연속된 ajax() 함수 호출의 실행 순서를 보장하는 JavaScript 객체 Promise
	    new Promise((resolve, reject) => {
	      // 성공했다면 resolve() 함수 호출 -> then() 메소드에 정의된 화살표 함수 호출
	      // 실패했다면 reject() 함수 호출 -> catch() 메소드에 정의된 화살표 함수 호출
	      // 1. 정규식 검사
	      let regEmail = /^[A-Za-z0-9-_]+@[A-Za-z0-9]{2,}([.][A-Za-z]{2,6}){1,2}$/;
	      if(!regEmail.test(email)){
	        reject(1);
	        return;
	      }
	      // 2. 이메일 중복 체크
	      $.ajax({
	        // 요청
	        type: 'get',
	        url: getContextPath() + '/user/checkEmail.do',
	        data: 'email=' + email,
	        // 응답
	        dataType: 'json',
	        success: (resData) => {  // resData === {"enableEmail": true}
	          if(resData.enableEmail){
	            $('#msg_email').text('');
	            resolve();
	          } else {
	            reject(2);
	          }
	        }
	      })
	    }).then(() => {
	      // 3. 인증코드 전송
	      $.ajax({
	        // 요청
	        type: 'get',
	        url: getContextPath() + '/user/sendCode.do',
	        data: 'email=' + email,
	        // 응답
	        dataType: 'json',
	        success: (resData) => {  // resData === {"code": "6자리코드"}
	          alert(email + "로 인증코드를 전송했습니다.");
	          $('#code').prop('disabled', false);
	          $('#btn_verify_code').prop('disabled', false);
	          $('#btn_verify_code').click(() => {
	            emailPassed = $('#code').val() === resData.code;
	            if(emailPassed){
	              alert('이메일이 인증되었습니다.');
	            } else {
	              alert('이메일 인증이 실패했습니다.');
	            }
	          })
	        }
	      })
	    }).catch((state) => {
	      emailPassed = false;
	      switch(state){
	      case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.'); break;
	      case 2: $('#msg_email').text('이미 가입한 이메일입니다. 다른 이메일을 입력해 주세요.'); break;
	      }
	    })
	  })
	}

	const fnCheckPassword = () => {
	  $('#pw').keyup((ev) => {
	    let pw = $(ev.target).val();
	    // 비밀번호 : 8~20자, 영문,숫자,특수문자, 2가지 이상 포함
	    let validPwCount = /[A-Z]/.test(pw)          // 대문자가 있으면   true
	                     + /[a-z]/.test(pw)          // 소문자가 있으면   true
	                     + /[0-9]/.test(pw)          // 숫자가 있으면     true
	                     + /[^A-Za-z0-9]/.test(pw);  // 특수문자가 있으면 true
	    pwPassed = pw.length >= 8 && pw.length <= 20 && validPwCount >= 2;
	    if(pwPassed){
	      $('#msg_pw').text('사용 가능한 비밀번호입니다.');
	    } else {
	      $('#msg_pw').text('비밀번호는 8~20자, 영문/숫자/특수문자를 2가지 이상 포함해야 합니다.');       
	    }
	  })
	}

	const fnCheckPassword2 = () => {
	  $('#pw2').blur((ev) => {
	    let pw = $('#pw').val();
	    let pw2 = ev.target.value;
	    pw2Passed = (pw !== '') && (pw === pw2);
	    if(pw2Passed){
	      $('#msg_pw2').text('');
	    } else {
	      $('#msg_pw2').text('비밀번호 입력을 확인하세요.');
	    }
	  })
	}

	const fnCheckName = () => {
	  $('#name').blur((ev) => {
	    let name = ev.target.value;
	    let bytes = 0;
	    for(let i = 0; i < name.length; i++){
	      if(name.charCodeAt(i) > 128){  // 코드값이 128을 초과하는 문자는 한 글자 당 2바이트임
	        bytes += 2;
	      } else {
	        bytes++;
	      }
	    }
	    namePassed = (bytes <= 50);
	    if(!namePassed){
	      $('#msg_name').text('이름은 50바이트 이내로 작성해야 합니다.');
	    }
	  })
	}

	const fnCheckphone = () => {
	  $('#phone').keyup((ev) => {
	    ev.target.value = ev.target.value.replaceAll('-', '');
	    // 휴대전화번호 검사 정규식 (010숫자8개)
	    let regphone = /^010[0-9]{8}$/;
	    phonePassed = regphone.test(ev.target.value);
	    if(phonePassed){
	      $('#msg_phone').text('');
	    } else {
	      $('#msg_phone').text('휴대전화번호를 확인하세요.');       
	    }
	  })
	}
	
	 // 시/도 선택 박스 초기화
	function fnResetAddress(){
  	  $("#sido").each(function() {
  	    $selsido = $(this);
  	    $.each(eval(area0), function() {
  	      $selsido.append("<option value='"+this+"'>"+this+"</option>");
  	    });
  	    $selsido.next().append("<option value=''>시/군/구 선택</option>");
  	  });
	}

	
	// 시/도 선택시 구/군 설정
	function fnAddress(){
	  $("#sido").change(function() {
		var area = "area"+$("option",$(this)).index($("option:selected",$(this))); // 선택지역의 시군구 Array
		var $sigungu = $(this).next(); // 선택영역 시군구 객체
		$("option",$sigungu).remove(); // 시군구 초기화
		if(area == "area0")
		  $sigungu.append("<option value=''>시/군/구 선택</option>");
		else {
		  $.each(eval(area), function() {
		  $sigungu.append("<option value='"+this+"'>"+this+"</option>");
		  });
		}
	  });
	}
	
	// 관심지역 시/도 선택 박스 초기화
	function fnResetInterestAddress(){
  	  $("#interestSido").each(function() {
  	    $selinterestsido = $(this);
  	    $.each(eval(area0), function() {
  	      $selinterestsido.append("<option value='"+this+"'>"+this+"</option>");
  	    });
  	    $selinterestsido.next().append("<option value=''>시/군/구 선택</option>");
  	  });
	}

	
	// 관심지역 시/도 선택시 구/군 설정
	function fnInterestAddress(){
	  $("#interestSido").change(function() {
		var area = "area"+$("option",$(this)).index($("option:selected",$(this))); // 선택지역의 시군구 Array
		var $interestsigungu = $(this).next(); // 선택영역 시군구 객체
		$("option",$interestsigungu).remove(); // 시군구 초기화
		if(area == "area0")
		  $interestsigungu.append("<option value=''>시/군/구 선택</option>");
		else {
		  $.each(eval(area), function() {
		  $interestsigungu.append("<option value='"+this+"'>"+this+"</option>");
		  });
		}
	  });
	}

	const fnJoin = () => {
	  $('#frm_join').submit((ev) => {
	    if(!emailPassed){
	      alert('이메일을 인증 받아야 합니다.');
	      ev.preventDefault();
	      return;
	    } else if(!pwPassed || !pw2Passed){
	      alert('비밀번호를 확인하세요.');
	      ev.preventDefault();
	      return;
	    } else if(!namePassed){
	      alert('이름을 확인하세요.');
	      ev.preventDefault();
	      return;
	    } else if(!phonePassed){
	      alert('휴대전화번호를 확인하세요.');
	      ev.preventDefault();
	      return;
	    } else if($("#sido").val() == '' || $("#sigungu").val() == ''){
      	  alert('주소를 선택해주세요.')
          ev.preventDefault();
	      return;
	    }
	  })
	}
	

</script>

<%@ include file="../layout/footer.jsp" %>
