package com.example.messaging.controller;

import com.example.messaging.exception.InvalidFileFormatException;
import com.example.messaging.model.Recipient;
import com.example.messaging.service.ExcelImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/import")
@Tag(name = "Import", description = "API for importing recipient data")
@Validated
public class ImportController {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private final ExcelImportService excelImportService;

    public ImportController(ExcelImportService excelImportService) {
        this.excelImportService = excelImportService;
    }

    @PostMapping(
        value = "/excel",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Import recipients from Excel file",
        description = "Upload an Excel file to import recipients. The file should have columns: Name, Phone number, Channel name, Agent Phone"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Recipients imported successfully",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Recipient.class))
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid file format or content"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error"
    )
    public ResponseEntity<List<Recipient>> uploadExcel(
            @RequestParam("file") 
            @NotNull(message = "File is required")
            @Valid
            MultipartFile file) {
        
        // Validate file type
        if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            throw new InvalidFileFormatException("Invalid file type. Please upload an Excel file (.xlsx)");
        }

        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidFileFormatException("File size exceeds the maximum limit of 5MB");
        }

        try {
            List<Recipient> imported = excelImportService.importRecipients(file);
            return ResponseEntity.ok(imported);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the Excel file: " + e.getMessage(), e);
        }
    }
}
