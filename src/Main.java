import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int goodEntryCounter = 0;
        LogEntry logEntry;
        Statistics statistics = new Statistics();
        for (int i = 1; i != -1; i++) {
            System.out.println("Введите путь к файлу и нажмите <Enter>: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();
            int totalLines = 0;
            int yandexBot = 0;
            int googleBot = 0;
            if (fileExist && !isDirectory) {
                System.out.println("Путь указан верно");
                goodEntryCounter++;
                System.out.println("Это файл номер " + goodEntryCounter);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        totalLines++;
                        String result = "";
                        int start = line.indexOf("(") + 1;
                        int end = line.lastIndexOf(")");
                        if (start > 0 && end > start) {
                            result = line.substring(start, end);
                            if (result.contains("Googlebot")) {
                                googleBot++;

                            } else if (result.contains("YandexBot")) {
                                yandexBot++;

                            }
                        }
                        if (length > 1024) {
                            throw new exceededLineLengthException();
                        }
                        logEntry = new LogEntry(line);
                        statistics.addEntry(logEntry);
                    }
                    System.out.println(statistics);
                    System.out.println("Файл содержит " + totalLines + " строк");
                    System.out.println("Запросов от YandexBot: " + yandexBot);
                    System.out.println("Запросов от Googlebot: " + googleBot);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                continue;
            }
            if (isDirectory) {
                System.out.println("Указанный путь - директория");
                continue;
            }
            System.out.println("Указанный файл не существует");
        }
    }
}
