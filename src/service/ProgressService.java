package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dao.ProgressDao;

import entity.EventStat;
import entity.Group;

public class ProgressService {

	private ProgressDao progressDao;
	
	public ProgressService() {
		progressDao = new ProgressDao();
	}
	
	public Map<Integer,EventStat> getGroupStats(int user_id){
		try {
			return progressDao.getGroupStats(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Group> getGroupsIncludingPersonal(int user_id){
		try {
			List<Group> groups = progressDao.getGroupsIncludingPersonal(user_id);
			Group group = new Group();
			group.setDeadline(new Date().getTime());
			group.setGroupName("Personal");
			group.setGroupId(1);
			group.setCreatorId(user_id);
			groups.add(0,group);
			
			return groups;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Group>();
	}
}
