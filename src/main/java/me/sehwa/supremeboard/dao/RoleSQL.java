package me.sehwa.supremeboard.dao;

final class RoleSQL {

    static final String selectRoleIdByName = "select id from role where name = :name";

    static final String selectAllByUserId = "select user_id, role_id, name\n" +
            "from user_role ur inner join role r on ur.role_id = r.id\n" +
            "where user_id = :userId";

    private RoleSQL(){}
}
