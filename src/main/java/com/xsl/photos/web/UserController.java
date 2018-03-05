package com.xsl.photos.web;

import com.xsl.photos.domain.User;
import com.xsl.photos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.xsl.photos.util.CommonVariables.USER_CONTEXT;

/**
 * Created by msi- on 2018/3/3.
 */
@Controller
public class UserController extends BaseController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public ModelAndView dologin(HttpServletRequest request, HttpServletResponse response,User user){
        ModelAndView view = new ModelAndView();
        response.setCharacterEncoding("UTF-8");
        int result;
        User userDb = userService.getUserByName(user.getUserName());
        String toUrl = "/index";
        if (userDb == null) {
            view.addObject("errorMsg","该用户不存在");
            result = 3;
        } else if (!userDb.getPassword().equals(user.getPassword())) {
            view.addObject("errorMsg","密码错误");
            result = 4;
        } else {
            setSessionUser(request,userDb);
            result = 5;
            toUrl = "/home";
        }
//        view.setViewName(toUrl);
        try {
            response.getWriter().write(result+"");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @RequestMapping(value = "/doRegister",method = RequestMethod.POST)
    public ModelAndView doRegister(HttpServletRequest request,HttpServletResponse response,User user) {
        ModelAndView view = new ModelAndView();
        response.setCharacterEncoding("UTF-8");
        int result;
        User userDb = userService.getUserByName(user.getUserName());
        if (userDb!=null){
            view.addObject("errorMsg","该用户名已存在");
            result = 1;
        } else {
            userService.register(user);
            setSessionUser(request,user);
            result = 6;
        }
        try {
            response.getWriter().write(result);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        view.setViewName("/success");
        return view;
    }

    @RequestMapping(value = "/doLogout")
    public String doLogout(HttpSession session){
        session.removeAttribute(USER_CONTEXT);
        return "forward:/index.jsp";
    }
}
