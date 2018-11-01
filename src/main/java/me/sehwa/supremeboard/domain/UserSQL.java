package me.sehwa.supremeboard.domain;

public final class UserSQL {
    public static final  String selectOneById =
            "SELECT id, email, name, password, created_at, updated_at, last_access_date, status" +
            " FROM user" +
            " WHERE id = :id and status = 'ENABLED'";

    public static final String selectOneByEmail =
            "SELECT id, email, name, password, created_at, updated_at, last_access_date, status" +
                    " FROM user" +
                    " WHERE email = :email and status = 'ENABLED'";

    public static final String update =
            "update user set" +
                    " email = :email" +
                    ", password = :password" +
                    ", name = :name" +
                    ", updated_at = CURRENT_TIMESTAMP" +
                    ", status = :status" +
                    " where id = :id";

    private UserSQL() {}
}
