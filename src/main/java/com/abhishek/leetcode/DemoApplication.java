package com.abhishek.leetcode;


import com.abhishek.leetcode.service.ExcelCreationService;
import com.abhishek.leetcode.service.LeetcodeGraphqlClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	LeetcodeGraphqlClient clientService;

	@Autowired
	ExcelCreationService excelCreationService;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		excelCreationService.createExcel();

	}

}
