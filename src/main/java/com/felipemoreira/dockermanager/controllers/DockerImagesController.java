package com.felipemoreira.dockermanager.controllers;

import com.felipemoreira.dockermanager.services.DockerService;
import com.github.dockerjava.api.model.Image;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/images")
public class DockerImagesController  {

    private static final Logger logger = LoggerFactory.getLogger(DockerService.class);
    private final DockerService dockerService;

    @GetMapping("")
    public List<Image> listImages() {
        List<Image> images = dockerService.listImages();
        logger.info("Images {}", images.size());
        return images;
    }

    @GetMapping("/filter")
    public List<Image> listImages(@RequestParam(required = false, defaultValue = "image-") String filterName) {
        return dockerService.filterImages(filterName);
    }
}
