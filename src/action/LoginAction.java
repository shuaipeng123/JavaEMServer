package action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

import service.UserService;
import utils.MD5Utils;
import utils.StrutsUtil;

import entity.User;
import enums.ExecResult;
import enums.LoginType;

public class LoginAction {

	String username;
	String pwd;
	String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public void doLogin(){
		HttpServletResponse response = ServletActionContext.getResponse();   
		if(username == null || pwd == null || type == null){
			
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		
		UserService us = new UserService();
		
		User user = new User();
		user.setUsername(username);
		
		String psd = MD5Utils.md5(pwd,"ecelinux4");
		
		
		user.setPassword(pwd);
		User res = null;
		try {
			res = us.doLogin(LoginType.valueOf(type),user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			if(res == null){
				StrutsUtil.write(response,ExecResult.failed.toString());
			}else{
				StrutsUtil.write(response,new Gson().toJson(res));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
