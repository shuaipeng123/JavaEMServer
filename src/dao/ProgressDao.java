package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.EventStat;
import entity.Group;

import utils.Dbconn;

public class ProgressDao {
	private Dbconn db = null;
	
	public ProgressDao(){
		db = new Dbconn();
	}
	
	public Map<Integer,EventStat> getGroupStats(int user_id) throws SQLException{
		Map<Integer,EventStat> map = new HashMap<Integer,EventStat>();
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_group_stat(?)}");
			c.setInt(1, user_id);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				EventStat es = new EventStat();
				es.setFinished(rs.getInt("finished"));
				es.setStarted(rs.getInt("started"));
				map.put(rs.getInt("groupid"), es);
			}
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
		return map;
	}
	
	
	public List<Group> getGroupsIncludingPersonal(int user_id) throws SQLException{
		List<Group> groups = new ArrayList<Group>();
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_groups_of_user_includeing_personal(?)}");
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
}
