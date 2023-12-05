
  $(() => {
    fnFindPw();
    fnRule();
  })


  const fnFindPw = () => {
	$('#btn_findPw').one('click', () => {
	  $.ajax({
		url:  '/user/findPw.do',
		type: 'post',
		data: { 
				email: $('#email').val(),
				name: $('#name').val()
			  },
		success: (resData) => {
		  alert(resData);
		  location.href = '/user/login.form';
		},
	  })
	})
  }
  
  
   const fnRule = () => {
	if('${sessionScope.user}' != '') {
	  location.href= '/main.do';
	}
  }
