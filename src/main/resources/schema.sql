CREATE TABLE USERS (ID INT PRIMARY KEY, USERNAME VARCHAR(50), PASSWORD VARCHAR(70));

CREATE TABLE APP_USER (
  USER_ID           NUMBER(19) NOT NULL,
  USER_NAME         VARCHAR2(36) NOT NULL,
  ENCRYTED_PASSWORD VARCHAR2(128) NOT NULL,
  ENABLED           NUMBER(1) NOT NULL 
);

ALTER TABLE APP_USER ADD CONSTRAINT APP_USER_PK PRIMARY KEY (USER_ID);
ALTER TABLE APP_USER ADD CONSTRAINT APP_USER_UK UNIQUE (USER_NAME);
 
 
-- CREATE TABLE
CREATE TABLE APP_ROLE (
  ROLE_ID   NUMBER(19) NOT NULL,
  ROLE_NAME VARCHAR2(30) NOT NULL
) ;
--  
ALTER TABLE APP_ROLE ADD CONSTRAINT APP_ROLE_PK PRIMARY KEY (ROLE_ID);
ALTER TABLE APP_ROLE ADD CONSTRAINT APP_ROLE_UK UNIQUE (ROLE_NAME);

-- CREATE TABLE
CREATE TABLE USER_ROLE (
  ID      NUMBER(19) NOT NULL,
  USER_ID NUMBER(19) NOT NULL,
  ROLE_ID NUMBER(19) NOT NULL
);
--  
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_PK PRIMARY KEY (ID);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_UK UNIQUE (USER_ID, ROLE_ID);
  
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_FK1 FOREIGN KEY (USER_ID) REFERENCES APP_USER (USER_ID);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_FK2 FOREIGN KEY (ROLE_ID) REFERENCES APP_ROLE (ROLE_ID);

-- USED BY SPRING REMEMBER ME API.  
CREATE TABLE PERSISTENT_LOGINS ( 
    USERNAME VARCHAR2(64) NOT NULL,
    SERIES VARCHAR2(64) NOT NULL,
    TOKEN VARCHAR2(64) NOT NULL,
    LAST_USED DATE NOT NULL,
    PRIMARY KEY (SERIES)
);
