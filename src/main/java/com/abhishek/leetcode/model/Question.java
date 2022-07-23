package com.abhishek.leetcode.model;

import lombok.*;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Question{
    public double acRate;
    public String difficulty;
    public Object freqBar;
    public String frontendQuestionId;
    public boolean isFavor;
    public boolean paidOnly;
    public Object status;
    public String title;
    public String titleSlug;
    public ArrayList<TopicTag> topicTags;
    public boolean hasSolution;
    public boolean hasVideoSolution;
}