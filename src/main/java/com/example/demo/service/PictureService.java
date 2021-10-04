package com.example.demo.service;

import com.example.demo.model.Picture;
import com.example.demo.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PictureService {


    private final PictureRepository pictureRepository;


    public Picture savePicture(MultipartFile pictureFile, String category, String description) throws IOException {

        String name = pictureFile.getOriginalFilename();
        String type = pictureFile.getContentType();
        byte[] file = pictureFile.getBytes();

        BufferedImage bufferedImage = ImageIO.read(pictureFile.getInputStream());
        Integer width = bufferedImage.getWidth();
        Integer height = bufferedImage.getHeight();

        Picture p = new Picture(name, category, description, type, width, height, false, null, file);

        //return pictureRepository.save(p);
        pictureRepository.save(p);
        return p;

    }


    public List<Picture> getProcessedAndAcceptedPictures() {
        return pictureRepository.findByAction("Accepted");
    }


    public Picture getProcessedAndAcceptedPicture(Integer id) {
        return pictureRepository.findByIdAndAction(id, "Accepted");
    }


    public List<Picture> getUnprocessedAndRejectedPictures() {
        return pictureRepository.findByIsProcessedOrAction(false, "Rejected");
    }


    public Picture getUnprocessedPicture(Integer id) {
        return pictureRepository.findByIdAndIsProcessed(id, false);
    }


    public Picture processPicture(Integer id, String action) {

        Picture picture = getUnprocessedPicture(id);

        if (picture != null) {

            if (action.equals("Accept")) {

                picture.setProcessed(true);
                picture.setAction("Accepted");

                //pictureRepository.save(picture);

                //return "redirect:/processPictures";

                pictureRepository.save(picture);
                return picture;

            } else if (action.equals("Reject")) {

                picture.setProcessed(true);
                picture.setFile(null);
                picture.setAction("Rejected");

                //pictureRepository.save(picture);

                //return "redirect:/processPictures";

                pictureRepository.save(picture);
                return picture;
            }
        }
        return picture;
    }


}
