package com.xsl.photos.service;

import com.xsl.photos.dao.ImageDao;
import com.xsl.photos.dao.UserDao;
import com.xsl.photos.domain.Image;
import com.xsl.photos.domain.User;
import com.xsl.photos.util.FileUtils;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by msi- on 2018/3/3.
 */
@Service
public class ImageService {
    private ImageDao imageDao;
    private UserDao userDao;
    @Autowired
    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void uploadImage(InputStream inputStream,Image image) {
        imageDao.add(image);
        FileUtils.upload(inputStream,image.getUrl());
    }

    public void deleteImage(String imageName) {
        Image image = imageDao.getImageByName(imageName);
        imageDao.remove(image);
        FileUtils.delete(image.getUrl());
    }


    public void deleteImages(String idx,String urls) {
        String[] idArray = idx.split(",");
        String[] urlArray = urls.split(",");
        Integer[] ids = new Integer[idArray.length];
        if (!"".equals(idArray[0]) && !"".equals(urlArray[0])) {
            for (int i = 0; i<idArray.length;i++) {
                ids[i] = Integer.parseInt(idArray[i]);
                FileUtils.delete(urlArray[i]);
            }
        }
        imageDao.deleteImages(ids);
    }
    public Image getImageByName(String imageName,int userId) {
        Assert.hasText(imageName);
        User user = userDao.get(userId);
        if (user != null) {
            List<Image> images = user.getImages();
            for (Image image : images) {
                if (imageName.equals(image.getImageName())) {
                    return image;
                }
            }
        }
        return null;
    }

    public List<Image> getImagesByUserId(int userId) {
        User user = userDao.get(userId);
        if (user != null) {
            return user.getImages();
        }
        return new ArrayList<Image>();
    }
}
