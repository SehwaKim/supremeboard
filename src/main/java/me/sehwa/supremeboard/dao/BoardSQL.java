package me.sehwa.supremeboard.dao;

class BoardSQL {
    static final String selectOneById = "select id" +
            "      , title" +
            "      , writer" +
            "      , hit" +
            "      , comment_cnt" +
            "      , created_at" +
            "      , updated_at" +
            "      , user_id" +
            "      , category_id" +
            " from board where id = :id";

    public static String updateTitle = "update board set title = :title where id = :id";

    public static String updateHit = "update board set hit = hit + 1 where id = :id";

    public static String updateCommentCnt = "update board set comment_cnt = comment_cnt + 1 where id = :id";

    private BoardSQL(){}
}