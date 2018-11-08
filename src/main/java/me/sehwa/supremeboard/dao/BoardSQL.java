package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.BoardSearchType;

final class BoardSQL {
    static final String selectOneById = "select b.id" +
            "      , title" +
            "      , writer" +
            "      , content" +
            "      , hit" +
            "      , comment_cnt" +
            "      , b.created_at" +
            "      , b.updated_at" +
            "      , user_id" +
            "      , category_id" +
            " from board b left outer join board_content bc on b.id = bc.board_id where b.id = :id";

    static String updateTitle = "update board set title = :title, updated_at = CURRENT_TIMESTAMP where id = :id";

    static String updateHit = "update board set hit = hit + 1, updated_at = CURRENT_TIMESTAMP where id = :id";

    static String updateCommentCnt = "update board set comment_cnt = comment_cnt + 1" +
            ", updated_at = CURRENT_TIMESTAMP where id = :id";

    private BoardSQL(){}

    static String createSelectAllSQL(String[] searchType) throws IllegalArgumentException {
        // case: 이름, 제목, 내용, 제목+내용

        StringBuilder sb = new StringBuilder("select b.id\n");
        sb.append(", title\n");
        sb.append(", writer\n");
        if (isContentSearched(searchType)) { // case: 내용, 제목+내용
            sb.append(", content\n");
        }
        sb.append(", hit\n");
        sb.append(", comment_cnt\n");
        sb.append(", b.created_at\n");
        sb.append(", b.updated_at\n");
        sb.append(", user_id\n");
        sb.append(", category_id\n");
        sb.append("from board b\n");
        if (isContentSearched(searchType)) { // case: 내용, 제목+내용
            sb.append("left outer join board_content bc on b.id = bc.board_id\n");
        }
        sb.append("where category_id = :categoryId\n");

        if (isSearched(searchType)) {
            sb.append("and (");
            for (int i = 0; i < searchType.length; i++) {
                if (isValidSearchType(searchType[i])) {
                    if (i > 0) {
                        sb.append(" or ");
                    }
                    sb.append(searchType[i]);
                    sb.append(" like CONCAT('%', :searchStr, '%')\n");
                }
            }
            sb.append(")");
        }
        sb.append("order by updated_at desc");
//        sb.append("limit :startIdx, :postSize");
        return sb.toString();
    }

    private static boolean isSearched(String[] searchType) {
        return searchType != null && searchType.length > 0;
    }

    private static boolean isContentSearched(String[] searchType) throws IllegalArgumentException {
        if (!isSearched(searchType)) {
            return false;
        }
        boolean isContentSearched = false;
        for (String type : searchType) {
            if (BoardSearchType.valueOf(type.toUpperCase()) == BoardSearchType.CONTENT) {
                isContentSearched = true;
                break;
            }
        }
        return isContentSearched;
    }

    private static boolean isValidSearchType(String searchType) throws IllegalArgumentException {
        BoardSearchType.valueOf(searchType.toUpperCase());
        return true;
    }
}