package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.User;

public class Users {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
		
	public static int create(String id, String pass, String name, String avatar) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password); 
			
			// 2. 요청객체 준비
			String sql = "INSERT INTO USERS(ID, PASS, NAME, AVATAR_ID) values(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pass);
			ps.setString(3, name);
			ps.setString(4, avatar);
			int r = ps.executeUpdate();			
			
			// 종료
			conn.close();
			return r;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static User findById(String id) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			String sql = "SELECT USERS.*, AVATARS.URL AS AVATAR_URL FROM USERS JOIN AVATARS ON USERS.AVATAR_ID = AVATARS.ID WHERE USERS.ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			User one = null;
			if(rs.next()) {
				one = new User();
				one.setId(id);
				one.setPass(rs.getString("pass"));
				one.setName(rs.getString("name"));
				one.setAvatar(rs.getString("avatar_id"));
				one.setAvatarURL(rs.getString("avatar_url"));
			}
			conn.close();
			
			return one;
		}catch(Exception e) {
			return null;
		}
	}
}
