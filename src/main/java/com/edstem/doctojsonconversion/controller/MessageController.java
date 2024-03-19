package com.edstem.doctojsonconversion.controller;

import com.edstem.doctojsonconversion.service.FileProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/doc")
public class MessageController {
    private FileProducer fileProducer;

    public MessageController(FileProducer fileProducer) {
        this.fileProducer = fileProducer;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        fileProducer.uploadFile(file);
        return "File Upload Successfully";

    }
}
