package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.BoardSearchType;

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

    public static String createSelectListSQL(String[] searchType) throws IllegalArgumentException {
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