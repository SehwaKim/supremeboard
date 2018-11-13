package me.sehwa.supremeboard.dao;

final class CommentSQL {

    static final String selectOneById = "select id" +
        ", commenter" +
        ", content" +
        ", created_at" +
        ", updated_at" +
        ", board_id" +
        ", user_id" +
        ", parent_id" +
        ", family" +
        ", family_seq" +
        ", indent" +
        " from comment where id = :id";

    static final String selectAll = "select id" +
            ", commenter" +
            ", content" +
            ", created_at" +
            ", updated_at" +
            ", board_id" +
            ", user_id" +
            ", parent_id" +
            ", family" +
            ", family_seq" +
            ", indent" +
            " from comment where board_id = :boardId" +
            " order by family, family_seq";

    static final String updateContent = "update comment set content = :content, updated_at = CURRENT_TIMESTAMP where id = :id";

    static final String updateFamilySeq = "update comment set family_seq = family_seq + 1" +
            " where board_id = :boardId and family = :family and family_seq > :edgeOfFamilySeq";

    static final String updateFamily = "update comment set family = :id where id = :id";

    private CommentSQL() {}
}
