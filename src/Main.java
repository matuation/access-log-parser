import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int goodEntryCounter = 0;
        for (int i = 1; i != -1; i++) {
            System.out.println("Введите путь к файлу и нажмите <Enter>: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();
            int totalLines = 0;
            int longestLine = 0;
            int shortestLine = 1024;
            if (fileExist && !isDirectory) {
                System.out.println("Путь указан верно");
                goodEntryCounter++;
                System.out.println("Это файл номер " + goodEntryCounter);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader =
                            new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        totalLines++;
                        if (length > longestLine) {
                            longestLine = length;
                            shortestLine = longestLine;
                        }
                        if (length < shortestLine) {
                            shortestLine = length;
                        }
                        if (length > 1024) {
                            throw new exceededLineLengthException();
                        }
                    }
                    System.out.println("Файл содержит " + totalLines + " строк");
                    System.out.println("Символов в самой длинной строке: " + longestLine);
                    System.out.println("Символов в самой короткой строке: " + shortestLine);
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