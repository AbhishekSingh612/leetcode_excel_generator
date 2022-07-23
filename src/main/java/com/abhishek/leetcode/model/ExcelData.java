package com.abhishek.leetcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExcelData {
    String questionNumber;
    String questiontitle;
    String link;//http://leetcode.com/problems/titleslug
    String difficulty;
    String accuracy;
    String topics;
    boolean locked;
    boolean hasSolution;
    boolean hasVideoSolution;
    Object freqBar;
}
