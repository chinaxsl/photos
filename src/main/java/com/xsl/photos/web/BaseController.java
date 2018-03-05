package com.xsl.photos.web;

import com.xsl.photos.domain.User;
import com.xsl.photos.util.CommonVariables;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by msi- on 2018/3/3.
 */
public class BaseController {

    protected void setSessionUser(HttpServletRequest request,User user){
        request.getSession().setAttribute(CommonVariables.USER_CONTEXT,user);
    }

    protected User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(CommonVariables.USER_CONTEXT);
    }

    public final String getAppbaseUrl(HttpServletRequest request, String url){
        Assert.hasLength(url, "url不可为空");
        return request.getContextPath() + url;
    }

}
