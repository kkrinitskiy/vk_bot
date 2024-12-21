package com.kkrinitskiy.bot.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class VocabularyService {
    @Getter
    private final List<String> botsVocabulary;
    private final String fileName;

    public VocabularyService(@Value("${vk.fileName}") String fileName) {
        this.fileName = fileName;
        this.botsVocabulary = new ArrayList<>();
        initVocabulary();
    }

    public void initVocabulary() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                log.error("Файл не найден в resources: " + fileName);
                return;
            }
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    Arrays.stream(line.split(",")).toList().forEach(s -> botsVocabulary.add(s.trim()));
                }
            }
            log.info("Словарь успешно загружен");
        } catch (IOException e) {
            log.info("Ошибка при чтении файла: " + e.getMessage());
        }
    }

//    TODO: addWords()
}
