package com.example.messaging.controller;

import com.example.messaging.model.Recipient;
import com.example.messaging.service.ExcelImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class ImportController {
    private final ExcelImportService excelImportService;

    public ImportController(ExcelImportService excelImportService) {
        this.excelImportService = excelImportService;
    }

    @PostMapping("/excel")
    public ResponseEntity<List<Recipient>> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Recipient> imported = excelImportService.importRecipients(file);
            return ResponseEntity.ok(imported);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
