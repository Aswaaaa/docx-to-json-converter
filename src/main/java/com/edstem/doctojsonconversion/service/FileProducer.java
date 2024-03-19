package com.edstem.doctojsonconversion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public FileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void uploadFile(MultipartFile file) throws Exception {
        XWPFDocument document = new XWPFDocument(file.getInputStream());
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);

        Map<String, String> mapJson = new HashMap<>();
        mapJson.put("text", extractor.getText());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mapJson);

        Message<String> message = MessageBuilder
                .withPayload(json)
                .setHeader(KafkaHeaders.TOPIC, "converter")
                .build();
        kafkaTemplate.send(message);

    }
}
