package com.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main1 {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("src/main/java/com/example/Main.java");
            CompilationUnit cu = new JavaParser().parse(file).getResult().orElseThrow(() -> new RuntimeException("Unable to parse the file"));
            // Get token counts
            Map<String, Integer> tokenCounts = getTokenCounts(cu);
            System.out.println("\nToken Counts:");
            tokenCounts.forEach((token, count) -> System.out.println(token + ": " + count));

            // Calculate total token count
            int totalTokens = getTotalTokenCount(tokenCounts);
            System.out.println("\nTotal Tokens: " + totalTokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nThe AST is below: \n");
            // Print AST recursively
           try {
            CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/com/example/Main.java"));
            // Now you can explore the AST!
            printAST(cu, "", "  ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private static void printAST(Node node, String prefix, String childrenPrefix) {
    System.out.println(prefix + node.getClass().getSimpleName() + ": " + node.toString().substring(0, Math.min(node.toString().length(), 50)) + "...");
    for (Node child : node.getChildNodes()) {
        printAST(child, childrenPrefix + "├── ", childrenPrefix + "│   ");
    }
}
    // Function to get token counts
    private static Map<String, Integer> getTokenCounts(CompilationUnit cu) {
   	Map<String, Integer> tokenCounts = new HashMap<>();
   	cu.getTokenRange().ifPresent(range -> range.forEach(token -> {
		String tokenType = token.getCategory().name();
        	tokenCounts.put(tokenType, tokenCounts.getOrDefault(tokenType, 0) + 1);
    	}));
    	return tokenCounts;
    } 

    // Function to calculate total token count
    private static int getTotalTokenCount(Map<String, Integer> tokenCounts) {
        return tokenCounts.values().stream().mapToInt(Integer::intValue).sum();
    }
}
    

