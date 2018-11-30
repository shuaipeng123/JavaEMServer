package action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import service.GroupService;
import utils.StrutsUtil;

import com.google.gson.Gson;

import entity.Group;
import enums.ExecResult;

public class GroupAction {

	int user_id;
	int group_id;
	
	//add a group
	/*for project*/
	String projectName;
	String projectDescription;
	long projectDeadline;
	int creator_id;
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public long getProjectDeadline() {
		return projectDeadline;
	}
	public void setProjectDeadline(long projectDeadline) {
		this.projectDeadline = projectDeadline;
	}
	public int getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	// user_id, group_id
	public void dropFromGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		GroupService gs = new GroupService();
		
		if(user_id==0 || group_id==0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		boolean res = gs.dropFromGroup(group_id, user_id);
		
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
	
	// user_id, group_id
	public void joinGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		GroupService gs = new GroupService();
		
		if(user_id==0 || group_id==0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		boolean res = gs.joinGroup(group_id, user_id);
		
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
	
	
	public void deleteGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		GroupService gs = new GroupService();
		if(group_id == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		boolean res = gs.deleteGroup(group_id);
		
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
	
	public void addGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		GroupService gs = new GroupService();
		if(projectName == null && projectDescription == null && projectDeadline == 0 && creator_id == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		Group res = null;
		try {
			res = gs.addGroup(projectName, projectDescription, projectDeadline, creator_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(res != null){
			try {
				StrutsUtil.write(response,new Gson().toJson(res));
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
