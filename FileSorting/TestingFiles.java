package com.sadds.PracticeProblems.FileSorting;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestingFiles {

    private static String path = "C:\\Users\\Devan\\Multi-Threading\\practice\\src\\main\\java\\com\\sadds\\PracticeProblems\\FileSorting\\resource";
    private static String filename = "test";
    private static String extension = ".txt";

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String fileName = "test0.txt";

//        createNewFiles();
//        writeSampleData();

//        readMultipleFilesConcurrently();
        readSingleFileConcurently(fileName);

//        seperateLogLevels(fileName);

    }

    public static void readSingleFileConcurently(String filename) throws IOException, ExecutionException, InterruptedException {

        File outputDir = new File(path + "\\" + "Output_Single_File_Logs");
        System.out.println("Directory made : " + outputDir.mkdirs());

        File readerFile = new File(path + "\\" + filename);
        Integer chunkSize = 1024*512;

        List<FileChunk> fileChunks = splitIntoFileChunks(readerFile, chunkSize);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();
        for (FileChunk fileChunk : fileChunks) {
            futures.add(executor.submit(() -> processChunk(readerFile, fileChunk)));
        }

        for (Future<Void> future : futures) {
            future.get();
        }

        executor.shutdown();
    }

    private static Void processChunk(File readerFile, FileChunk fileChunk) throws IOException {
        Map<String, BufferedWriter> writers = createWriters();

        try (RandomAccessFile inputFile = new RandomAccessFile(readerFile, "r")) {
            inputFile.seek(fileChunk.start);
            String line;

            while (inputFile.getFilePointer() <= fileChunk.end && (line = inputFile.readLine()) != null) {
                Pattern pattern = Pattern.compile("(INFO|DEBUG|WARN|ERROR)");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    BufferedWriter writer = writers.get(matcher.group());
                    writer.write(line + "\n");
                }
            }
            closeWriters(writers);
        }
        return null;
    }

    private static void closeWriters(Map<String, BufferedWriter> writers) throws IOException {
        for (BufferedWriter writer : writers.values()) {
            writer.close();
        }
    }

    private static Map<String, BufferedWriter> createWriters() throws IOException {
        Map<String, BufferedWriter> writers = new HashMap<>();
        String writerPath = path + "\\" + "Output_Single_File_Logs" + "\\";

        writers.put("INFO", new BufferedWriter(new FileWriter(writerPath + "info.log", true)));
        writers.put("DEBUG", new BufferedWriter(new FileWriter(writerPath + "debug.log", true)));
        writers.put("WARN", new BufferedWriter(new FileWriter(writerPath + "warn.log", true)));
        writers.put("ERROR", new BufferedWriter(new FileWriter(writerPath + "error.log", true)));

        return writers;
    }

    private static List<FileChunk> splitIntoFileChunks(File readerFile, Integer chunkSize) {
        List<FileChunk> chunks = new ArrayList<FileChunk>();

        try (RandomAccessFile file = new RandomAccessFile(readerFile, "r")) {
            long fileLength = file.length();
            long offset = 0;

            while (offset < fileLength) {
                long end = Math.min(fileLength, offset + chunkSize);
                file.seek(end);

                while (end < fileLength && (file.read() != '\n')) {
                    end++;
                }

                chunks.add(new FileChunk(offset, end));
                offset = end + 1;
            }
            return chunks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void readMultipleFilesConcurrently() throws IOException {
        String[] allFiles = {"test0.txt", "test1.txt", "test2.txt", "test3.txt", "test4.txt"};
        for (String file : allFiles) {
            Thread thread = new Thread(new FileRearrange(file));
            thread.start();
        }
    }

    public static void seperateLogLevels(String filename) throws IOException {

        String fullpath = path + "\\" + filename;
        BufferedReader br = new BufferedReader(new FileReader(fullpath));
        String line = "";
        Pattern pattern = Pattern.compile("(INFO|DEBUG|WARN|ERROR)");

        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String level = matcher.group();
                String writepath = path + "\\" + level + ".txt";

                File file = new File(writepath);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(writepath, true))) {
                    writer.write(line + "\n");
                }
            }
        }
        System.out.println("Successfully sepereated file " + filename);
    }

    public static void writeSampleData() throws IOException {

        String[] loglevels = {"INFO", "DEBUG", "WARN", "ERROR"};
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            String fullPath = path + "\\" + filename + String.valueOf(i) + extension;
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath));

            for (int j = 0; j < 1_00_000; j++) {
                String timeStamp = "13-01-2025T " +
                        random.nextInt(1, 25) + ":" +
                        random.nextInt(1, 61) + ":" +
                        random.nextInt(1, 61);
                String loglevel = loglevels[random.nextInt(loglevels.length)];
                String message = "Log message " + String.valueOf(j);

                writer.write(timeStamp + " " + loglevel + " " + message + "\n");

            }
            System.out.println("Sample file created : " + fullPath);

        }

    }

    public static void createNewFiles() throws IOException {

        for (int i = 0; i < 5; i++) {

            String fullPath = path + "\\" + filename + String.valueOf(i) + extension;
            File file = new File(fullPath);

            if(file.createNewFile()) {
                System.out.println("File created: " + fullPath);
                System.out.println("File space: " + file.getTotalSpace());
                System.out.println("File usage space: " + file.getUsableSpace());
                System.out.println();
            } else {
                System.out.println("File already exists: " + fullPath);
            }
        }
    }
}

class FileChunk {
    long start;
    long end;

    public FileChunk(long start, long end) {
        this.start = start;
        this.end = end;
    }
}

class FileRearrange implements Runnable {

    private String readerFilePath;
    private Lock lock = new ReentrantLock();
    private static String path = "C:\\Users\\Devan\\Multi-Threading\\practice\\src\\main\\java\\com\\sadds\\PracticeProblems\\FileSorting\\resource";

    public FileRearrange(String file) {
        readerFilePath = path + "\\" + file;
    }

    @Override
    public void run() {

        Pattern pattern = Pattern.compile("(INFO|DEBUG|WARN|ERROR)");
        String line = "";
        String loglevel = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(readerFilePath));
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    loglevel = matcher.group();
                } else {
                    System.out.println("Logging level error : " + line);
                }
                String writerPath = path + "\\" + loglevel + ".txt";

                lock.lock();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(writerPath, true))) {
                    writer.write(line + "\n");
                } finally {
                    System.out.println("File sorting " + readerFilePath);
                    lock.unlock();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
