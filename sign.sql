/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/4/17 15:32:41                           */
/*==============================================================*/

create database weixin;

use weixin;

drop table if exists tb_class;

drop table if exists tb_instrutor;

drop table if exists tb_leave;

drop table if exists tb_reason;

drop table if exists tb_sign_in;

drop table if exists tb_student;

drop table if exists tb_student_info;

/*==============================================================*/
/* Table: tb_class                                              */
/*==============================================================*/
create table tb_class
(
   id                   int primary key auto_increment,
   num                  varchar(255) unique,
   instructor_id        int 
);

alter table tb_class comment '�༶��';

/*==============================================================*/
/* Table: tb_instrutor                                          */
/*==============================================================*/
create table tb_instructor
(
   id                   int primary key auto_increment,
   name                 varchar(30) not null,
   psd                  varchar(30) not null,
   openId               varchar(30) not null
);

alter table tb_instrutor comment '����Ա��';

/*==============================================================*/
/* Table: tb_leave                                              */
/*==============================================================*/
create table tb_leave
(
   id                   int primary key auto_increment,
   account              varchar(30) not null,
   name                 varchar(30) not null,
   startTime            varchar(30) not null,
   endTime              varchar(30) not null,
   reasonDetail         text,
   instructor_name      varchar(30)
);

alter table tb_leave comment '��ٵı�';

/*==============================================================*/
/* Table: tb_reason                                             */
/*==============================================================*/
create table tb_reason
(
   id                   int not null auto_increment,
   content              text not null,
   primary key (id)
);

/*==============================================================*/
/* Table: tb_sign_in                                            */
/*==============================================================*/
 #accAndTime ѧ�ż�ʱ�䣬ÿ��ǩ��һ��
create table tb_sign_in
(
   id                   int primary key auto_increment,
   account              varchar(30) not null,
   name                 varchar(30) not null,
   time                 varchar(30) not null,
   instructor_name      varchar(30) not null,
   accAndTime           varchar(30) not null unique,
   core                 int
);

alter table tb_sign_in comment 'ǩ����';

/*==============================================================*/
/* Table: tb_student                                            */
/*==============================================================*/
create table tb_student
(
   id                   int primary key auto_increment,
   account              varchar(30) not null,
   name                 varchar(30) not null,
   openId               varchar(60) not null,
   class_id             int references tb_class(id),
   instructor_id        int 
);

alter table tb_student comment 'ѧ����';

/*==============================================================*/
/* Table: tb_student_info                                       */
/*==============================================================*/
create table tb_student_info
(
   id                   int not null auto_increment,
   account              varchar(30) not null,
   name                 varchar(30) not null,
   identity             varchar(60) not null,
   primary key (id)
);



#����ԤԼ�յ�����
create table tb_condition
(
id int primary key auto_increment,
floor varchar(30) not null,
dormitory varchar(30) not null,
dorAndFlo varchar(30) not null unique, #���Һ�¥����һ�޶�
personCount int not null,
contacts varchar(30) not null,
tel varchar(30) not null
);

create table tb_condition
(
id int primary key auto_increment,
floor varchar(30) not null,
dormitory varchar(30) not null,
personCount int not null,
contacts varchar(30) not null,
tel varchar(30) not null��
);



//�����ѯ
select floor, count(dormitory) as count from tb_condition group by floor;


