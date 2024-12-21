package com.kkrinitskiy.bot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class TextGeneratorService {
    private final VocabularyService vocabularyService;
    private List<String> vocabulary;

    public TextGeneratorService(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
        vocabulary = vocabularyService.getBotsVocabulary();
    }

    public String getRandomText() {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(vocabulary.get(rand.nextInt(vocabulary.size()))).append(" ");
        }
        return result.toString();
    }
}
