package com.abhishek.leetcode.service;

import com.abhishek.leetcode.model.*;
import com.abhishek.leetcode.utils.GraphqlSchemaReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeetcodeGraphqlClient {

    @Value("${leetcode.graphql.url}")
    private String url;

    public ProblemResponse getAllProblems(String category, int skip, int limit, Map<String, String> filters) throws IOException {

        WebClient webClient = WebClient.builder().build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

        ProblemRequestVariable variable = ProblemRequestVariable.builder()
                .categorySlug(category)
                .skip(skip)
                .limit(limit)
                .filters(filters)
                .build();

        final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getProblemList");
        final String variables = new ObjectMapper().writeValueAsString(variable);

        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(variables);

        return webClient.post()
                .uri(url)
                .bodyValue(graphQLRequestBody)
                .retrieve()
                .bodyToMono(ProblemResponse.class)
                .block();
    }

    public List<Question> getAllQuestions() throws IOException {
        List<Question> questionList = new ArrayList<>();
        ProblemResponse allProblems = getAllProblems("", 0, 1, new HashMap<>());
        Integer totalProblems = getTotalProblems(allProblems);
        if (totalProblems == null) return questionList;
        while (questionList.size() < totalProblems) {
            allProblems = getAllProblems("", questionList.size(), 500, new HashMap<>());
            ArrayList<Question> questions = getQuestions(allProblems);
            if (questions == null) break;
            questionList.addAll(questions);
        }
        return questionList;
    }

    private ArrayList<Question> getQuestions(ProblemResponse allProblems) {
        return Optional.ofNullable(allProblems)
                .map(ProblemResponse::getData)
                .map(Data::getProblemsetQuestionList)
                .map(ProblemsetQuestionList::getQuestions)
                .orElse(null);
    }

    private Integer getTotalProblems(ProblemResponse allProblems) {
        return Optional.ofNullable(allProblems)
                .map(ProblemResponse::getData)
                .map(Data::getProblemsetQuestionList)
                .map(ProblemsetQuestionList::getTotal)
                .orElse(null);
    }


}
