import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 读取文件内容
        List<String> file1Words = readFile("1.txt");
        List<String> file2Words = readFile("2.txt");

        // 计算词频向量
        Map<String, Integer> file1WordFreq = calculateWordFrequency(file1Words);
        Map<String, Integer> file2WordFreq = calculateWordFrequency(file2Words);

        // 计算余弦相似度
        double cosineSimilarity = calculateCosineSimilarity(file1WordFreq, file2WordFreq);

        // 将结果写入文件
        writeResultToFile(cosineSimilarity, "result.txt");
    }

    // 读取文件内容并返回单词列表
    public static List<String> readFile(String filename) {
        List<String> words = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                words.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    // 计算词频向量
    public static Map<String, Integer> calculateWordFrequency(List<String> words) {
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }
        return wordFreq;
    }

    // 计算余弦相似度
    public static double calculateCosineSimilarity(Map<String, Integer> wordFreq1, Map<String, Integer> wordFreq2) {
        Set<String> allWords = new HashSet<>(wordFreq1.keySet());
        allWords.addAll(wordFreq2.keySet());

        int dotProduct = 0;
        int norm1 = 0;
        int norm2 = 0;

        for (String word : allWords) {
            int freq1 = wordFreq1.getOrDefault(word, 0);
            int freq2 = wordFreq2.getOrDefault(word, 0);

            dotProduct += freq1 * freq2;
            norm1 += freq1 * freq1;
            norm2 += freq2 * freq2;
        }

        double cosineSimilarity;
        if (norm1 == 0 || norm2 == 0) {
            cosineSimilarity = 0;
        } else {
            cosineSimilarity = dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        return cosineSimilarity;
    }

    // 将结果写入文件
    public static void writeResultToFile(double cosineSimilarity, String filename) {
        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            writer.write("Cosine Similarity: " + cosineSimilarity);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
