package searchEngine;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightWordRenderer extends DefaultTableCellRenderer {
    private final String wordToHighlight;

    public HighlightWordRenderer(String wordToHighlight) {
        this.wordToHighlight = wordToHighlight;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Highlight the word within the cell text
        if (value != null && value instanceof String) {
            String cellText = (String) value;
            String highlightedText = highlightWord(cellText, wordToHighlight);
            setText(highlightedText);
        }

        return cellComponent;
    }

    private String highlightWord(String text, String word) {
        Pattern pattern = Pattern.compile("\\b" + word + "\\b");
        Matcher matcher = pattern.matcher(text);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "<html><span style='background-color: yellow;'>" + matcher.group(0) + "</span></html>");
        }
        matcher.appendTail(sb);

        return "<html>" + sb.toString() + "</html>";
    }
}