<?php
// Connexion à la base de données
$host = "localhost"; // Adresse du serveur MySQL
$user = "root"; // Nom d'utilisateur MySQL
$pass = ""; // Mot de passe MySQL
$dbname = "easybeebdd3"; // Nom de la base de données MySQL
$conn = mysqli_connect($host, $user, $pass, $dbname);

// Vérification de la connexion
if (!$conn) {
  die("Erreur de connexion à la base de données: " . mysqli_connect_error());
}

// Tous les essaims dont etat = 1
$sql = "SELECT * FROM essaim WHERE etat = 1";

//Exécution de la requête
$resultat = mysqli_query($conn, $sql);

//Récupération des données
$essaims = mysqli_fetch_all($resultat, MYSQLI_ASSOC);

//Affichage des données
$resultArray = array();
foreach ($essaims as $essaim) {
    $row = array(
        "id_essaim" => $essaim["id_essaim"],
        "latitude" => $essaim["latitude"],
        "longitude" => $essaim["longitude"],
        "dateCreation" => $essaim["dateCreation"]
    );
    array_push($resultArray, $row);
}
$json = json_encode($resultArray);
echo $json;

// Fermeture de la connexion à la base de données
mysqli_close($conn);
?>
