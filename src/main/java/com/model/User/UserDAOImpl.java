package com.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.model.Groups.Groups;


import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;
	private static final int DEFAULT_GROUPID_USER = 25;
	
	public UserDAOImpl(SessionFactory _sessionFactory)
	{
		this.sessionFactory = _sessionFactory;
	}
	
	@SuppressWarnings("unchecked") 
	public List<User> listUser() {
		List<User> userList = new ArrayList<User>();
		
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction tx2 = session.beginTransaction();
		
		userList = (List<User>) session.createQuery("FROM User u LEFT JOIN FETCH u.groups").list();
		
		tx2.commit();
		session.close();
		
		return userList;
	}
	
	public void addUser(User user) {
		user.encryptPasswd();
		Session session = this.sessionFactory.openSession();
		
		session.beginTransaction();
			
		Groups group = (Groups)session.get(Groups.class, DEFAULT_GROUPID_USER);
		user.setGroups(group);
			
		session.persist(user);
		session.getTransaction().commit();
		
		session.close();
	}
	
	public boolean checkLogin(String userName, String passWord) {
		User user = new User();
		User tmp = new User();
		tmp.setPassWord(passWord);
		tmp.encryptPasswd();
		
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		user = (User) session.get(User.class, userName);
		if(!userName.equals("") && !passWord.equals(""))
		{
			if(user.getPassWord()== tmp.getPassWord())
			{
				return true;
			}
		}
		return false;
	}

	public User getUser(int userid)  {
		User user = null;
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction tx2 = session.beginTransaction();
		
		user = (User) session.get(User.class, userid);
		
		tx2.commit();
		session.close();
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> listUserId() {
		List<User> userList = new ArrayList<User>();
		
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction tx2 = session.beginTransaction();
		
		userList = (List<User>) session.createQuery("FROM User u LEFT JOIN FETCH u.groups").list();
		
		tx2.commit();
		session.close();
		
		return userList;
	}

	public Groups getGroupById(Integer userId) {
		Groups group = null;
		Session session = this.sessionFactory.openSession();
		org.hibernate.Transaction tx2 = session.beginTransaction();
		
		group = (Groups) session.get(Groups.class, userId);
		
		tx2.commit();
		session.close();
		
		return group;
	}
}
