package action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import service.ProgressService;
import utils.StrutsUtil;
import entity.EventStat;
import entity.Group;
import enums.EventStatus;
import enums.ExecResult;

public class ProgressAction {

	int user_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public void getGroupsIncludingPersonal(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user_id == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		List<Group> groups = new ProgressService().getGroupsIncludingPersonal(user_id);
		
		if(groups == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(groups));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void getGroupStats(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user_id == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		Map<Integer,EventStat> stats =  new ProgressService().getGroupStats(user_id);
		
		if(stats == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(stats));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
