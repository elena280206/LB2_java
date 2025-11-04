import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static List<Links> collection = new ArrayList<>();

    public static void printInfo() {
        System.out.println("Лабораторная работа №2. Выполнили студенты группы 24ВП1 Шадчина Е. С., Коноплева Н. Д.");
        System.out.println("Задание: Преобразовать Markdown-ссылки в HTML.");
    }

    public static void inputLink() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Введите одну или несколько ссылок в формате Markdown, разделяя их пробелом:");
        System.out.println("Например: [Google](https://google.com) [YouTube](https://youtube.com)");
        System.out.println("Чтобы завершить выполнение программы, введите 0");
        System.out.println("Чтобы вывести все ссылки из коллекции, введите 1");

        while (true) {
            String str = reader.nextLine().trim();
            if (str.equals("0")) break;

            if (str.equals("1")) {
                showAllFromCollection();
                System.out.println("\nВведите следующую ссылку, 1 для вывода всех ссылок или 0 для выхода:");
                continue;
            }

            if (str.isEmpty()) continue;

            String[] parts = str.split("\\s+");

            for (String part : parts) {
                if (!Links.isValidInput(part)) {
                    System.out.println("Неправильный формат ввода: " + part);
                    continue;
                }
                Links link = new Links(part);
                collection.add(link);
                String html = Links.MarkdownToHTML(part);
                System.out.println("Результат в HTML: " + html);
            }

            System.out.println("\nВведите следующую ссылку, 1 для вывода всех ссылок или 0 для выхода:");
        }

        reader.close();
    }

    public static void showAllFromCollection() {
        for (Links link : collection) {
            System.out.println(link.get_value());
        }
    }


    public static void run() {
        printInfo();
        inputLink();
    }
}
