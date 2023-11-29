<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="아이디/비밀번호 찾기" name="title"/>
</jsp:include>
<style>

  .wrap_5 {
    width: 500px;
  }
  
  .wrap {
    padding: 10px;
    margin: 0 auto;  
  }
  
  .title {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .btn {
    margin-top: 20px;
    margin-bottom: 20px;
  }
  
  .input-box{
    position:relative;
    margin:10px 0;
  }
  
</style>

  <div class="wrap wrap_5">
  
    <h2 class="title">아이디 찾기</h2>
    
    <form method="post" action="${contextPath}/user/findId.do">
    
      <div class="mb-3 row">
        <label for="name" class="col-sm-3 col-form-label">이름</label>
        <div class="col-sm-9"><input type="text" name="name" id="name" placeholder="이름" class="form-control"></div>
      </div>
        
      <div class="mb-3 row">
        <label for="phone" class="col-sm-3 col-form-label">전화번호</label>
        <div class="col-sm-9"><input type="text" name="phone" id="phone" placeholder="전화번호" class="form-control"></div>
      </div>
      <div class="d-grid gap-2 mb-3">
        <button class="btn btn-outline-success btn-lg" type="submit">아이디 찾기</button>
      </div>
    </form>
    
    <hr>
    
    <h2 class="title">비밀번호 찾기</h2>
    
    <div>
    
      <div class="mb-3 row">
        <label for="email" class="col-sm-3 col-form-label">이메일</label>
        <div class="col-sm-9"><input type="text" name="email" id="email" placeholder="이메일" class="form-control"></div>
      </div>
      
      <div class="mb-3 row">
        <label for="name" class="col-sm-3 col-form-label">이름</label>
        <div class="col-sm-9"><input type="text" name="name" id="name" placeholder="이름" class="form-control"></div>
      </div> 
      
      <div class="d-grid gap-2 mb-3">
        <button class="btn btn-outline-success btn-lg" type="button" id="btn_findPw">비밀번호 찾기</button>
      </div>
      
    </div>
    
  </div>
  
<script>
	
  $(() => {
    fnFindPw();
    fnRule();
  })


  const fnFindPw = () => {
	$('#btn_findPw').one('click', () => {
	  $.ajax({
		url:  '${contextPath}/user/findPw.do',
		type: 'post',
		data: { 
				email: $('#email').val(),
				name: $('#name').val()
			  },
		success: (resData) => {
		  alert(resData);
		  location.href = '${contextPath}/user/login.form';
		},
	  })
	});
  }
  
  const fnRule = () => {
	if('${sessionScope.user}' != '') {
	  location.href= '${contextPath}/main.do';
	}
  }

</script>

<%@ include file="../layout/footer.jsp" %>