package me.sehwa.supremeboard.dao;

final class UserSQL {
    static final  String selectOneById =
            "SELECT id, email, name, password, created_at, updated_at, last_access_date, status" +
            " FROM user" +
            " WHERE id = :id and status = 'ENABLED'";

    static final String selectOneByEmail =
            "SELECT id, email, name, password, created_at, updated_at, last_access_date, status" +
                    " FROM user" +
                    " WHERE email = :email and status = 'ENABLED'";

    static final String update =
            "update user set" +
                    " email = :email" +
                    ", password = :password" +
                    ", name = :name" +
                    ", updated_at = CURRENT_TIMESTAMP" +
                    ", status = :status" +
                    " where id = :id";

    private UserSQL() {}
}
