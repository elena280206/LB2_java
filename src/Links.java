import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс Links отвечает за всю работу с Markdown-ссылками.
 * Он инкапсулирует логику:
 * - хранение коллекции ссылок;
 * - проверка валидности ссылок;
 * - преобразование Markdown → HTML;
 * - добавление ссылок в коллекцию и получение их HTML-представления.
 */
public class Links {

    /** Markdown-ссылка как строка. */
    private String _value;

    /** Флаг валидности текущей ссылки. */
    private boolean _isValid;

    /** Регулярное выражение для проверки Markdown-ссылки*/
    private static final Pattern LINK_PATTERN = Pattern.compile("^\\[([^\\]]+)]\\(([^)]+)\\)$");

    /** Коллекция всех валидных ссылок. */
    private static final List<Links> collection = new ArrayList<>();

    /**
     * Приватный конструктор. Используется только внутри класса.
     * @param value Markdown-ссылка для создания объекта.
     */
    private Links(String value) {
        set_value(value);
    }

    /**
     * Устанавливает Markdown-значение ссылки и проверяет её валидность.
     * @param str Markdown-ссылка для установки.
     */
    private void set_value(String str) {
        this._value = (str != null) ? str.trim() : "";
        this._isValid = isValidInput(this._value);
    }

    /**
     * Возвращает исходное Markdown-значение ссылки.
     * @return Markdown-ссылка как строка.
     */
    public String get_value() {
        return _value;
    }

    /**
     * Проверяет, соответствует ли строка формату Markdown-ссылки [text](url).
     * @param input строка для проверки
     * @return true, если строка валидна, иначе false
     */
    private boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) return false;
        Matcher mLink = LINK_PATTERN.matcher(input.trim());
        return mLink.matches();
    }

    /**
     * Возвращает флаг валидности текущей ссылки.
     * @return true, если ссылка валидна, иначе false
     */
    public boolean get_isValid() {
        return _isValid;
    }

    /**
     * Преобразует Markdown-ссылку в HTML-формат.
     * Если ссылка невалидна, возвращает сообщение "Невалидная ссылка".
     * @return HTML-представление ссылки или сообщение об ошибке.
     */
    public String getHTML() {
        if (!_isValid) return "Невалидная ссылка";

        Matcher matcher = LINK_PATTERN.matcher(_value);
        if (matcher.matches()) {
            String text = matcher.group(1);
            String url = matcher.group(2).trim();

            // Защита от JavaScript-инъекций
            if (url.toLowerCase().startsWith("javascript:")) {
                return _value;
            }

            return "<a href=\"" + escapeHtmlAttr(url) + "\">" + escapeHtmlAttr(text) + "</a>";
        }
        return _value;
    }

    /**
     * Экранирует специальные HTML-символы для безопасного вывода.
     * @param s входная строка
     * @return строка с экранированными символами
     */
    private static String escapeHtmlAttr(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    /**
     * Создаёт объект Links, проверяет валидность и добавляет его в коллекцию.
     * Возвращает HTML-представление ссылки или сообщение об ошибке.
     * @param markdownLink строка Markdown-ссылки для добавления
     * @return HTML-представление или сообщение об ошибке
     */
    public static String addLink(String markdownLink) {
        Links link = new Links(markdownLink);
        if (link.get_isValid()) {
            collection.add(link);
            return link.getHTML();
        } else {
            return "Неправильный формат ввода";
        }
    }

    /**
     * Обрабатывает строку, которая может содержать несколько Markdown-ссылок,
     * разделённых пробелами. Для каждой ссылки возвращает HTML или сообщение об ошибке.
     * @param input строка с одной или несколькими Markdown-ссылками
     * @return список строк с результатами обработки каждой ссылки
     */
    public static List<String> processInput(String input) {
        List<String> results = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return results;

        String[] parts = input.trim().split("\\s+");
        for (String part : parts) {
            results.add(addLink(part));
        }
        return results;
    }

    /**
     * Возвращает все ссылки, сохранённые в коллекции, в виде HTML-строк.
     * @return список HTML-ссылок
     */
    public static List<String> getAllHTMLLinks() {
        List<String> htmlList = new ArrayList<>();
        for (Links link : collection) {
            htmlList.add(link.getHTML());
        }
        return htmlList;
    }
}
