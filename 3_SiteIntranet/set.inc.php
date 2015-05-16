<?php

	/*
		Fichier de configuration du site intranet
		et d'instantiation de la connexion à la base de donnée
	*/

	$_SET = Array(
		"BDD" => Array(
			"TYPE" => "mysql"         ,
			"HOST" => "localhost"     ,
			"BASE" => "LigueLorraine" ,
			"USER" => "root"          ,
			"PASS" => ""              ,
			"PORT" => 3306
		) 
	) ;

	try {
		$flux_bdd = new PDO(
			$_SET['BDD']['TYPE'] . ':host=' . $_SET['BDD']['HOST'] . ";dbname=" . $_SET['BDD']['BASE'] ,
			$_SET['BDD']['USER'] , $_SET['BDD']['PASS']
		) ;
	} catch( PDOException $e ){ // Si erreur lors de la connexion
		print "Err : " . $e->getMessage() ;
 		die( "Erreur connexion à la base de donnée !" ) ; // ... Alors on arrête tout, car cette dernière est obligatoire !
	} ;