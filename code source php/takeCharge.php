<?php
// Récupération des données POST envoyées par l'application Android
$id_apiculteur = $_POST['id_apiculteur'];
$id_essaim = $_POST['id_essaim'];

// Connexion à la base de données MySQL
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "easybeebdd3";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Requête SQL pour mettre à jour l'état de l'essaim
$sql1 = "UPDATE essaim SET etat = 2 WHERE id_essaim = $id_essaim;";

// Requête SQL pour mettre à jour essaimEnCharge de l'apiculteur
$sql2 = "UPDATE apiculteur SET essaimEnCharge = $id_essaim WHERE id_apiculteur = $id_apiculteur;";

// Requête SQL pour insérer dans la table reception
date_default_timezone_set('Europe/Paris');
$datePriseEnCharge = date("Y-m-d H:i:s");
$dateFinPriseEnCharge = date("Y-m-d H:i:s", strtotime($datePriseEnCharge . " + 3 hours"));
    
$sql3 = "INSERT INTO reception (datePriseEnCharge, dateFinPriseEnCharge, apiculteurEnCharge, essaimAssocie) VALUES ('$datePriseEnCharge', '$dateFinPriseEnCharge', '$id_apiculteur', '$id_essaim');";
$sql = $sql1 . $sql2 . $sql3;

// Exécution des requêtes
if (mysqli_multi_query($conn, $sql)) {
    echo "Requêtes exécutées avec succès.";
} else {
    echo "Erreur lors de l'exécution des requêtes : " . mysqli_error($conn);
}
mysqli_close($conn);
?>
