package com.example.demo.controller;

import com.example.demo.model.Picture;
import com.example.demo.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PictureController {


    @Autowired
    PictureService pictureService;



    @GetMapping(path = "/")
    public String get(Model model, HttpServletRequest httpServletRequest) {

        List<Picture> pictures = pictureService.getProcessedAndAcceptedPictures();

        model.addAttribute("pictures", pictures);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {

            model.addAttribute("role", "none");
            return "pictures";

        } else if (httpServletRequest.isUserInRole("ADMIN")) {

            return "redirect:/processPictures";

        } else {

            model.addAttribute("role", "user");
            return "pictures";

        }

    }



    @GetMapping(path = "/uploadPicture")
    public String getUploadingForm() {

        return "uploadPicture";

    }



    @PostMapping(path = "/uploadPicture")
    public String uploadPicture(@RequestParam("file")MultipartFile pictureFile, @RequestParam("category")String category, @RequestParam("description")String description) throws IOException {

        pictureService.savePicture(pictureFile, category, description);

        return "redirect:/";

    }



    @GetMapping(path = "/showPicture/{id}")
    public void showProcessedPicture(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Picture picture = pictureService.getProcessedAndAcceptedPicture(id);

        if (picture != null) {

            response.setContentType("image/jpg, image/png, image/gif");
            response.getOutputStream().write(picture.getFile());

            response.getOutputStream().close();

        }
    }



    @GetMapping(path = "/showUnProcessedPicture/{id}")
    public void ShowUnprocessedPicture(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Picture picture = pictureService.getUnprocessedPicture(id);

        if (picture != null) {

            response.setContentType("image/jpg, image/png, image/gif");
            response.getOutputStream().write(picture.getFile());

            response.getOutputStream().close();

        }
    }



    @GetMapping(path = "/processPictures")
    public String adminPage(Model model) {

        List<Picture> pictures = pictureService.getUnprocessedAndRejectedPictures();

        model.addAttribute("pictures", pictures);

        return "processPictures";
    }



    @GetMapping(path = "/processPictures/{id}")
    public String processPage(@PathVariable Integer id, @ModelAttribute Picture picture, Model model) {

        picture = pictureService.getUnprocessedPicture(id);

        if (picture == null) {return "redirect:/uploadPicture";}

        model.addAttribute("picture", picture);

        return "processPicture";
    }



    @PostMapping(path = "/processPictures/{id}")
    public String takeAction(@PathVariable Integer id, @RequestParam("action")String action) {

        //return pictureService.processPicture(id, action);

        pictureService.processPicture(id, action);
        return "redirect:/processPictures";

    }


}
