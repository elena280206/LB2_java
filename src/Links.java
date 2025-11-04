import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Links {
    private String _value;

    private static final Pattern LINK_PATTERN = Pattern.compile("^\\[([^\\]]+)]\\(([^)]+)\\)$");

    public Links() {
        _value = "[Google](https://google.com)";
    }

    public Links(String str) {
        _value = str;
    }

    /**
     * Проверяет, что введённая строка полностью соответствует формату Markdown-ссылки.
     */
    public static boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        Matcher mLink = LINK_PATTERN.matcher(input.trim());
        return mLink.matches();
    }

    /**
     * Конвертирует Markdown → HTML.
     */
    public static String MarkdownToHTML(String input) {
        if (input == null) return null;

        Matcher matcher = LINK_PATTERN.matcher(input.trim());
        if (matcher.matches()) {
            String text = matcher.group(1);
            String url = matcher.group(2).trim();

            if (url.toLowerCase().startsWith("javascript:")) {
                return input;
            }

            return "<a href=\"" + escapeHtmlAttr(url) + "\">" + escapeHtmlAttr(text) + "</a>";
        }
        return input;
    }

    private static String escapeHtmlAttr(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
