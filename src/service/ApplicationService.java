package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ApplicationDao;
import entity.Application;

public class ApplicationService {

	private ApplicationDao appDao = null;
	public ApplicationService(){
		appDao = new ApplicationDao();
	}
	
	public List<Application> getApplications(int user_id){
		try {
			return appDao.getApplications(user_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Application>();
	}
	
	public Application addApplication(int fromId, int toId, int groupId){
		try {
			return appDao.addApplication(fromId, toId, groupId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean finishApplication(int appId, int isAccept){
		try {
			
			appDao.finishApplication(appId,isAccept);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
