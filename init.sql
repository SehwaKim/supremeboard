insert into user (email, password, name, status) VALUES ('pooh@yellow.bear','1234','pooh','ENABLED');
insert into role (name) values ('ADMIN');
insert into role (name) values ('USER');
insert into category (name) values ('Common');
insert into board (id, title, writer, user_id, category_id, family) values (1, '와', 'pooh', (select id from user where name = 'pooh'), 1, 1);
insert into board_content (content, board_id) values ('이거 게시판 누가만들었어 쩐다', 1);
