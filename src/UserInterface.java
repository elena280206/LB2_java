import java.util.List;
import java.util.Scanner;

/**
 * Класс UserInterface отвечает за взаимодействие с пользователем через консоль.
 * Он обеспечивает:
 *  - вывод информации о лабораторной работе,
 *  - ввод Markdown-ссылок пользователем,
 *  - вывод обработанных ссылок в HTML,
 *  - показ всех сохранённых ссылок из коллекции.
 */
public class UserInterface {

    /**
     * Выводит информацию о лабораторной работе
     */
    public static void printInfo() {
        System.out.println("Лабораторная работа №2. Выполнили студенты группы 24ВП1 Шадчина Е. С., Коноплева Н. Д.");
        System.out.println("Задание: Преобразовать Markdown-ссылки в HTML.");
    }

    /**
     * Основной цикл ввода ссылок пользователем.
     * Пользователь может:
     *  - Ввести одну или несколько Markdown-ссылок, разделённых пробелом.
     *  - Ввести "0" для выхода из программы.
     *  - Ввести "1" для вывода всех ссылок из коллекции.
     */
    public static void inputLink() {
        Scanner reader = new Scanner(System.in); // Создаём сканер для чтения с клавиатуры
        System.out.println("Введите одну или несколько ссылок в формате Markdown, разделяя их пробелом:");
        System.out.println("Например: [Google](https://google.com) [YouTube](https://youtube.com)");
        System.out.println("Чтобы завершить выполнение программы, введите 0");
        System.out.println("Чтобы вывести все ссылки из коллекции, введите 1");

        while (true) {
            String str = reader.nextLine().trim(); // Считываем строку и удаляем лишние пробелы

            if (str.equals("0")) break; // Команда выхода

            if (str.equals("1")) { // Команда показать все ссылки
                showAllFromCollection();
                System.out.println("\nВведите следующую ссылку, 1 для вывода всех ссылок или 0 для выхода:");
                continue; // Переходим к следующей итерации цикла
            }

            if (str.isEmpty()) continue; // Игнорируем пустой ввод

            // Обработка введённых ссылок через Links.processInput
            List<String> results = Links.processInput(str);
            for (String res : results) {
                System.out.println(res); // Вывод результата обработки каждой ссылки
            }

            System.out.println("\nВведите следующую ссылку, 1 для вывода всех ссылок или 0 для выхода:");
        }

        reader.close(); // Закрываем сканер после завершения ввода
    }

    /**
     * Выводит все ссылки, сохранённые в коллекции класса Links в формате HTML.
     * Если коллекция пуста, выводит соответствующее сообщение.
     */
    public static void showAllFromCollection() {
        List<String> all = Links.getAllHTMLLinks(); // Получаем список HTML-ссылок
        if (all.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        for (String linkHTML : all) {
            System.out.println(linkHTML); // Печатаем каждую ссылку
        }
    }

    /**
     * Запускает пользовательский интерфейс.
     * Метод последовательно выводит информацию о лабораторной работе и запускает цикл ввода ссылок.
     */
    public static void run() {
        printInfo();    // Вывод информации
        inputLink();    // Запуск цикла ввода ссылок
    }
}
