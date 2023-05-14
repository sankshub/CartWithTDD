package com.sank.bookshop.repos.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
@Component
public class BookRepository {
    @Autowired
    private ResourceLoader resourceLoader;
    public List<String> findAll()  {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:masterbookfeed.json");
        try (FileReader reader = new FileReader(resource.getFile())) {
            return mapper.readValue(reader, new TypeReference<List<String>>() {
            });
        } catch (IOException | NullPointerException e) {
            return Collections.emptyList();
        }
    }
}
