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

// Récupération des données en POST
$nom = $_POST['nom'];
$prenom = $_POST['prenom'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

// Préparation de la requête SQL
$date_creation = date('Y-m-d');
$sql = "INSERT INTO essaim (nomParticulier, prenomParticulier, dateCreation, etat, latitude, longitude)
        VALUES ('$nom', '$prenom', '$date_creation', '1', '$latitude', '$longitude')";

// Exécution de la requête SQL
if (mysqli_query($conn, $sql)) {
}
else {
  echo "Erreur: " . $sql . "<br>" . mysqli_error($conn);
}

// Fermeture de la connexion à la base de données
mysqli_close($conn);
?>
