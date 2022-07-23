package com.abhishek.leetcode.model;

import lombok.*;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TopicTag{
    public String name;
    public String id;
    public String slug;
}