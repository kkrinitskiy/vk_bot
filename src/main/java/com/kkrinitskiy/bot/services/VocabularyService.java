package com.kkrinitskiy.bot.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class VocabularyService {
    @Getter
    private final List<String> botsVocabulary;
    private final String vocabularyPath;

    public VocabularyService(@Value("${vk.vocabularyPath}") String fileName) {
        this.vocabularyPath = fileName;
        this.botsVocabulary = new ArrayList<>();
        initVocabulary();
    }

    public void initVocabulary() {
        try (Stream<String> lines = Files.lines(Paths.get(vocabularyPath))) {
            lines.forEach(line -> Arrays.stream(line.split(",")).toList().forEach(s -> botsVocabulary.add(s.trim())));
            log.info("Словарь успешно загружен");
        } catch (IOException e) {
            log.info("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
