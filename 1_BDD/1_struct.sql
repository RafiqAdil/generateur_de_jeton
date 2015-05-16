/*
	Définission de la structure de la base de données
*/

SET NAMES utf8 ;

-- Suppression de la base de donnée si existante
DROP DATABASE IF EXISTS LigueLorraine ;

-- Création de la base de données avec l'encodage UTF-8
CREATE DATABASE LigueLorraine CHARACTER SET utf8 COLLATE utf8_general_ci ;

-- Utilisation de la base de donnée nouvellement créer
USE LigueLorraine ;

-- Suppression de toutes les tables et relations
DROP TABLE IF EXISTS employe ; -- Table des employés

-- Création de la table des employés
CREATE TABLE IF NOT EXISTS employe (
  id_employe        INT(3)       NOT     NULL  AUTO_INCREMENT , 
  nom_employe       VARCHAR(20)  DEFAULT NULL                 ,  
  prenom_employe    VARCHAR(20)  DEFAULT NULL                 ,
  mail_employe      VARCHAR(30)  DEFAULT NULL                 ,
  datejeton_employe DATE         DEFAULT NULL                 , -- Date de génération du jeton
  jeton_employe     VARCHAR(100) DEFAULT NULL                 , -- Jeton de connexion à l'interface intranet
  mdp_sha1_employe  VARCHAR(100) NOT     NULL                 , -- Hash du mot de passe en SHA1 pour l'application mobile
  mdp_md5_employe   VARCHAR(32)  NOT     NULL                 , -- Hash du mot de passe en MD5  pour l'application mobile
  administrateur    TINYINT(1)   DEFAULT NULL                 ,
  PRIMARY KEY ( id_employe )
)  ENGINE=InnoDB CHARACTER SET=utf8 ;