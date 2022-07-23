package com.abhishek.leetcode.service;

import com.abhishek.leetcode.model.Question;
import com.abhishek.leetcode.model.TopicTag;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelCreationService {
    public static final String LEETCODE_PROBLEM_URL = "https://leetcode.com/problems/";
    @Value("${excel.path}")
    public String excelPath;

    @Autowired
    LeetcodeGraphqlClient client;
    public ArrayList<Object> mapQuestionsToExcelRowData(Question question) {
        ArrayList<Object> dataList = new ArrayList<>();
        dataList.add(question.getFrontendQuestionId());
        dataList.add(question.getTitle());
        dataList.add(LEETCODE_PROBLEM_URL +question.getTitleSlug());
        dataList.add(question.getDifficulty());
        dataList.add(String.valueOf(question.getAcRate()).substring(0,6));
        dataList.add(question.getTopicTags().stream().map(TopicTag::getName).collect(Collectors.joining(", ")));
        dataList.add(question.isPaidOnly());
        dataList.add(question.isHasSolution());
        dataList.add(question.isHasSolution());
        dataList.add(question.getFreqBar());
        return dataList;
    }

    public List<List<Object>> getExcelData(List<Question> questions){
        return questions.stream().map(this::mapQuestionsToExcelRowData).collect(Collectors.toList());
    }

    public boolean writeExcel(List<List<Object>> dataList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Questions");
        List<String> headings = Arrays.asList("Question Number", "Question title", "Question Link", "Difficulty", "Accuracy", "Topics", "Premium", "Solution Available", "Has Video Solution", "Frequency");
        Row headingRow = sheet.createRow(1);
        int columnCount = 0;

        for (String field : headings) {
            Cell cell = headingRow.createCell(++columnCount);
            cell.setCellValue((String) field);
        }


        int rowCount = 1;

        for (List<Object> data : dataList) {
            Row row = sheet.createRow(++rowCount);

            columnCount = 0;

            for (Object field : data) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
                else if (field instanceof Boolean) {
                    cell.setCellValue((Boolean) field);
                }
            }

        }
        try (FileOutputStream outputStream = new FileOutputStream(excelPath+"/leetcode.xlsx")) {
            workbook.write(outputStream);
        }

        return true;
    }

    public void createExcel() throws IOException {
        List<List<Object>> excelData = getExcelData(client.getAllQuestions());
        writeExcel(excelData);
    }
}
