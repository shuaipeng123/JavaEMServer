package action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import entity.User;
import enums.ExecResult;

import service.UserService;
import utils.StrutsUtil;

public class RegisterAction {
	
	String username;
	String email;
	String pwd;
	String type;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void doRegister(){
		HttpServletResponse response = ServletActionContext.getResponse();   
		if(username == null || pwd == null || email == null || type == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(pwd);
		user.setEmail(email);
		user.setAccountType(type);
		
		try {
			user = new UserService().doRegister(user);
			
			try {
				StrutsUtil.write(response,new Gson().toJson(user));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
