package com.logic.gate.picture.repository;


import com.logic.gate.picture.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture,Long> {
}
