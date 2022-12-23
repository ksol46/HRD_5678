package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.Member;
import DTO.Rank;
import DTO.Vote;

public class DAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	//데이터 베이스 연결 메소드
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection
				("jdbc:oracle:thin:@//localhost:1521/xe","system","sys1234");
		return con;
	}
	
	//후보조회
	public String memberView (HttpServletRequest request,HttpServletResponse response) {
		ArrayList<Member> m_list = new ArrayList<Member>();
		try {
			conn = getConnection(); //DB 연결함.
			String sql = " select m.m_no, m.m_name, p.p_name,";
			sql += " decode(m.p_school, '1', '고졸', '2', '학사', '3', '석사', '4', '박사') p_school,";
			sql += " (substr(m.m_jumin,1,6)||'-'||substr(m.m_jumin,7)) m_jumin, m.m_city,";
			sql += " (p.p_tel1 || '-' || p.p_tel2 || '-' || (substr(p.p_tel3,4) || substr(p.p_tel3,4) || substr(p.p_tel3,4) ||substr(p.p_tel3,4))) p_tel";
			sql += " from tbl_member_202005 m, tbl_party_202005 p where m.p_code = p.p_code";
					
		ps = conn.prepareStatement(sql); //쿼리문 실행 준비
		rs = ps.executeQuery(); //쿼리문 실행
		
		while (rs.next()) {
			Member member = new Member();
			member.setM_no(rs.getString(1));
			member.setM_name(rs.getString(2));
			member.setP_name(rs.getString(3));
			member.setP_school(rs.getString(4));
			member.setM_jumin(rs.getString(5));
			member.setM_city(rs.getString(6));
			member.setP_tel(rs.getString(7));
			
			m_list.add(member);
			
		}
			
		request.setAttribute("m_list", m_list);
		
		conn.close();
		ps.close();
		rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member.jsp";
	}
	
	//투표하기
	public int do_vote (HttpServletRequest request,HttpServletResponse response) {
		String m_jumin = request.getParameter("m_jumin");
		String v_name = request.getParameter("v_name");
		String m_no = request.getParameter("m_no");
		String v_time = request.getParameter("v_time");
		String v_area = request.getParameter("v_area");
		String v_confirm = request.getParameter("v_confirm");
		int result = 0;
		
		try {
		conn = getConnection();
		String sql = " insert into tbl_vote_202005 values(?,?,?,?,?,?)";
		ps= conn.prepareStatement(sql);
		ps.setString(1, m_jumin);
		ps.setString(2, v_name);
		ps.setString(3, m_no);
		ps.setString(4, v_time);
		ps.setString(5, v_area);
		ps.setString(6, v_confirm);
		
		result = ps.executeUpdate();
		
		System.out.println(result);
		
		conn.close();
		ps.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//투표검수조회 화면
	public String do_inquiry (HttpServletRequest request,HttpServletResponse response) {
		ArrayList<Vote> v_list = new ArrayList<Vote>();
		
		try {
			
		conn = getConnection(); //DB연결
			
		String sql = " select  v.v_name V_name, '19'||substr(v_jumin,1,2)||'년'||substr(v_jumin,3,2)||'월'||substr(v_jumin,5,2)||'일생' Birth,";
			   sql += " '만'||(to_number(to_char(sysdate,'yyyy')) - to_number('19'||substr(v_jumin,1,2)))||'세' Age,";
			   sql += " decode((substr(v_jumin,7,1)),'1','남','2','여') Sex, v.m_no M_no, substr(v_time,1,2)||':'||substr(v_time,3,2) V_time,";
			   sql += " decode(v.v_confirm, 'Y', '확인', 'N', '미확인') V_confirm";
			   sql += " from tbl_vote_202005 v";
			   sql += " where v_area = '제1투표장'";
					
		ps = conn.prepareStatement(sql); //쿼리문 실행 준비
		rs = ps.executeQuery(); //쿼리문 실행
		
	while(rs.next()) {
		Vote vote = new Vote();
		
		vote.setV_name(rs.getString(1));
		vote.setBirth(rs.getString(2));
		vote.setAge(rs.getString(3));
		vote.setSex(rs.getString(4));
		vote.setM_no(rs.getString(5));
		vote.setV_time(rs.getString(6));
		vote.setV_confirm(rs.getString(7));
		
		v_list.add(vote);
		
	}
	
	request.setAttribute("v_list", v_list);
	conn.close();
	ps.close();
	rs.close();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "inquiry.jsp";
	}
	
	//후보자 등수
	public String do_rank (HttpServletRequest request,HttpServletResponse response) {
		
		ArrayList<Rank> r_list = new ArrayList<Rank>();
				
		try {
		conn = getConnection(); //DB연결
		
		String sql = "select m.m_no, m.m_name, count(*) as Total ";
			   sql+= "from tbl_member_202005 m, tbl_vote_202005 v ";
			   sql+= "where m.m_no = v.m_no and v.v_confirm = 'Y' ";
			   sql+= "group by m.m_no, m.m_name ";
			   sql+= "order by total desc";
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			Rank rank = new Rank ();
			
			rank.setM_no(rs.getString(1));
			rank.setM_name(rs.getString(2));
			rank.setTotal(rs.getString(3));
			
			r_list.add(rank);
		}
		
		request.setAttribute("r_list", r_list);
		conn.close();
		ps.close();
		rs.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rank.jsp";
	}
	
	
	
	
	
	
}
