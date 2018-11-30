package dao;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.*;

import entity.BaseMsg;
import entity.Group;
import entity.Project;
import entity.User;
import enums.MsgType;

import utils.Dbconn;

public class GroupDao {

	private Dbconn db = null;
	
	public GroupDao(){
		db = new Dbconn();
	}
	
	/** 
	* @Title: getGroupsOfUser 
	* @Description: TODO 
	* @param @param user_id
	* @param @return
	* @param @throws SQLException    
	* @return List<Group>    
	* @throws 
	*/
	public List<Group> getGroupsOfUser(int user_id) throws SQLException{
		List<Group> groups = new ArrayList<Group>();
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_groups_of_user(?)}");
			c.setInt(1, user_id);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				Group group = new Group();
				group.setGroupId(rs.getLong("id"));
				group.setGroupDescription("groupDescription");
				group.setGroupName(rs.getString("groupName"));
				group.setProjectId(rs.getInt("project_id"));
				group.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				group.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				group.setCover(rs.getString("cover"));
				group.setCreatorId(rs.getInt("creator"));
				group.setDeadline(rs.getTimestamp("projectDeadline").getTime());
				
				groups.add(group);
			}
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
		
		
		return groups;
	}
	
	
	/** 
	* @Title: getUsersOfGroup 
	* @Description: TODO 
	* @param @param group_id
	* @param @return
	* @param @throws SQLException    
	* @return List<User>    
	* @throws 
	*/
	public List<User> getUsersOfGroup(int group_id) throws SQLException{
		List<User> users = new ArrayList<User>();
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_users_of_group(?)}");
			c.setInt(1, group_id);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUserId(rs.getLong("id"));
				user.setAccountType(rs.getString("accountType"));
				user.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				user.setUpdateAt(rs.getTimestamp("updatedAt").getTime());
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setFacebook(rs.getString("facebook"));
				user.setAvatar(rs.getString("avatar"));
				users.add(user);
			}
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
		
		
		
		return users;
		
	}
	
	public void dropFromGroup(int group_id, int user_id) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call drop_a_group(?,?)}");
			c.setInt(1, group_id);
			c.setInt(2, user_id);
			
			c.executeUpdate();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
	}
	
	
	public void joinGroup(int group_id, int user_id) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call join_a_group(?,?)}");
			c.setInt(1, group_id);
			c.setInt(2, user_id);
			
			c.executeUpdate();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
	}
	
	public List<BaseMsg> getMessagesBefore(int group_id,long timestamp,int cnt) throws SQLException{
		List<BaseMsg> msgs = new ArrayList<BaseMsg>();
		Connection conn = db.getConnection();
		CallableStatement c = null;
		Gson gson = new Gson();
		try {
			c = conn.prepareCall("{call get_messages_before_time(?,?,?)}");
			c.setInt(1, group_id);
			System.out.println(timestamp);
			c.setTimestamp(2, new java.sql.Timestamp(timestamp));
			c.setInt(3, cnt);
			
			ResultSet rs = c.executeQuery();
			
			while(rs.next()){
				BaseMsg msg = new BaseMsg();
				msg.setType(MsgType.ChatMsg);
				msg.setClientId(rs.getString("sender_id"));
				msg.setDate(rs.getTimestamp("timestamp").getTime());
				msg.setGroupId(rs.getString("group_id"));
				msg.setAvator(rs.getString("avatar"));
				String body = rs.getString("msg_body");
				
				Type type = new TypeToken<Map<String,Object>>(){}.getType();
				
				msg.setParams((Map<String,Object>)gson.fromJson(body, type));
				
				msgs.add(msg);
				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
		return msgs;
	}
	
	
	
	public void deleteGroup(int group_id) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call delete_a_group(?)}");
			c.setInt(1, group_id);
			
			c.executeUpdate();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
	}

	public int addProject(String projectName, String projectDescription,
			long projectDeadline,int creator) throws SQLException {
		Connection conn = db.getConnection();
		CallableStatement c = null;
		try {
			
			c = conn.prepareCall("{call add_a_project(?,?,?,?,?)}");
			c.setString(1, projectName);
			c.setString(2, projectDescription);
			c.setTimestamp(3, new java.sql.Timestamp(projectDeadline));
			c.setInt(4, creator);
			c.registerOutParameter(5, Types.INTEGER);
			c.executeUpdate();
			
			return c.getInt(5);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
		
		return 0;
	}

	public Group addGroup(String groupName, String groupDescription, int projectId,int creatorId) throws SQLException {
		Connection conn = db.getConnection();
		CallableStatement c = null;
		Group group = null;
		try {
			// TODO implement proc
			c = conn.prepareCall("{call add_group(?,?,?,?)}");
			c.setString(1, groupName);
			c.setString(2, groupDescription);
			c.setInt(3, projectId);
			c.setInt(4, creatorId);
			
			ResultSet rs = c.executeQuery();
			
			if(rs.next()){
				group = new Group();
				group.setGroupId(rs.getLong("id"));
				group.setGroupDescription("groupDescription");
				group.setGroupName("groupName");
				group.setProjectId(rs.getInt("project_id"));
				group.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				group.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				group.setCover(rs.getString("cover"));
				group.setCreatorId(rs.getInt("creator"));
				group.setDeadline(rs.getTimestamp("projectDeadline").getTime());
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			c.close();
			
			db.dispose();
		}
		
		
		return group;
	}
}
