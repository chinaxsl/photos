package com.xsl.photos.dao;

import com.xsl.photos.domain.User;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;

/**
 * Created by msi- on 2018/3/3.
 */
@Repository
public class UserDao extends BaseDao<User> {
    private static final String GET_USER_BY_NAME = "from User u where u.userName = ?";

    public User getUserByName(String userName){
        List<User> users = (List<User>) getHibernateTemplate().find(GET_USER_BY_NAME, userName);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
