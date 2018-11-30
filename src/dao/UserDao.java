package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import entity.User;
import utils.Dbconn;

public class UserDao {

	private Dbconn db = null;
	
	public UserDao(){
		db = new Dbconn();
	}
	
	
	/** 
	* @Title: doLogin 
	* @Description: log in with user's username and pwd 
	* @param @param user User Object
	* @param @return
	* @param @throws SQLException    
	* @return User    
	* @throws 
	*/
	public User doLoginByUsername(User user) throws SQLException{
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call log_in_by_username(?,?)}");
			c.setString(1, user.getUsername());
			c.setString(2, user.getPassword());
			//c.registerOutParameter(3, Types.INTEGER);
			ResultSet rs = c.executeQuery();
			if(rs.next()){
				user.setUserId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAccountType(rs.getString("accountType"));
				user.setFacebook(rs.getString("facebook"));
				user.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				user.setUpdateAt(rs.getTimestamp("updatedAt").getTime());
				user.setAvatar(rs.getString("avatar"));
				return user;
			}
			return null;
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
		return null;
		
	}

	
	/**
	 * @Title: doLoginByEmail
	 * @Description: user login by email and pwd
	 * @param @param user
	 * @param @return
	 * @param @throws SQLException
	 * @return User
	 * @throws
	 */
	public User doLoginByEmail(User user) throws SQLException {

		Connection conn = db.getConnection();
		CallableStatement c = null;

		try {
			c = conn.prepareCall("{call log_in_by_email(?,?)}");
			c.setString(1, user.getEmail());
			c.setString(2, user.getPassword());
			// c.registerOutParameter(3, Types.INTEGER);
			ResultSet rs = c.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAccountType(rs.getString("accountType"));
				user.setFacebook(rs.getString("facebook"));
				user.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				user.setUpdateAt(rs.getTimestamp("updatedAt").getTime());
				user.setAvatar(rs.getString("avatar"));
				return user;
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.dispose();
		}

		return null;

	}

	
	/** 
	* @Title: doLoginByFacebook 
	* @Description: auto login with facebook/google account 
	* @param @param user
	* @param @return
	* @param @throws SQLException    
	* @return User    
	* @throws 
	*/
	public User doLoginBy3rdParty(User user) throws SQLException {

		Connection conn = db.getConnection();
		CallableStatement c = null;

		try {
			c = conn.prepareCall("{call log_in_by_facebook(?)}");
			c.setString(1, user.getFacebook());

			// c.registerOutParameter(3, Types.INTEGER);
			ResultSet rs = c.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAccountType(rs.getString("accountType"));
				user.setFacebook(rs.getString("facebook"));
				user.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				user.setUpdateAt(rs.getTimestamp("updatedAt").getTime());
				user.setAvatar(rs.getString("avatar"));
				return user;
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.dispose();
		}

		return null;

	}

	/** 
	* @Title: doRegister 
	* @Description: TODO 
	* @param @param user
	* @param @return
	* @param @throws SQLException    
	* @return User    
	* @throws 
	*/
	public User doRegister(User user) throws SQLException {

		Connection conn = db.getConnection();
		CallableStatement c = null;

		try {
			c = conn.prepareCall("{call register_new_account(?,?,?,?,?)}");
			c.setString(1, user.getUsername());
			c.setString(2, user.getEmail());
			c.setString(3, user.getPassword());
			c.setString(4, user.getAccountType());
			c.registerOutParameter(5, Types.INTEGER);
			
			c.execute();
			
			user.setUserId(c.getInt(5));
			
			return user;
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.dispose();
		}

		return null;

	}
	
	
	public boolean updateInfo(User user) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;

		try {
			c = conn.prepareCall("{call update_user_info(?,?,?,?)}");
			c.setLong(1, user.getUserId());
			c.setString(2, user.getEmail());
			c.setString(3, user.getFacebook());
			
			c.registerOutParameter(4, Types.INTEGER);
			
			c.execute();
			
			if(c.getInt(5) == 0){
				return false;
			}
			
			return true;
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.dispose();
		}

		return false;

	}
	
	public List<User> getUsers(String username) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		List<User> users = new ArrayList<User>();

		try {
			c = conn.prepareCall("{call get_users(?)}");
			c.setString(1, username);

			// c.registerOutParameter(3, Types.INTEGER);
			ResultSet rs = c.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAccountType(rs.getString("accountType"));
				user.setFacebook(rs.getString("facebook"));
				user.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				user.setUpdateAt(rs.getTimestamp("updatedAt").getTime());
				user.setAvatar(rs.getString("avatar"));
				users.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			db.dispose();
		}

		return users;
	}
	
	public static void main(String[] args) throws SQLException {
		UserDao ud = new UserDao();
		User user = new User();
		user.setUsername("q45hu");
		user.setEmail("q45hu@uwaterloo.ca");
		user.setPassword("12345678");
		user.setAccountType("normal");
		
		User ultimate = ud.doRegister(user);
		
		System.out.print(ultimate.getUserId());
		
		
		
		
	}
}
