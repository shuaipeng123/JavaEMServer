package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.GroupDao;

import entity.BaseMsg;
import entity.Group;
import entity.MsgList;
import entity.Project;
import entity.User;

public class GroupService {

	private GroupDao groupDao = null;
	
	public GroupService(){
		groupDao = new GroupDao();
		
	}
	
	/** 
	* @Title: getGroupsOfUser 
	* @Description: TODO 
	* @param @param user_id
	* @param @return    
	* @return List<Group>    
	* @throws 
	*/
	public List<Group> getGroupsOfUser(int user_id){
		try {
			return groupDao.getGroupsOfUser(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Group>();
	}
	
	
	/** 
	* @Title: getUsersOfGroup 
	* @Description: TODO 
	* @param @param group_id
	* @param @return    
	* @return List<User>    
	* @throws 
	*/
	public List<User> getUsersOfGroup(int group_id){
		try {
			return groupDao.getUsersOfGroup(group_id);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return new ArrayList<User>();
	}
	
	public boolean dropFromGroup(int group_id, int user_id){
		try {
			groupDao.dropFromGroup(group_id, user_id);
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean joinGroup(int group_id, int user_id){
		try {
			groupDao.joinGroup(group_id, user_id);
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteGroup(int group_id){
		try {
			groupDao.deleteGroup(group_id);
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public Group addGroup(String projectName, String projectDescription, long projectDeadline,int creatorId) throws SQLException{
		int projectId = groupDao.addProject(projectName,projectDescription,projectDeadline,creatorId);
		if(projectId == 0){
			return null;
		}
		String groupName = "Group For "+projectName;
		String groupDescription = projectDescription;
		
		Group group = groupDao.addGroup(groupName,groupDescription,projectId,creatorId);
		
		return group;
		
	}
	
	public MsgList getMsgs(int group_id,long timestamp,int cnt){
		List<BaseMsg> msgs = null;
		
		try {
			// to get one more to check whether there are more messages or not
			msgs = groupDao.getMessagesBefore(group_id, timestamp, cnt+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MsgList list = new MsgList();
		list.setHasMore(true);
		if(msgs == null) return list;
		if(msgs.size() == cnt+1){
			list.setHasMore(true);
			if(msgs.size() > 0){
				msgs.remove(msgs.size()-1);
			}
			list.setMsgs(msgs);
		}else{
			list.setHasMore(false);
			list.setMsgs(msgs);
		}
		
		return list;
	}
	
	
	public static void main(String[] args) throws SQLException {
		GroupService gs = new GroupService();
		Group g = gs.addGroup("test service", "desc", new Date().getTime(), 2);
		System.out.println(g.getCreatorId());
	}
}
