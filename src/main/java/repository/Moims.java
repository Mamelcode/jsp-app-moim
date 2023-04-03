package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import data.Avatar;
import data.Moim;
import data.User;

public class Moims {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	public static int create(String id, String managerid, String event, String type,
			String cate, String description, int maxPerson, String beginDate, String endDate) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password); 
			
			// 2. 요청객체 준비
			String sql = "INSERT INTO MOIMS VALUES(?, ?, ?, ?, ?, ?, ?, 1, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), "
					+ "TO_DATE(?, 'YYYY-MM-DD HH24:MI'), null)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, managerid);
			ps.setString(3, event);
			ps.setString(4, type);
			ps.setString(5, cate);
			ps.setString(6, description);
			ps.setInt(7, maxPerson);
			ps.setString(8, beginDate);
			ps.setString(9, endDate);
			int r = ps.executeUpdate();			
			
			// 종료
			conn.close();
			return r;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static List<Moim> findLatest() {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = " SELECT * FROM "
					+ " (SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE ORDER BY BEGIN_DATE-SYSDATE) "
					+ " WHERE ROWNUM <=3";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Moim> moimList = new ArrayList<>();
			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("manager_id"));
				moim.setEvent(rs.getString("event"));
				moim.setCate(rs.getString("cate"));
				moim.setType(rs.getString("type"));
				moim.setDescription(rs.getString("description"));
				moim.setMaxPerson(rs.getInt("Max_Person"));
				moim.setCurrentPerson(rs.getInt("current_Person"));
				moim.setBeginDate(rs.getDate("Begin_Date"));
				moim.setEndDate(rs.getDate("End_Date"));
				moim.setFinalCost(rs.getInt("final_cost"));

				moimList.add(moim);
			}
			conn.close();

			return moimList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Moim> findBycate(String cate) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM MOIMS WHERE cate='?' AND BEGIN_DATE > SYSDATE";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Moim> moimList = new ArrayList<>();
			
			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("manager_id"));
				moim.setEvent(rs.getString("event"));
				moim.setCate(rs.getString("cate"));
				moim.setType(rs.getString("type"));
				moim.setDescription(rs.getString("description"));
				moim.setMaxPerson(rs.getInt("Max_Person"));
				moim.setCurrentPerson(rs.getInt("current_Person"));
				moim.setBeginDate(rs.getDate("Begin_Date"));
				moim.setEndDate(rs.getDate("End_Date"));
				moim.setFinalCost(rs.getInt("final_cost"));

				moimList.add(moim);
			}
			conn.close();

			return moimList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Moim> findByAllcate() {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Moim> moimList = new ArrayList<>();
			
			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("manager_id"));
				moim.setEvent(rs.getString("event"));
				moim.setCate(rs.getString("cate"));
				moim.setType(rs.getString("type"));
				moim.setDescription(rs.getString("description"));
				moim.setMaxPerson(rs.getInt("Max_Person"));
				moim.setCurrentPerson(rs.getInt("current_Person"));
				moim.setBeginDate(rs.getDate("Begin_Date"));
				moim.setEndDate(rs.getDate("End_Date"));
				moim.setFinalCost(rs.getInt("final_cost"));

				moimList.add(moim);
			}
			conn.close();

			return moimList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Moim> findBycateBoth(String[] cate) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			String sql = "SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE AND cate=?";
			for(int i=1; i < cate.length; i++) {
				sql += " OR cate=?";
			}
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			for(int i=0; i < cate.length; i++) {
				ps.setString(i+1, cate[i]);
			}
			
			ResultSet rs = ps.executeQuery();
			List<Moim> li = new LinkedList<>();
			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("id"));
				moim.setManagerId(rs.getString("manager_id"));
				moim.setEvent(rs.getString("event"));
				moim.setCate(rs.getString("cate"));
				moim.setType(rs.getString("type"));
				moim.setDescription(rs.getString("DESCRIPTION"));
				moim.setMaxPerson(rs.getInt("MAX_PERSON"));
				moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
				moim.setBeginDate(rs.getDate("BEGIN_DATE"));
				moim.setEndDate(rs.getDate("END_DATE"));
				moim.setFinalCost(rs.getInt("FINAL_COST"));

				li.add(moim);
			}
			conn.close();

			return li;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Moim findById(String id) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT MOIMS.*, USERS.NAME AS MANAGER_NAME , AVATARS.URL AS MANAGER_URL "
					+ "FROM MOIMS "
					+ "JOIN USERS ON MOIMS.MANAGER_ID = USERS.ID "
					+ "JOIN AVATARS ON AVATARS.ID = USERS.AVATAR_ID "
					+ "WHERE MOIMS.ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			Moim moim = null;
			if (rs.next()) {
				moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("MANAGER_ID"));
				moim.setEvent(rs.getString("EVENT"));
				moim.setCate(rs.getString("CATE"));
				moim.setType(rs.getString("TYPE"));
				moim.setDescription(rs.getString("DESCRIPTION"));
				moim.setMaxPerson(rs.getInt("MAX_PERSON"));
				moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
				moim.setBeginDate(rs.getDate("BEGIN_DATE"));
				moim.setEndDate(rs.getDate("END_DATE"));
				moim.setFinalCost(rs.getInt("FINAL_COST"));
				
				moim.setManagerName(rs.getString("MANAGER_NAME"));
				moim.setManagerAvatarURL(rs.getString("MANAGER_URL"));
			}
			conn.close();
			return moim;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int updatePerson(String id) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			String req1 = "UPDATE MOIMS SET CURRENT_PERSON = CURRENT_PERSON + 1 WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(req1);
			
			pstmt.setString(1, id);
			
			int result = pstmt.executeUpdate();
			
			conn.close();
			
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
