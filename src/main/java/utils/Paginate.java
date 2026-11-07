package main.java.utils;

public class Paginate {
    private int currentPage;
    private int pageSize;
    private int offset;
    private int totalPages;
    private int totalItems;

    public Paginate(int currentPage, int pageSize, int totalItems) {
        // Initialize the pagination parameters.
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        // Calculate pagination parameters based on the provided values.
        calculatePagination();
    }

    /**
     * Sets the current page number and recalculates the pagination parameters.
     * @param currentPage The current page number (1-based index).
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        calculatePagination();
    }

    /**
     * Sets the page size (number of items per page) and recalculates the pagination parameters.
     * @param pageSize The number of items per page.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        calculatePagination();
    }

    /**
     * Sets the total number of items and recalculates the pagination parameters.
     * @param totalItems The total number of items available for pagination.
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        calculatePagination();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalItems () {
        return totalItems;
    }

    /**
     * Calculates the pagination parameters based on the current page, page size, and total items.
     */
    private void calculatePagination() {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero.");
        }

        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
        this.offset = (currentPage - 1) * pageSize;
    }
}
