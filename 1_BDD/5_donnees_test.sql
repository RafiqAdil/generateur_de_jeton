/*
	Définission d'un jeu de donnée de test
*/

SET NAMES utf8 ;

USE LigueLorraine ; -- Selection de la base de donnée

INSERT INTO
	employe ( nom_employe, prenom_employe, mdp_sha1_employe , mdp_md5_employe, mail_employe, administrateur )
VALUES
	( 'Lassalle'  , 'Quentin'  , SHA1( 'test' ) , MD5( 'test' ) , 'quentin@lassalle.com'    , 0 ),
	( 'Bayart'    , 'Nicolas'  , SHA1( 'test' ) , MD5( 'test' ) , 'bayart@nicolas.com'      , 0 ),
	( 'Lenfant'   , 'Valentin' , SHA1( 'test' ) , MD5( 'test' ) , 'valentin@lenfant.com'    , 0 ),
	( 'Rihet'     , 'Alexandre', SHA1( 'test' ) , MD5( 'test' ) , 'rihet@alexandre.com'     , 0 ),
	( 'Rafiq'     , 'Adil'     , SHA1( 'test' ) , MD5( 'test' ) , 'rafiq@adil.com'          , 1 ),
	( 'Bedrignans', 'Thibault' , SHA1( 'test' ) , MD5( 'test' ) , 'thibault@bedrignans.com' , 0 ),
	( 'Bartolomei', 'Emmanuel' , SHA1( 'test' ) , MD5( 'test' ) , 'bartolomei@emmanuel.com' , 0 ),
	( 'Kerrad'    , 'Mamed'    , SHA1( 'test' ) , MD5( 'test' ) , 'mamed@kerrad.com'        , 0 )
;
