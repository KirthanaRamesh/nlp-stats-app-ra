package com.example.demo.model;

import java.text.DecimalFormat;

public class NLPResult {
    private int wordCount;
    private int sentenceCount;
    private int verbCount;
    private int commonNounCount;
    private int properNounCount;
    private double typeTokenRatio;

    // Constructor
    public NLPResult() {
    }

    // Getters and Setters
    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(int sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public int getVerbCount() {
        return verbCount;
    }

    public void setVerbCount(int verbCount) {
        this.verbCount = verbCount;
    }
    
    public int getCommonNounCount() {
        return commonNounCount;
    }

    public void setCommonNounCount(int commonNounCount) {
        this.commonNounCount = commonNounCount;
    }
    
    public int getProperNounCount() {
        return properNounCount;
    }

    public void setProperNounCount(int properNounCount) {
        this.properNounCount = properNounCount;
    }
    
    public double getTypeTokenRatio() {
        return typeTokenRatio;
    }

    public void setTypeTokenRatio(double typeTokenRatio) {
    	 DecimalFormat df = new DecimalFormat("#.###"); // Format to 3 decimal places
    	 this.typeTokenRatio = Double.parseDouble(df.format(typeTokenRatio));
    }
    

    @Override
    public String toString() {
        return "NLPResult{" +
                "wordCount=" + wordCount +
                ", sentenceCount=" + sentenceCount +
                ", verbCount=" + verbCount +
                ", commonNounCount=" + commonNounCount +
                ", properNounCount=" + properNounCount +
                ", typeTokenRatio=" + typeTokenRatio +
                '}';
    }
}
