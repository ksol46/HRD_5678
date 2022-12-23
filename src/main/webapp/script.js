function fn_submit(){
	var f = document.frm;
	
	var m_jumin = f.m_jumin.value;
	if(m_jumin ==""){
		alert("주민번호가 입력되지 않았습니다!");
		f.m_jumin.focus();
		return false;
	}

	var v_name = f.v_name.value;
	if(v_name ==""){
		alert("성명이 입력되지 않았습니다!");
		f.v_name.focus();
		return false;
	}

	if(f.m_no.value ==""){
		alert("후보번호가 선택되지 않았습니다!");
		f.m_no.focus();
		return false;
	}
	
	

	if(f.v_time.value ==""){
		alert("투표시간이 입력되지 않았습니다!");
		f.v_time.focus();
		return false;
	}
	
	if(f.v_area.value ==""){
		alert("투표장소가 입력되지 않았습니다!");
		f.v_area.focus();
		return false;
	}
	
	if(f.v_confirm.value ==""){
		alert("유권자 확인이 선택되지 않았습니다!");
		return false;
	}
	fn.submit();
	
}

function fn_reset() {
	alert("정보를 지우고 처음부터 다시 입력합니다!");
	location = "vote.jsp";
}
