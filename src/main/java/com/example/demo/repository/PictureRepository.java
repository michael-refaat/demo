package com.example.demo.repository;

import com.example.demo.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Picture, Integer> {

    Picture findByIdAndAction(Integer id, String action);

    List<Picture> findByAction(String action);

    Picture findByIdAndIsProcessed(Integer id, Boolean isProcessed);

    List<Picture> findByIsProcessedOrAction(Boolean isProcessed, String action);

}
