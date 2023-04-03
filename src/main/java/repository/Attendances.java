package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Attendance;
import data.Moim;

public class Attendances {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	public static List<Attendance> findByMoimId(String moimId) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM ATTENDANCES WHERE MOIM_ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moimId);
			
			ResultSet rs = ps.executeQuery();
			List<Attendance> li = new ArrayList<>();
			while (rs.next()) {
				Attendance at = new Attendance();
				at.setId(rs.getInt("ID"));
				at.setUserId(rs.getString("USER_ID"));
				at.setMoimId(rs.getString("MOIM_ID"));
				at.setStatus(rs.getInt("STATUS"));
				li.add(at);
			}
			conn.close();

			return li;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int create(String moimId, String userId, int status) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password); 
			
			// 2. 요청객체 준비
			String sql = "INSERT INTO ATTENDANCES VALUES(ATTENDANCES_SEQ.NEXTVAL, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, moimId);
			ps.setInt(3, status);
			int r = ps.executeUpdate();			
			
			// 종료
			conn.close();
			return r;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
