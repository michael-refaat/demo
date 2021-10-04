package com.example.demo.service;

import com.example.demo.model.Picture;
import com.example.demo.repository.PictureRepository;
import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;



import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PictureServiceTest {

    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private PictureService pictureService;



    @SneakyThrows
    @Test
    void savePicture() {

        // Arrange
        File file = new File("Untitled.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("Untitled.png", fileInputStream);
        String category = "category1";
        String description = "description1";

        // Act
        Picture picture = pictureService.savePicture(multipartFile, category, description);

        // Assert
        assertEquals(multipartFile.getBytes(), picture.getFile());
        assertEquals(category, picture.getCategory());
        assertEquals(description, picture.getDescription());




    }


}