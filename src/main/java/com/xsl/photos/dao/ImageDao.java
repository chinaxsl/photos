package com.xsl.photos.dao;

import com.xsl.photos.domain.Image;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by msi- on 2018/3/3.
 */
@Repository
public class ImageDao extends BaseDao<Image> {
    private static final String GET_IMAGE_BY_NAME = "from Image i where i.imageName=?";
    private static final String DELETE_IMAGES_SQL = "delete Image i where i.imageId in (:ids)";
    public Image getImageByName(String imageName) {
        return (Image) getHibernateTemplate().find(GET_IMAGE_BY_NAME, imageName);
    }

    public void deleteImages(final Serializable[] idArray) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = (Query) session.createQuery(DELETE_IMAGES_SQL);
                query.setParameterList("ids",idArray);
                query.executeUpdate();
                return null;
            }
        });
    }
}
