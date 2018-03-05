package com.xsl.photos.web;

import com.xsl.photos.domain.Image;
import com.xsl.photos.domain.User;
import com.xsl.photos.service.ImageService;
import com.xsl.photos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by msi- on 2018/3/3.
 */
@Controller
public class ImageController extends BaseController{
    private ImageService imageService;
    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    public ModelAndView uploadImage(HttpServletRequest request, @RequestPart("image") MultipartFile file, Image image) throws IOException {
        ModelAndView view = new ModelAndView();
        User user = getSessionUser(request);
        image.setUrl(user.getUserName() + "/" + UUID.randomUUID());
        image.setUploadTime(new Date());
        image.setUser(user);
        InputStream inputStream = file.getInputStream();
        Image imageDb = imageService.getImageByName(image.getImageName(),user.getUserId());
        if (imageDb == null) {
            imageService.uploadImage(inputStream, image);
        } else {
            view.addObject("errorMsg","该图片名已存在");
        }
        view.setViewName("redirect:/home.html");
        return view;
    }

    @RequestMapping(value = "/deleteImage",method = RequestMethod.POST)
    public String deleteImage(@RequestParam("ids") String imageName, @RequestParam("urls") String imageUrl) {
        imageService.deleteImages(imageName,imageUrl);
        return "redirect:/home.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView listAllImages(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getSessionUser(request);
        List<Image> images = imageService.getImagesByUserId(user.getUserId());
        modelAndView.addObject("user",user);
        modelAndView.addObject("imageList",images);
        modelAndView.setViewName("listAllImages");
        return modelAndView;

    }
}
