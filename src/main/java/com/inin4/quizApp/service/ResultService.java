package com.inin4.quizApp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inin4.quizApp.model.Result;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ResultService {

    private final ObjectMapper mapper;
    private final File file;

    public ResultService() {
      
      this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        File directory = new File("data");
      
      if (!directory.exists()) {
            directory.mkdirs();
        }

        this.file = new File("data/results.json");
    }

    public synchronized void saveResult(Result result) {
        
      List<Result> results = getAllResults();
        results.add(result);
      
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, results);
      
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku:");
            e.printStackTrace();
        }
    }

    public synchronized List<Result> getAllResults() {
        
      try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Result>>() {});
          
        } catch (IOException e) {
            System.err.println("Błąd odczytu z pliku:");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
