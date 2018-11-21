package me.sehwa.supremeboard.util;

public class Pagination {
    private final int postSize = 3;
    private int pageSize;
    private int startNum;
    private int page;

    public Pagination() {
        this(1);
    }

    public Pagination(int page) {
        this.page = page;
        this.startNum = postSize * (page - 1);
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

    public int getPage() {
        return page;
    }
}
