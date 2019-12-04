<?php
/* Un server SOAP scris in PHP 5 oferind metode de manipulare a portocalelor

   Autor: Sabin-Corneliu Buraga - https://profs.info.uaic.ro/~busaco/
   Ultima actualizare: 30 aprilie 2011
*/

require_once ('bd.php');

$bd = new DataBase_MySQL ('localhost', 'stud', 'utilizator', '');
// ne conectam la serverul MySQL 
$bd->connect(); 


try {
  // nu oferim nici o descriere WSDL, stabilim URI-ul serviciului
  $server = new SoapServer(null, 
    array('uri' => 'urn:test2_server') // spatiul de nume folosit
  );
  $server->addFunction('getInfo');
  $server->handle();
} catch (SOAPFault $exception) {
  echo 'A aparut o exceptie: ' . $exception;
}

function getInfo ($nume) {
    $bd->query("select * from info where name='".$nume."';");
    return $bd->Record;
}
?>