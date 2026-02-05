import java.io.File;
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
            if (fileExist && !isDirectory) {
                System.out.println("Путь указан верно");
                goodEntryCounter++;
                System.out.println("Это файл номер " + goodEntryCounter);
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