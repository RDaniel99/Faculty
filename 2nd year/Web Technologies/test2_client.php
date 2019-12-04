<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ro" xml:lang="ro">
<head>
<title>Portocale</title>
</head>
<body>
<?php
/* Un client SOAP scris in PHP 5 invocind metode de obtinere 
   a stocului de portocale

   Autor: Sabin-Corneliu Buraga - https://profs.info.uaic.ro/~busaco/
   Ultima actualizare: 6 martie 2019
*/
try {
	$client = new SoapClient(null, 
	    array('location' => 'http://localhost/test2_server.php', // adresa serviciului
              'uri'      => 'urn:test2_server'                        // spatiul de nume
		)
	);
   // realizam o suita de invocari ale metodei dorite
   foreach (array('Gotca Adrian') as $nume) {
     $rez = $client->__soapCall('furnizeazaCantit', $nume);
     $varsta=$rez["varsta"];
     $inaltimea=$rez["inaltime"];
     $color=$rez["culoare"];
     echo "<p>Pentru $nume avem varsta de $varsta ani, inaltimea de $inaltime cm si culoarea preferata $color.</p>";
   }  
} catch (SOAPFault $exception) {
   echo 'A aparut o exceptie: ' . $exception->faultstring;
}
?>
</body>
</html>