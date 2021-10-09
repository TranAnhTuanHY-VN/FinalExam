DROP DATABASE IF EXISTS FinalProject;
CREATE DATABASE FinalProject;
USE FinalProject;

DROP TABLE IF EXISTS `Group`;
CREATE TABLE `Group`(
	GroupID 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    GroupName 				NVARCHAR(50) NOT NULL UNIQUE KEY,
	`MemberNum`				INT UNSIGNED DEFAULT 0,
    CreatorID				INT UNSIGNED,
    CreateDate				DATETIME DEFAULT NOW(),
	ModifiedDate			DATETIME DEFAULT NOW()
);

DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account`(
	AccountID				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    Email					VARCHAR(50) NOT NULL UNIQUE KEY, 
    Username				VARCHAR(50) NOT NULL UNIQUE KEY,
    `Password` 				VARCHAR(800) NOT NULL,
    FirstName				NVARCHAR(50) NOT NULL,
    LastName				NVARCHAR(50) NOT NULL,
    CreateDate				DATETIME DEFAULT NOW(),
    GroupID 				INT UNSIGNED DEFAULT 1,
    `Role`					NVARCHAR(50) DEFAULT 'User',
    FOREIGN KEY(GroupID) REFERENCES `Group`(GroupID) ON DELETE SET NULL,
    `Status`				TINYINT  DEFAULT 0 -- 0:Not Active, 1: Active
);


-- Create table Registration_User_Token
DROP TABLE IF EXISTS 	`Token`;
CREATE TABLE IF NOT EXISTS `Token` ( 	
	id 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`token`	 		CHAR(36) NOT NULL UNIQUE,
	`user_id` 		SMALLINT UNSIGNED NOT NULL,
	`expiryDate` 	DATETIME NOT NULL,
    `type`			Varchar(50)	
);

                                                                            