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
$sql1 = "UPDATE essaim SET etat = 1 WHERE id_essaim = ?";
$stmt1 = mysqli_prepare($conn, $sql1);
mysqli_stmt_bind_param($stmt1, "s", $id_essaim);
$result1 = mysqli_stmt_execute($stmt1);

// Requête SQL pour mettre à jour essaimEnCharge de la table apiculteur
$sql2 = "UPDATE apiculteur SET essaimEnCharge = NULL WHERE id_apiculteur = ?";
$stmt2 = mysqli_prepare($conn, $sql2);
mysqli_stmt_bind_param($stmt2, "s", $id_apiculteur);
$result2 = mysqli_stmt_execute($stmt2);

// Requête SQL pour supprimer la ligne de la table reception
$sql3 = "DELETE FROM reception WHERE apiculteurEnCharge = ?";
$stmt3 = mysqli_prepare($conn, $sql3);
mysqli_stmt_bind_param($stmt3, "s", $id_apiculteur);
$result3 = mysqli_stmt_execute($stmt3);

if ($result1 && $result2 && $result3) {
    echo "Success";
} else {
    echo "Error";
}

mysqli_close($conn);
?>
