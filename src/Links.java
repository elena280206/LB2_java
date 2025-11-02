import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ссылок, преобразующий Markdown -> HTML.
 * Преобразует ссылки формата [текст](url) в <a href="url">текст</a>,
 */
public class Links {

    //private String _value;
    //public HashMap<String, String> collection = new HashMap<>();

    private static final Pattern LINK_PATTERN = Pattern.compile("(?<!\\!)\\[([^\\]]+)\\]\\(([^)]+)\\)");
    private static final Pattern IMAGE_PATTERN = Pattern.compile("^!\\[[^\\]]*\\]\\([^\\)]+\\)$");


    /**
     * Проверяет формат ввода.
     * Возвращает true — если формат некорректен, иначе false.
     */
    public static boolean checkInput(String input) {
        if (input == null) return true;
        String trimmed = input.trim();
        if (trimmed.isEmpty()) return true;

        Matcher mImage = IMAGE_PATTERN.matcher(trimmed);
        if (mImage.matches()) {
            return false;
        }

        Matcher mLink = LINK_PATTERN.matcher(trimmed);
        if (mLink.find()) {
            String url = mLink.group(2).trim().toLowerCase();

            return url.startsWith("javascript:");
        }

        return true;
    }

    /**
     * Конвертирует Markdown-ссылки в HTML.
     */
    public static String MarkdownToHTML(String input) {
        if (input == null) return null;

        StringBuilder sb = new StringBuilder();
        Matcher matcher = LINK_PATTERN.matcher(input);

        while (matcher.find()) {
            String text = matcher.group(1);
            String url = matcher.group(2).trim();

            if (url.toLowerCase().startsWith("javascript:")) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group(0)));
                continue;
            }

            String replacement = "<a href=\"" + escapeHtmlAttr(url) + "\">" + escapeHtmlAttr(text) + "</a>";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Экранирует специальные HTML-символы в тексте.
     * @param s исходная строка.
     * @return экранированная строка (никогда не {@code null}).
     */
    private static String escapeHtmlAttr(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}


//поля класса добавить
//конструкторы добавить