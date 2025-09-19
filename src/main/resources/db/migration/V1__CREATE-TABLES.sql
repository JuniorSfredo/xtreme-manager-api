create table tb_member
(
    is_active         bit,
    created_at datetime(6),
    id            bigint not null,
    primary key (id)
) engine=InnoDB;

create table tb_assessment
(
    imc                   float(53),
    bodyfat_percentage float(53),
    weight                  float(53),
    member_id              bigint,
    date                  datetime(6),
    id                    bigint not null auto_increment,
    personal_id          bigint,
    note            varchar(255),
    status                varchar(30),
    primary key (id)
) engine=InnoDB;

create table tb_skin_fold
(
    mm           float(53),
    assessment_id bigint,
    id           bigint not null auto_increment,
    fold        varchar(80),
    primary key (id)
) engine=InnoDB;

create table tb_exercise
(
    max_reps integer not null,
    min_reps integer not null,
    series_number     integer not null,
    id             bigint not null auto_increment,
    name           varchar(255),
    primary key (id)
) engine=InnoDB;

create table tb_subscription
(
    expiration_date  date,
    created_at       date,
    member_id         bigint,
    id               bigint not null auto_increment,
    plan_id         bigint,
    payment_status varchar(70),
    primary key (id)
) engine=InnoDB;

create table tb_plan
(
    value float(53) not null,
    id    bigint not null auto_increment,
    plan varchar(40) not null,
    primary key (id)
) engine=InnoDB;

create table tb_personal
(
    hire_date date,
    salary          float(53),
    id               bigint not null,
    primary key (id)
) engine=InnoDB;

create table tb_role
(
    id   bigint not null auto_increment,
    name varchar(30),
    primary key (id)
) engine=InnoDB;

create table tb_workout
(
    member_id    bigint,
    id           bigint not null auto_increment,
    personal_id bigint,
    name         varchar(40),
    primary key (id)
) engine=InnoDB;

create table tb_workout_exercise
(
    exercise_id bigint not null,
    workout_id    bigint not null
) engine=InnoDB;

create table tb_workout_register
(
    end_date    datetime(6),
    start_date datetime(6),
    id          bigint not null auto_increment,
    workout_id   bigint not null,
    primary key (id)
) engine=InnoDB;

create table tb_user_role
(
    role_id bigint not null,
    user_id bigint not null
) engine=InnoDB;

create table tb_user
(
    height          float(53) not null,
    birth_date date not null,
    weight            float(53) not null,
    id              bigint       not null auto_increment,
    cpf             varchar(255) not null,
    email           varchar(255) not null,
    address        varchar(255) not null,
    name            varchar(255) not null,
    profile_img_url  varchar(255),
    password           varchar(255),
    primary key (id)
) engine=InnoDB;

alter table tb_member
    add constraint FKdsc9jop9hqwlo0cypuxv3weeq foreign key (id) references tb_user (id);
alter table tb_assessment
    add constraint FKodcn55938dhrhkud4vt0cb21r foreign key (member_id) references tb_member (id);
alter table tb_assessment
    add constraint FKamnd4hqx5cae2e20xk17w2fh6 foreign key (personal_id) references tb_personal (id);
alter table tb_skin_fold
    add constraint FKf6mtk8wag5awwh5irwtov5fw6 foreign key (assessment_id) references tb_assessment (id);
alter table tb_subscription
    add constraint FKskn319v4db9h5rocqrccti8in foreign key (member_id) references tb_member (id);
alter table tb_subscription
    add constraint FKislrd8hypw03g6kwfuhe1mbwf foreign key (plan_id) references tb_plan (id);
alter table tb_personal
    add constraint FKk1vstdijdax9gu30bmvqodtdv foreign key (id) references tb_user (id);
alter table tb_workout
    add constraint FKfywas00mgcg2ldaarbyg9fjd2 foreign key (member_id) references tb_member (id);
alter table tb_workout
    add constraint FKjys54x7nd9yignd0x8wk45qjr foreign key (personal_id) references tb_personal (id);
alter table tb_workout_exercise
    add constraint FKk9boig5nd3iug4qed3kqdeb00 foreign key (exercise_id) references tb_exercise (id);
alter table tb_workout_exercise
    add constraint FKfqw4g1347twekai4j793qrje1 foreign key (workout_id) references tb_workout (id);
alter table tb_workout_register
    add constraint FK6n9e5g20ww333tiqmc51la8ce foreign key (workout_id) references tb_workout (id);
alter table tb_user_role
    add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role (id);
alter table tb_user_role
    add constraint FKjbc88xnvdrcxn8tlsw21v4n7t foreign key (user_id) references tb_user (id);
