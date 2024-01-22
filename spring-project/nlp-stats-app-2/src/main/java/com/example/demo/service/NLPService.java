package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.example.demo.model.NLPResult;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import org.springframework.stereotype.Service;
import java.util.Properties;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
@Service
public class NLPService {

 

	public NLPResult analyzeText(String text) {
        // Set up the Stanford CoreNLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Create a document object
        CoreDocument document = pipeline.processToCoreDocument(text);

        // NLP statistics
        int wordCount = 0;
        int sentenceCount = document.sentences().size();
        int verbCount = 0;
        int commonNounCount = 0;
        int properNounCount = 0;
        Set<String> uniqueTokens = new HashSet<>();

        for (CoreSentence sentence : document.sentences()) {
            wordCount += sentence.tokens().size();
            
            for (CoreLabel token : sentence.tokens()) {
            	uniqueTokens.add(token.word().toLowerCase());
                if (token.tag().startsWith("VB")) {
                    verbCount++;
                }
                else if (token.tag().startsWith("NN") && !token.tag().equals("NNP") && !token.tag().equals("NNPS")) {
                	commonNounCount++;
                }
                else if (token.tag().equals("NNP") || token.tag().equals("NNPS")) {
                	properNounCount++;
                }
                
            }
        }
        
        double typeTokenRatio = uniqueTokens.size() / (double) wordCount;

        // Assuming average sentiment score as sentiment polarity for simplicity
        // Stanford CoreNLP does not provide a direct measure of subjectivity

        // Create NLPResult object
        NLPResult result = new NLPResult();
        result.setWordCount(wordCount);
        result.setSentenceCount(sentenceCount);
        result.setVerbCount(verbCount);
        result.setCommonNounCount(commonNounCount);
        result.setProperNounCount(properNounCount);
        result.setTypeTokenRatio(typeTokenRatio);

        return result;
    }
	
    public NLPResult readAggregatedResults() {
        NLPResult aggregatedResult = new NLPResult();
        try {
            List<String> lines = Files.readAllLines(Paths.get("D:/nlp_task_RA/aggregated_results.csv"));
            
            if (!lines.isEmpty()) {
                Map<String, String> resultMap = new HashMap<>();

                // Iterate over the data and populate the map
                for (int i = 0; i < lines.size(); i++) {
                	String[] data = lines.get(i).split(",");
                        resultMap.put(data[0].trim(), data[1].trim());
                        
                }
                
                // Parse and set values to aggregatedResult
                aggregatedResult.setWordCount(parseIntegerValue(resultMap, "word_count"));
                aggregatedResult.setSentenceCount(parseIntegerValue(resultMap, "sentence_count"));
                aggregatedResult.setVerbCount(parseIntegerValue(resultMap, "verb_count"));
                aggregatedResult.setCommonNounCount(parseIntegerValue(resultMap, "common_noun_count"));
                aggregatedResult.setProperNounCount(parseIntegerValue(resultMap, "proper_noun_count"));
                aggregatedResult.setTypeTokenRatio(parseDoubleValue(resultMap, "type_token_ratio"));
            }
        } catch (IOException e) {
            System.out.println("Error reading aggregated results.");
            e.printStackTrace();
        }
        return aggregatedResult;
    }

    private Integer parseIntegerValue(Map<String, String> resultMap, String key) {
        try {
            // Use Double.parseDouble to handle values that are not whole numbers
            return (int) Math.round(Double.parseDouble(resultMap.getOrDefault(key, "0")));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer for key: " + key);
            return 0;
        }
    }


    private Double parseDoubleValue(Map<String, String> resultMap, String key) {
        try {
            return Double.parseDouble(resultMap.getOrDefault(key, "0.0"));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing double for key: " + key);
            return 0.0;
        }
    }

}

