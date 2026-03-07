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
            double yandexPart = 0.0;
            double googlePart = 0.0;
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
                        statistics.getExistingPages();
                    }
                    yandexPart = Math.round(((double)yandexBot / totalLines) * 100.0) / 100.0;
                    googlePart = Math.round(((double)googleBot / totalLines) * 100.0) / 100.0;
                    System.out.println(statistics);
                    System.out.println("Файл содержит " + totalLines + " строк");
                    System.out.println("Доля запросов от YandexBot: " + yandexPart);
                    System.out.println("Доля запросов от Googlebot: " + googlePart);
                    System.out.println(statistics.getExistingPages().size());
                    System.out.println(statistics.getOpSysAmountStatistics());


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
//C:\Users\supercomp_123456\Documents\reclass\access.log