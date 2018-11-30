package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Application;

import utils.Dbconn;

public class ApplicationDao {

	private Dbconn db = null;
	
	public ApplicationDao(){
		db = new Dbconn();
	}
	
	public List<Application> getApplications(int user_id) throws SQLException{
		List<Application> apps = new ArrayList<Application>();
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_applications(?)}");
			c.setInt(1, user_id);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				Application app = new Application();
				
				app.setId(rs.getInt("application.id"));
				app.setFrom(rs.getInt("from"));
				app.setFromName(rs.getString("user.username"));
				app.setGroupDescription(rs.getString("group.groupDescription"));
				app.setGroupId(rs.getInt("groupId"));
				app.setGroupName(rs.getString("group.groupName"));
				app.setTimestamp(rs.getTimestamp("timestamp").getTime());
				app.setTo(rs.getInt("to"));
				
				apps.add(app);
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return apps;
	}
	
	public Application addApplication(int fromId, int toId, int groupId) throws SQLException{
		Application app = null;
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call add_application(?,?,?)}");
			c.setInt(1, fromId);
			c.setInt(2, toId);
			c.setInt(3, groupId);
			
			ResultSet rs = c.executeQuery();
			if(rs.next()){
				app = new Application();
				
				app.setId(rs.getInt("application.id"));
				app.setFrom(rs.getInt("application.from"));
				app.setFromName(rs.getString("user.username"));
				app.setGroupDescription(rs.getString("group.groupDescription"));
				app.setGroupId(rs.getInt("application.groupId"));
				app.setGroupName(rs.getString("group.groupName"));
				app.setTimestamp(rs.getTimestamp("application.timestamp").getTime());
				app.setTo(rs.getInt("application.to"));
				
				
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return app;
	}
	
	public void finishApplication(int appId, int accept) throws SQLException{
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call finish_application(?,?)}");
			c.setInt(1, appId);
			c.setInt(2, accept);
			c.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
	}
}
