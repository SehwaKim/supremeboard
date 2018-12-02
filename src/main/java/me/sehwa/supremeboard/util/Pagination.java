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
    private int startIdx; // 페이지 당 시작 게시물 인덱스
    private int endIdx; // 페이지 당 마지막 게시물 인덱스

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
        this.totalPostSize = (int) Math.ceil(totalPostSize*1.0/postSize);
        this.startPage = (currentPage/(pageSize+1))*pageSize+1;
        this.endPage = startPage+pageSize-1;
        if(endPage > totalPostSize){
            endPage = totalPostSize;
        }
        this.startIdx = (this.currentPage-1)*postSize;
        this.endIdx = startIdx + postSize-1;
        if(endIdx >= totalPostSize){
            endIdx = totalPostSize-1;
        }
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
