<?php
// Récupération des données POST envoyées par l'application Android
$identifiant = $_POST['identifiant'];
$motdepasse = $_POST['motdepasse'];

// Connexion à la base de données MySQL
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "easybeebdd3";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Requête SQL pour vérifier si l'apiculteur est enregistré dans la base de données
$sql = "SELECT * FROM apiculteur WHERE identifiant_apiculteur = '$identifiant' AND motdepasse_apiculteur = '$motdepasse'";
$result = mysqli_query($conn, $sql);

// Si l'apiculteur est enregistré dans la base de données, on renvoie son identifiant
if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $id_apiculteur = $row["id_apiculteur"];
    $essaimEnCharge = $row["essaimEnCharge"];
    $nom_apiculteur = $row["nom_apiculteur"];
    $prenom_apiculteur = $row["prenom_apiculteur"];
    $response = array('id_apiculteur' => $id_apiculteur, 'essaimEnCharge' => $essaimEnCharge, 'nom_apiculteur' => $nom_apiculteur, 'prenom_apiculteur' => $prenom_apiculteur);
    echo json_encode($response);
} else {
    echo "Identifiant ou mot de passe incorrect";
}
mysqli_close($conn);
?>
