package me.sehwa.supremeboard.dao;

final class FileInfoSQL {

    static final String selectOneById = "select id" +
            ", board_id" +
            ", name" +
            ", path" +
            ", size" +
            ", type" +
            ", created_at" +
            ", deleted" +
            " from file_info where id = :id and deleted = 'n'";

    static final String selectAllByBoardId = "select id" +
            ", board_id" +
            ", name" +
            ", path" +
            ", size" +
            ", type" +
            ", created_at" +
            ", deleted" +
            " from file_info where board_id = :boardId and deleted = 'n'";

    static final String updateDeleted = "update file_info set deleted = 'y' where id = :id";

    private FileInfoSQL(){}
}
