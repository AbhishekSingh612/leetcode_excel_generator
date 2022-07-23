package com.abhishek.leetcode.model;

import lombok.*;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProblemsetQuestionList{
    public int total;
    public ArrayList<Question> questions;
}