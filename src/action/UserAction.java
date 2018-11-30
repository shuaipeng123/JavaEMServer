package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import service.UserService;
import utils.StrutsUtil;
import entity.User;
import enums.ExecResult;

public class UserAction {

	String username;
	
	
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void getUsers(){
		HttpServletResponse response = ServletActionContext.getResponse();  
		if(username == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		List<User> users = new UserService().getUsers(username);
		
		try {
			StrutsUtil.write(response,new Gson().toJson(users));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
