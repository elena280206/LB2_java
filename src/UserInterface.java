import java.util.Scanner;

/**
 * Класс {@code UserInterface} реализует консольный пользовательский интерфейс программы.
 * <p>
 * Позволяет пользователю вводить Markdown-ссылки и получать их преобразование в HTML.
 * Также отображает информацию о лабораторной работе.
 */
public class UserInterface {

    /**
     * Выводит информацию о лабораторной работе и авторах.
     */
    public static void printInfo() {
        System.out.println("Лабораторная работа №2. Выполнили студенты группы 24ВП1 Шадчина Е. С., Коноплева Н. Д.");
        System.out.println("Задание: Преобразовать Markdown-ссылки в HTML.");
    }

    /**
     * Осуществляет ввод Markdown-ссылок с консоли и вывод результата в HTML-формате.
     * <p>
     * Пользователь может завершить работу программы, введя символ {@code 0}.
     */
    public static void inputLink() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Введите ссылку в формате Markdown (например: [Google](https://google.com)):");
        System.out.println("Чтобы завершить выполнение программы, введите 0");

        while (true) {
            String str = reader.nextLine().trim();
            if (str.equals("0")) break;

            if (Links.checkInput(str)) {
                System.out.println("Неправильный формат ввода.");
            } else {
                String html = Links.MarkdownToHTML(str);
                System.out.println("\nРезультат в HTML:\n" + html);
            }

            System.out.println("\nВведите следующую ссылку или 0 для выхода:");
        }

        reader.close();
    }

    /**
     * Запускает выполнение программы:
     * <li>Выводит информацию о лабораторной работе;</li>
     * <li>Запускает процесс ввода и преобразования ссылок.</li>
     */
    public static void run() {
        printInfo();
        inputLink();
    }
}
