package main.java.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class TablePrinter<T> {
    public enum Align { LEFT, RIGHT }

    public record Column<T>(String header, Function<T, ?> extractor, int maxWidth, Align align) {}

    private static final int DEFAULT_MAX_WIDTH = 24;

    private final List<Column<T>> columns = new ArrayList<>();
    private String emptyMessage = "No results found.";

    private Paginate paginate = null;

    public static <T> TablePrinter<T> create() {
        return new TablePrinter<>();
    }

    public TablePrinter<T> column(String header, Function<T, ?> extractor) {
        return column(header, extractor, DEFAULT_MAX_WIDTH, Align.LEFT);
    }

    public TablePrinter<T> column(String header, Function<T, ?> extractor, Align align) {
        return column(header, extractor, DEFAULT_MAX_WIDTH, align);
    }

    public TablePrinter<T> column(String header, Function<T, ?> extractor, int maxWidth,
                                  Align align) {
        columns.add(new Column<>(header, extractor, maxWidth, align));
        return this;
    }

    public TablePrinter<T> paginate(Paginate paginate) {
        this.paginate = paginate;
        return this;
    }

    public TablePrinter<T> emptyMessage(String message) {
        this.emptyMessage = message;
        return this;
    }

    public void print(Collection<T> rows) {
        if (rows == null || rows.isEmpty()) {
            ScreenUtils.printUI(emptyMessage);
            return;
        }

        List<String[]> cells = rows.stream().map(this::toCells).toList();
        int[] widths = computeWidths(cells);

        ScreenUtils.printUI(formatRow(headers(), widths));
        ScreenUtils.printUI(separator(widths));
        cells.forEach(row -> ScreenUtils.printUI(formatRow(row, widths)));
        ScreenUtils.printUI(rows.size() + (rows.size() == 1 ? " row" : " rows"));
        if (paginate != null) {
            int totalItems = paginate.getTotalItems();
            ScreenUtils.printUI("Page %d of %d of %d %s".formatted(
                    paginate.getCurrentPage(),
                    paginate.getTotalPages(),
                    totalItems,
                    totalItems == 1 ? "item" : "items"
            ));
        }
    }

    private String[] headers() {
        return columns.stream().map(Column::header).toArray(String[]::new);
    }

    private String[] toCells(T row) {
        return columns.stream()
                .map(c -> truncate(String.valueOf(valueOf(c, row)), c.maxWidth()))
                .toArray(String[]::new);
    }

    private Object valueOf(Column<T> column, T row) {
        var value = column.extractor().apply(row);
        return value == null ? "-" : value;
    }

    private int[] computeWidths(List<String[]> cells) {
        int[] widths = new int[columns.size()];
        for (int i = 0; i < widths.length; i++) {
            widths[i] = columns.get(i).header().length();
            for (String[] row : cells) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }
        return widths;
    }

    private String formatRow(String[] values, int[] widths) {
        var sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) sb.append(" │ ");
            var pattern = columns.get(i).align() == Align.RIGHT ? "%" + widths[i] + "s"
                    : "%-" + widths[i] + "s";
            sb.append(pattern.formatted(values[i]));
        }
        return sb.toString();
    }

    private String separator(int[] widths) {
        var sb = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            if (i > 0) sb.append("─┼─");
            sb.append("─".repeat(widths[i]));
        }
        return sb.toString();
    }


    private static String truncate(String value, int maxWidth) {
        return value.length() <= maxWidth ? value : value.substring(0, maxWidth - 1) + "…";
    }
}
