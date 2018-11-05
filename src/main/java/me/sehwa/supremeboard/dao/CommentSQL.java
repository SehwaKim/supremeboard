package me.sehwa.supremeboard.dao;

public class CommentSQL {

    public static String selectOneById = "select id" +
        ", commenter" +
        ", content" +
        ", created_at" +
        ", updated_at" +
        ", board_id" +
        ", user_id" +
        " from comment where id = :id";

    public static String updateComment = "update comment set comment = :comment where id = :id";

    private CommentSQL() {}
}
