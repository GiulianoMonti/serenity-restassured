package com.claro.automation.planmgmt.jiralog;


import com.claro.automation.jirareport.logger.LogJlr;

import java.io.FileNotFoundException;

public class App {

    public static void main(String[] args) {
        System.out.println("-------------------------");
        System.out.println("CONSOLE TEST RESULT");
        System.out.println("-------------------------");
        String filePath = "target/site/serenity/results.csv";
        try {
            new LogJlr().displayReport(filePath);
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Not found results.csv");
        }

    }
}
