package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import commons.EncryptionUtils;
import dto.MembersDTO;
import dto.UserManageDTO;

public class MembersDAO {
    public static MembersDAO instance;

	public synchronized static MembersDAO getInstance() {
		if(instance == null) {
			instance = new MembersDAO();
		}
		return instance;
	}
	private MembersDAO() {}
	
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	public int insert(MembersDTO dto) throws Exception{
		String sql;
		if(dto.getId().contains("admin")) {
			sql = "insert into members values(?,?,?,?,?,?,?,?,?,?,true)";
			
		}else {
			sql = "insert into members values(?,?,?,?,?,?,?,?,?,?,default)";
	        
		}
		
		try(Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);){
            pstat.setString(1, dto.getId());
            pstat.setString(2, dto.getPw());
            pstat.setString(3, dto.getName());
            pstat.setString(4, dto.getPhone());
            pstat.setString(5, dto.getEmail());
            pstat.setString(6, dto.getZipcode());
            pstat.setString(7, dto.getAddress1());
            pstat.setString(8, dto.getAddress2());
            pstat.setTimestamp(9, dto.getSignup_date());
            pstat.setString(10, "쥐돌이.png");
            int result = pstat.executeUpdate();
            return result;
		}
    }
	
	
   public boolean isIdExist (MembersDTO dto) throws Exception {
      String sql = "select * from members where id=?";
      try(Connection con = this.getConnection();
         PreparedStatement pstat = con.prepareStatement(sql);){
         
         pstat.setString(1, dto.getId());
         
         try(ResultSet rs = pstat.executeQuery();){
            return rs.next();
         }
      }
   }
   
   public boolean isEmailExist (MembersDTO dto) throws Exception {
	      String sql = "select * from members where email=?";
	      try(Connection con = this.getConnection();
	         PreparedStatement pstat = con.prepareStatement(sql);){
	         
	         pstat.setString(1, dto.getEmail());
	         
	         try(ResultSet rs = pstat.executeQuery();){
	            return rs.next();
	         }
	      }
   }
   
   public boolean login(String id,String pw)throws Exception{
         String sql = "select * from members where id=? and pw=?";
         try(Connection con = this.getConnection();
               PreparedStatement pstat = con.prepareStatement(sql);){
            pstat.setString(1, id);
            pstat.setString(2, pw);
            try(ResultSet rs = pstat.executeQuery();){
               return  rs.next();
            }   
         }
      }
   
   
   public int isadmin(String id) throws Exception {
	    String sql = "SELECT isAdmin FROM members WHERE id=?";
	    try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql);) {
	        pstat.setString(1, id);
	        try (ResultSet rs = pstat.executeQuery();) {
	            int result = 0; 
	            if (rs.next()) { // 결과가 있을 때
	                result = rs.getInt("isAdmin"); // "isAdmin" 컬럼의 값을 가져옴
	            }

	            return result;
	        }
	    }
	}
   public MembersDTO mypage(String id) throws Exception {
      String sql = "select * from members where id=?";


		try (Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, id);
			try (ResultSet rs = pstat.executeQuery()) {
				while(rs.next()) {
					String ids = rs.getString("id");
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String email = rs.getString("email");
					String zipcode = rs.getString("zipcode");
					String address1 = rs.getString("address1");
					String address2 = rs.getString("address2");
					Timestamp signup_date = rs.getTimestamp("signup_date");
					String profile = rs.getString("profile");
					boolean isadmin = rs.getBoolean("isAdmin");
					return new MembersDTO(ids,pw,name,phone,email,zipcode,address1,address2,signup_date,profile,isadmin);
				}
				
			}return new MembersDTO("0","0","0","0","0","0","0","0",null,"0");
		}
	}

	public int update(String id, String name, String phone, String email, String zipcode, String address1, String address2) throws Exception{
		String sql = "update members set name=? , phone=? , email=? , zipcode=? ,address1=?, address2=? where id=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, name);
			pstat.setString(2, phone);
			pstat.setString(3, email);
			pstat.setString(4, zipcode);
			pstat.setString(5, address1);
			pstat.setString(6, address2);
			pstat.setString(7, id);
			
			return pstat.executeUpdate();
		}
	}
	
	public String findId(String email,String name) throws Exception {
		String sql = "select id from members where email=? and name=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1,email);
			pstat.setString(2,name);
			try (ResultSet rs = pstat.executeQuery()){
				while(rs.next()) {
					String memberId = rs.getString(1);
					return memberId.substring(0,memberId.length()-2)+"**";
				}
				
			}
		}
		return null;
	}
	
	public boolean tempPwCondition(String id, String email, String name) throws Exception {
		String sql = "select * from members where id = ? and email = ? and name=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1,id);
			pstat.setString(2, email);
			pstat.setString(3, name);
			try (ResultSet rs = pstat.executeQuery()){
				return rs.next();
			}	
		}
	} 
	
	public int updateTempPw(String pw, String id, String email, String name) throws SQLException, Exception {
		String sql = "update members set pw = ? where id = ? and email = ? and name=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, EncryptionUtils.getSHA512(pw));
			pstat.setString(2, id);
			pstat.setString(3, email);
			pstat.setString(4, name);
			return pstat.executeUpdate();
		}
	}
	
	public int delete(String id) throws SQLException, Exception {
		String sql = "delete from members where id=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, id);
			return pstat.executeUpdate();
		}
	}
	
	public int changePW(String pw1, String pw2) throws SQLException, Exception {
		String sql = "update members set pw = ? where pw = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, pw2);
			pstat.setString(2, pw1);
			return pstat.executeUpdate();
		}
	}
	
	public int uploadProfile(String profile, String id) throws Exception {
		String sql = "update members set profile = ? where id = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, profile);
			pstat.setString(2, id);
			return pstat.executeUpdate();
		}
	}
	
	public int insertUserManagement(String id) throws SQLException, Exception {
		String sql = "insert into user_management values (?,default)";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, id);
			return pstat.executeUpdate();
		}
	}
	
	public int updateUserIsBanned(UserManageDTO dto) throws Exception {
		String sql = "update user_management set isbanned = ? where id = ?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setBoolean(1, !dto.isBanned());
			pstat.setString(2, dto.getId());
			return pstat.executeUpdate();
		}
	}
	
	public List<String> notBannedList() throws Exception{
		String sql = "SELECT um.id "
				+ "FROM user_management um "
				+ "JOIN members m ON um.id = m.id "
				+ "WHERE um.isbanned = false AND m.isAdmin = false";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			try(ResultSet rs = pstat.executeQuery() ){
				List<String> list = new ArrayList<>();
				while(rs.next()) {
					list.add(rs.getString(1));
				}
				return list;
			}
		}
	}
	
	public List<String> isBannedList() throws Exception{
		String sql = "SELECT um.id "
				+ "FROM user_management um "
				+ "JOIN members m ON um.id = m.id "
				+ "WHERE um.isbanned = true AND m.isAdmin = false";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)){
			try(ResultSet rs = pstat.executeQuery() ){
				List<String> list = new ArrayList<>();
				while(rs.next()) {
					list.add(rs.getString(1));
				}
				return list;
			}
		}
	}
	

}