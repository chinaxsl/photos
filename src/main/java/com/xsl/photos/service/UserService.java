package com.xsl.photos.service;

import com.xsl.photos.dao.UserDao;
import com.xsl.photos.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/3/3.
 */
@Service
public class UserService {
    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(int userId) {
        return userDao.get(userId);
    }

    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);

    }
    public void register(User user) {
        userDao.add(user);
    }
}
