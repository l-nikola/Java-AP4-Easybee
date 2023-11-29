<?php
// Récupération des données POST envoyées par l'application Android
$nom = $_POST['nom'];
$prenom = $_POST['prenom'];
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

// Requête SQL pour vérifier si l'essaim est enregistré dans la base de données
$updateSql = "UPDATE essaim SET etat = 3 WHERE nomParticulier = '$nom' AND prenomParticulier = '$prenom' AND id_essaim = '$id_essaim'";
$result = mysqli_query($conn, $updateSql);

if ($result) {
    echo "Mise à jour réussie.";
} else {
    echo "Erreur lors de la mise à jour : " . mysqli_error($conn);
}

mysqli_close($conn);
?>
