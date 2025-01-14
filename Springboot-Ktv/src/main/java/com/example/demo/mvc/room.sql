-- 刪除已存在的表格
drop table if exists booking_room;
drop table if exists room;
drop table if exists users;
-- 建立 room
create table if not exists room(
	room_id int primary key,
    room_name varchar(50) not null unique,
    room_size int not null default 5
);

-- 建立 users
create table if not exists users(
	user_id int auto_increment primary key,
	user_name varchar(50) not null,    --  姓名
    gender varchar(10) not null,       --  男/女
    email varchar(100) not null,       --  信箱
    password varchar(50) not null,     --  密碼
    phone varchar(20) not null,        --  電話
    salt varchar(50) not null,		   --  密碼鹽
	permissions int not null default 0 --  權限
);

-- 建立 booking_room
create table if not exists booking_room(
	booking_id int auto_increment primary key,
    user_id int not null,
    room_id int not null,
    booking_date datetime not null,
    booking_hours int not null default 3,
    lastupdatetime timestamp default current_timestamp on update current_timestamp,
    foreign key (room_id) references room(room_id),
    foreign key (user_id) references users(user_id),
    constraint unique_room_id_and_booking_date unique(room_id, booking_date)
);

-- 建立範例資料
insert into room(room_id, room_name, room_size) values
(101, '101(S)', 5),
(102, '102(L)', 10),
(103, '103(L)', 10),
(104, '104(S)', 5);

insert into users(user_name, gender, email, password, phone, salt, permissions) values
('Shin', '男', 'user1@example.com', '5b3c304369e4430880dbb2fbd1946f27', '0900-123-321', 'pvGdXhnCNxiQX1X5ARH20g==', 1),
('Shikken', '女', 'user2@example.com', 'ce4749c8e8362e684f98cededd780083', '0911-321-123', 'EyssD1e4NmQV0ycjEH6zsA==', 1),
('Terry', '男', 'user3@example.com', 'd5bf231443430a6af2434aad09baaa7d', '0922-222-111', 'swc2UFCEVPgmNBB9QJdzeA==', 0),
('Alice', '女', 'user4@example.com', '2bfda777545cecf70c1765b6b2c4443d', '0933-444-555', 'ITdPiWX2b88eo6otRfNTrQ==', 0),
('Bob', '男', 'user5@example.com', '3bc320f6487df81eba9ce87bb9ceb03e', '0944-555-666', 'Ji3zbcTJt/k2ewREMfe1KA==', 1),
('Charlie', '男', 'user6@example.com', '5fb36129fe28e00214449e42c97c5d1b', '0955-666-777', 'b9rLyn78zZuXaKtI5V5NNw==', 0),
('Diana', '女', 'user7@example.com', '790b8d85ffa73d6695d23b8a6ca17349', '0966-777-888', 'yg3xK6elg1UfArl9pmA5jg==', 1);

insert into booking_room(user_id, room_id, booking_date, booking_hours) values
(1, 101, '2024-07-05 10:00', 3),
(2, 102, '2024-07-05 13:00', 5),
(3, 103, '2024-07-05 11:00', 4),
(4, 104, '2024-07-05 11:00', 4),
(5, 103, '2024-07-05 16:00', 3),
(6, 104, '2024-07-05 16:00', 5);

