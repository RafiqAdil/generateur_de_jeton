<?php

	/*
		On spécifie un jeton de connexion à allouer à un utilisateur
	*/

	// On inclus le fichier de configuration une seule fois
	require_once( "../private-inc/set.inc.php" ) ;

	if( count( $_GET ) < 4 || empty( $_GET[ 'id' ] ) || empty( $_GET[ 'sha1' ] ) || empty( $_GET['md5'] ) || empty( $_GET['jeton'] ) ) {
		$flux_bdd = null ; // Fermeture du flux vers la base de donnée
		die( "Accès refuser !" ) ;
	} 

	try {
		// Vérification de la taille du jeton
		if( strlen( $_GET['jeton'] ) != 10 )
			die( json_encode( array( "status" => 0 ) ) ) ;

		// Préparation de la requête, récupération de l'ID du compte concerné
		$stmt = $flux_bdd->prepare(
			"SELECT setJeton( :jeton , :id , :sha1 , :md5 ) AS STATUS ;"
		) ;

		$stmt->bindParam( ':jeton' , $_GET[ 'jeton' ] ) ; // Jeton de l'utilisateur
		$stmt->bindParam( ':id'    , $_GET[ 'id'    ] ) ; // Identificateur unique de l'utilisateur
		$stmt->bindParam( ':sha1'  , $_GET[ 'sha1'  ] ) ; // Hash du mot de passe en SHA1
		$stmt->bindParam( ':md5'   , $_GET[ 'md5'   ] ) ; // Hash du mot de passe en MD5

		$result = $stmt->execute() ; // Execution de la procédure stockée

		if( $result && intval( $stmt->fetchAll( PDO::FETCH_COLUMN , 0 )[0] ) == intval(0) )
			die( json_encode( array( "status" => 1 ) ) ) ;
		else 
			die( json_encode( array( "status" => 0 ) ) ) ;

	} catch( Exception $e ){
		echo json_encode( array( "status" => 0  ) ) ;
	} finally {
		$flux_bdd = null ; // Fermeture du flux vers la base de donnée
	}