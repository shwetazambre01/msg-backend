package com.example.messaging.service;

import com.example.messaging.model.Recipient;
import com.example.messaging.repository.RecipientRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelImportService {

    private final RecipientRepository recipientRepository;

    public ExcelImportService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public List<Recipient> importRecipients(MultipartFile file) throws Exception {
        List<Recipient> saved = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean headerRow = true;
            for (Row row : sheet) {
                if (headerRow) { headerRow = false; continue; } // skip header
                Cell nameCell = row.getCell(0);
                Cell phoneCell = row.getCell(1);
                Cell emailCell = row.getCell(2);

                if (phoneCell == null) continue;
                String phone = getCellString(phoneCell).trim();
                if (phone.isEmpty()) continue;

                String name = nameCell != null ? getCellString(nameCell) : "";
                String email = emailCell != null ? getCellString(emailCell) : "";

                Optional<Recipient> existing = recipientRepository.findByPhone(phone);
                Recipient r = existing.orElseGet(Recipient::new);
                r.setPhone(phone);
                r.setName(name);
                r.setEmail(email);
                r.setImportedFrom(file.getOriginalFilename());

                recipientRepository.save(r);
                saved.add(r);
            }
        }
        return saved;
    }

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
}
