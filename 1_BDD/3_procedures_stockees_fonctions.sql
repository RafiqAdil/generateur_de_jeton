/*
	Définission des procédures stockées et fonctions
*/

SET NAMES utf8 ;

USE LigueLorraine ; -- Selection de la base de donnée

-- Suppression des procédures existantes
DROP FUNCTION IF EXISTS setJeton ; -- Ajout d'un jeton

DELIMITER $$ -- Changement du délimiteur pour la définition des procédures stockées

-- Ajout d'un jeton
CREATE FUNCTION
	setJeton(
		jetonValue  VARCHAR(10)  , -- Le jeton
		employeID   INT(3)       , -- L'identificateur unique de l'employé concerné
		mdpSHA1     VARCHAR(100) , -- Hash du mot de passe en SHA1
		mdpMD5      VARCHAR(32)    -- Hash du mot de passe en MD5
	) RETURNS INT(1)
	LANGUAGE SQL
BEGIN
	DECLARE employe_exists INTEGER ; -- Variable de vérification si l'employe existe

	-- Vérification de la bonne formulation du jeton (10 caractères)
    IF CHAR_LENGTH( jetonValue ) != 10 THEN
    	RETURN 1 ; -- Code erreur longueur invalide
    ELSE
	    -- Vérification de l'existance de l'utilisateur (au minimum 1, sachant qu'il ne peut en avoir qu'un au maximum)
	    UPDATE employe SET jeton_employe = jetonValue, datejeton_employe = NOW() WHERE id_employe = employeID AND mdp_sha1_employe = mdpSHA1 AND mdp_md5_employe = mdpMD5 LIMIT 1 ;
	    RETURN 0 ;
    END IF ;
END;
$$

DELIMITER ; -- Rétablissement du délimiteur par défaut