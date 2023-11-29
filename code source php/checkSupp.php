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

// Requête SQL pour supprimer l'essaim de la base de données
$deleteSql = "DELETE FROM essaim WHERE nomParticulier = '$nom' AND prenomParticulier = '$prenom' AND id_essaim = '$id_essaim'";
$result = mysqli_query($conn, $deleteSql);

if ($result) {
    echo "Suppression réussie.";
} else {
    echo "Erreur lors de la suppression : " . mysqli_error($conn);
}

mysqli_close($conn);
?>
