create database TeachingWords

USE TeachingWords


CREATE TABLE USERS (
 idUser INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
 email VARCHAR(32) UNIQUE NOT NULL,
 password VARCHAR(32) NOT NULL,
 hash VARCHAR(32) NOT NULL,
 active BIT NOT NULL
 )

CREATE TABLE STATS(
idStats int AUTO_INCREMENT NOT NULL PRIMARY KEY,
idUser int NOT NULL,
idLevel int NOT NULL,
dataInput date UNIQUE NOT NULL,
valueProgres DECIMAL NOT NULL,
FOREIGN KEY (idUser) REFERENCES USERS(idUser) ON DELETE CASCADE
)

CREATE TABLE LEVELS(
idLevel int AUTO_INCREMENT NOT NULL PRIMARY KEY,
levelName VARCHAR(32) NOT NULL,
categorie VARCHAR(32) NOT NULL
)

CREATE TABLE TRANSLATIONS(
idTranslation int AUTO_INCREMENT NOT NULL PRIMARY KEY,
idLevel int NOT NULL,
engWord VARCHAR(32) NOT NULL,
plWord VARCHAR(32) NOT NULL,
FOREIGN KEY (idLevel) REFERENCES LEVELS(idLevel) ON DELETE CASCADE
)

CREATE TABLE EXAMPLES(
idExample int AUTO_INCREMENT NOT NULL PRIMARY KEY,
idTranslation int NOT NULL,
engExample VARCHAR(64) NOT NULL,
plExample VARCHAR(64) NOT NULL,
FOREIGN KEY (idTranslation) REFERENCES TRANSLATIONS(idTranslation) ON DELETE CASCADE
)

CREATE TABLE DEFINITIONS(
idDefinition int AUTO_INCREMENT NOT NULL PRIMARY KEY,
idTranslation int NOT NULL,
plDefinition VARCHAR(64) NOT NULL,
engDefinition VARCHAR(64) NOT NULL,
FOREIGN KEY (idTranslation) REFERENCES TRANSLATIONS(idTranslation) ON DELETE CASCADE
)