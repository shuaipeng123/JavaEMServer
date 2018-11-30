package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import service.ApplicationService;
import service.GroupService;
import utils.StrutsUtil;
import entity.Application;
import enums.ExecResult;

public class ApplicationAction {

	int user_id;
	int fromId; 
	int toId; 
	int groupId;
	int appId;
	int isAccept;
	
	
	
	public int getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	public void getApplications(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		ApplicationService appService = new ApplicationService();
		
		if(user_id==0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		List<Application> apps = appService.getApplications(user_id);
		
		if(apps == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(apps));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void addApplicaton(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		ApplicationService appService = new ApplicationService();
		
		if(fromId==0 || toId == 0 || groupId == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		Application app = appService.addApplication(fromId, toId, groupId);
		
		if(app == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(app));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void finishApplication(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		ApplicationService appService = new ApplicationService();
		
		if(appId == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		boolean res = appService.finishApplication(appId,isAccept);
		
		if(res){
			try {
				StrutsUtil.write(response,ExecResult.success.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
