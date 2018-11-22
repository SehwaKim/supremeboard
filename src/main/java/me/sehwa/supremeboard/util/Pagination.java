package me.sehwa.supremeboard.util;

public class Pagination {
    private final int postSize = 3;
    private int pageSize;
    private int startNum;
    private int currentPage;
    private int startPage;
    private int endPage;

    public Pagination() {
        this(1);
    }

    public Pagination(int currentPage) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
        this.startNum = postSize * (currentPage - 1);
    }

    public boolean hasPrev() {
        return true;
    }

    public boolean hasNext() {
        return true;
    }

    public int getPostSize() {
        return postSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartNum() {
        return startNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }
}
