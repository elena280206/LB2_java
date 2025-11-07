import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Links {
    private String _value;
    private boolean _isValid;
    private static final Pattern LINK_PATTERN = Pattern.compile("^\\[([^\\]]+)]\\(([^)]+)\\)$");

    public static List<Links> collection = new ArrayList<>();

    public Links() {
        _value = "";
    }

    public Links(String str) {
        _value = str;
    }

    /**
     * Проверяет, что введённая строка полностью соответствует формату Markdown-ссылки.
     */
    public boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        Matcher mLink = LINK_PATTERN.matcher(input.trim());
        set_isValid(mLink.matches());
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

    public String get_value() { return _value; }

    public void set_value(String _value) {
        if (isValidInput(_value)) {this._value = _value;}
    }

    public boolean get_isValid() {
        return _isValid;
    }

    public void set_isValid(boolean _isValid) {
        this._isValid = _isValid;
    }
}