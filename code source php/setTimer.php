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

// Requête SQL pour récupérer dateFinPriseEnCharge
$sql = "SELECT datePriseEnCharge, dateFinPriseEnCharge FROM reception WHERE apiculteurEnCharge = '$id_apiculteur'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // Récupération de la valeur de dateFinPriseEnCharge
    $row = mysqli_fetch_assoc($result);
    $datePriseEnCharge = $row['datePriseEnCharge'];
    $dateFinPriseEnCharge = $row['dateFinPriseEnCharge'];

    $sql2 = "SELECT latitude, longitude FROM essaim WHERE id_essaim = '$id_essaim'";
    $result2 = mysqli_query($conn, $sql2);
    $row2 = mysqli_fetch_assoc($result2);
    $latitude = $row2['latitude'];
    $longitude = $row2['longitude'];

    // Retourner la valeur de dateFinPriseEnCharge
    $response = array('datePriseEnCharge' => $datePriseEnCharge, 'dateFinPriseEnCharge' => $dateFinPriseEnCharge, "latitude" => $latitude, "longitude" => $longitude);
    echo json_encode($response);
} else {
    // Aucun enregistrement trouvé
    echo "0";
}

mysqli_close($conn);
?>
