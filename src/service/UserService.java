package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UserDao;
import entity.User;
import enums.LoginType;

public class UserService {

	UserDao userDao = null;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public User doLogin(LoginType loginType, User user) throws SQLException{
		
		switch(loginType){
		case auto:
			return userDao.doLoginBy3rdParty(user);
		case email:
			return userDao.doLoginByEmail(user);
		case username:
			return userDao.doLoginByUsername(user);
		default:
			break;
		}
		
		return null;
		
		
	}
	
	public List<User> getUsers(String username){
		try {
			return userDao.getUsers(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}
	
	public User doRegister(User user) throws SQLException{
		return userDao.doRegister(user);
	}
	
	public boolean updateUserInfo(User user) throws SQLException{
		return userDao.updateInfo(user);
	}
}
