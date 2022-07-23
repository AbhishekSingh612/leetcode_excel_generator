package com.abhishek.leetcode.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProblemRequestVariable {
    public String categorySlug;
    public int skip;
    public int limit;
    public Map<String,String> filters;
}