package kr.ac.tukorea.grade.service;

import kr.ac.tukorea.grade.dto.StudentDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportStudents(List<StudentDTO> students) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("학생 목록");

            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            String[] headers = {"학번", "이름", "학년", "학과", "전화번호", "이메일", "상태", "평균점수"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (StudentDTO s : students) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(nvl(s.getStudentNumber()));
                row.createCell(1).setCellValue(nvl(s.getName()));
                row.createCell(2).setCellValue(nvl(s.getGrade()));
                row.createCell(3).setCellValue(nvl(s.getDepartment()));
                row.createCell(4).setCellValue(nvl(s.getPhone()));
                row.createCell(5).setCellValue(nvl(s.getEmail()));
                row.createCell(6).setCellValue(nvl(s.getStatus()));
                Cell avgCell = row.createCell(7);
                if (s.getAverage() != null) avgCell.setCellValue(s.getAverage());
                else avgCell.setCellValue("-");
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        }
    }

    private String nvl(String s) { return s != null ? s : ""; }
}
