package com.example.demo.controller;

import com.example.demo.service.NLPService;
import com.example.demo.model.NLPResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class NLPController {

    @Autowired
    private NLPService nlpService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    
    @PostMapping("/compare")
    public String compareResults(@RequestParam("file") MultipartFile file, 
            @RequestParam("text") String text, 
    		Model model) {
    	try {
            String content;
            if (!file.isEmpty()) {
                content = new String(file.getBytes());
            } else {
                content = text;
            }
            NLPResult result = nlpService.analyzeText(content);
            model.addAttribute("result", result);
            NLPResult aggregatedResult = nlpService.readAggregatedResults();
            model.addAttribute("aggregatedResult", aggregatedResult);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error processing the input");
            e.printStackTrace();
        }

        return "comparison";
    }
}
