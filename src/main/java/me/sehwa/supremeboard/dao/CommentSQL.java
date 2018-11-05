package me.sehwa.supremeboard.dao;

class CommentSQL {

    static final String selectOneById = "select id" +
        ", commenter" +
        ", content" +
        ", created_at" +
        ", updated_at" +
        ", board_id" +
        ", user_id" +
        " from comment where id = :id";

    static final String selectAll = "select id" +
            ", commenter" +
            ", content" +
            ", created_at" +
            ", updated_at" +
            ", board_id" +
            ", user_id" +
            " from comment";

    static final String updateContent = "update comment set content = :content where id = :id";

    private CommentSQL() {}
}
