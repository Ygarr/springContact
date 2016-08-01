package com.borrowed.contact.dao;

import java.util.List;

import com.borrowed.contact.model.User;

public interface ContactDAO {
	
	public void saveOrUpdate(User user);
	
	public void delete(int contactId);
	
	public User get(int contactId);
	
	public List<User> list();
}
