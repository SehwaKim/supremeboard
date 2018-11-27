package me.sehwa.supremeboard.util;

public class Pagination {
    private int postSize;
    private int pageSize;
    private int startNum;
    private int currentPage;
    private int totalPostSize;
    private int startPage;
    private int endPage;
    private int totalPage;

    public Pagination(int totalPostSize, int postSize, int pageSize) {
        this(1, totalPostSize, postSize, pageSize);
    }

    public Pagination(int currentPage, int totalPostSize, int postSize, int pageSize) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
        this.totalPostSize = totalPostSize;
        this.postSize = postSize;
        this.pageSize = pageSize;
        this.startNum = postSize * (currentPage - 1);
        // limit :startNum, :postSize
        // 페이지에 따라 변하는 것은 startNum 이다.
        init();
    }

    private void init() {
        startPage = 0;
        endPage = 0;
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

    public boolean hasPrev() {
        return startPage > 1;
    }

    public boolean hasNext() {
        return endPage < totalPage;
    }
}
