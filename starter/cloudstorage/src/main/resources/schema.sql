CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20) UNIQUE,
  salt VARCHAR(1000),
  password VARCHAR(1000),
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR(1000),
    contenttype VARCHAR(1000),
    filesize VARCHAR(1000),
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR(1000),
    password VARCHAR(1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

--init data
INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES
('PandaReveluv', 'n16ccebcSdTPHzTY5toZLw==', 'ezlTMhIkP9FN8H2t/GXeBP+NK9KGz+NlKiusF6YjyxYX5RqO8j0SGGWzE0GmO3C590mMtMwP4NxFWzvB8B622Sy9sHG321fZERs8ZEHuObj0rmUmjuexxbmER0pS5HXw8EyZAW+WC4/8b7P8KV8RZwa+rrif+huKvyMN/6TDdP86ZQUyfdDyE6Q9mp+ZkkfyRWl1D0VLPm7jiFxG5C7pzxzcEhDgGXlQT7iNy5glph/TJXYR6+MSBj8qmIuQHdyJj0cRqjb3pKRYKvKaz9dhHnydHGl7icB3pnREKqkekj5RbXD81BiasO6LvYrDMbXnTh0WzyFseuLg1db7WhQOOA==', 'Dat', 'Nguyen');

INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('Test Note 1', 'Some Notes 1', 1);
INSERT INTO NOTES (notetitle, notedescription, userid) VALUES ('Test Note 2', 'Some Notes 2', 1);

INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES ('google.com', 'Dat', 'dfKO8IANpC0qsR9Klpk53w==', '6lRyQbRq273ow/pjd9LyAw==', 1)