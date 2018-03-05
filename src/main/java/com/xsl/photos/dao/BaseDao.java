package com.xsl.photos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by msi- on 2018/3/3.
 */
public class BaseDao<T> {
    private HibernateTemplate hibernateTemplate;
    private Class<T> entityClass;
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    //通过反射类型获取泛型的class对象
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
    }

    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public T get(Serializable id) {
        return hibernateTemplate.get(entityClass, id);
    }

    public T load(Serializable id) {
        return hibernateTemplate.load(entityClass, id);
    }

    public void update(T entity) {
        hibernateTemplate.update(entity);
    }

    public void add(T entity) {
        hibernateTemplate.save(entity);
    }

    public void remove(T entity) {
        hibernateTemplate.delete(entity);
    }

    public List<T> loadAll() {
       return hibernateTemplate.loadAll(entityClass);
    }


}
