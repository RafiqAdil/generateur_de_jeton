<?php

	/*
		Connexion et récupération des informations du compte
	*/

	// On inclus le fichier de configuration une seule fois
	require_once( "../private-inc/set.inc.php" ) ;

	// Nom d'utilisateur / Mot de passe HASH SHA1  / Mot de passe HASH MD5 
	if( count( $_GET ) < 3 || empty( $_GET[ 'user' ] ) || empty( $_GET[ 'sha1' ] ) || empty( $_GET['md5'] ) ) {
		$flux_bdd = null ; // Fermeture du flux vers la base de donnée
		die( "Accès refuser !" ) ;
	} 

	try {
		// Préparation de la requête, récupération de l'ID du compte concerné
		$stmt = $flux_bdd->prepare(
			"SELECT id_employe AS ID, nom_employe AS NOM, prenom_employe AS PRENOM FROM employe WHERE mail_employe = :username AND " .
			"mdp_sha1_employe = :sha1pass AND mdp_md5_employe = :md5pass LIMIT 1 ;"
		) ;

		$stmt->bindParam( ':username' , $_GET[ 'user' ] ) ; // Nom d'utilisateur correspondant à l'@ e-mail
		$stmt->bindParam( ':sha1pass' , $_GET[ 'sha1' ] ) ; // Hash du mot de passe en SHA1
		$stmt->bindParam( ':md5pass'  , $_GET[ 'md5'  ] ) ; // Hash du mot de passe en MD5

		$stmt->execute() ; // Execution de la requête SQL

		if( $stmt->rowCount() < 1 )
			die( json_encode( array( "status" => 0  ) ) ) ;
		else{
			$datas = $stmt->fetchAll()[0] ;
			die( json_encode( array( "status" => 1 , "account_id" => $datas["ID"] , "account_firstname" => $datas["PRENOM"] , "account_lastname" => $datas["NOM"] ) ) ) ;
		}
  
	} catch( Exception $e ){
		echo json_encode( array( "status" => 0  ) ) ;
	} finally {
		$flux_bdd = null ; // Fermeture du flux vers la base de donnée
	}