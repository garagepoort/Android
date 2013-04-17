CREATE TABLE Users
( UserId SERIAL not null,
Naam varchar(40) not null,
Achternaam varchar(50) not null,
Paswoord varchar(40) not null,
Email varchar(100) not null unique,
senderID varchar(500),
Salt char(20) not null,
admin boolean not null default false,
CONSTRAINT PRIMARY_KEY_USER PRIMARY KEY (UserId));

CREATE TABLE Alarm
( AlarmID SERIAL NOT NULL UNIQUE,
title varchar(50) not null,
info varchar(50) not null,
repeated boolean not null,
repeat_unit varchar(8),
repeatquantity integer,
repeat_enddate bigint,
date_in_millis bigint not null,
CONSTRAINT PRIMARY_KEY_ALARM PRIMARY KEY (AlarmId));

CREATE TABLE User_Alarm
( UserAlarmId SERIAL NOT NULL UNIQUE,
UserId int not null,
AlarmId int not null,
CONSTRAINT FOREIGN_KEY_UserLINK FOREIGN KEY (UserId) references Users (UserId),
CONSTRAINT FOREIGN_KEY_ALARM FOREIGN KEY (AlarmId) references Alarm (AlarmId),
CONSTRAINT PRIMARY_KEY_USERALARM PRIMARY KEY (UserAlarmId));