/* H2 DB 최초 테이블 생성 */
create table Member(
   id bigint not null,
   name varchar(255),
   primary key(id)
);