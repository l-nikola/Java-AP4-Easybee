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
$telephone = $_POST['telephone'];
$adresse = $_POST['adresse'];
$CP = $_POST['CP'];
$ville = $_POST['ville'];
$identifiant = $_POST['identifiant'];
$motdepasse = $_POST['motdepasse'];

// Vérification de l'existence de l'identifiant et du mot de passe
$sql = "SELECT * FROM apiculteur WHERE identifiant_apiculteur = '$identifiant' AND motdepasse_apiculteur = '$motdepasse'";
$result = mysqli_query($conn, $sql);
if (mysqli_num_rows($result) > 0) {
  die ("Cet identifiant et son mot de passe sont deja inscrits dans notre base de donnees !");
}
if (empty($identifiant || $motdepasse)) {
    die ("Creez vous un identifiant et un mot de passe !");
}
else {
    // Préparation de la requête SQL
    $date_creation = date('Y-m-d');
    $sql = "INSERT INTO apiculteur (nom_apiculteur, prenom_apiculteur, adr_apiculteur, CP_apiculteur, telephone_apiculteur, ville_apiculteur, identifiant_apiculteur, motdepasse_apiculteur, essaimEnCharge)
        VALUES ('$nom', '$prenom', '$adresse', '$CP', '$telephone', '$ville', '$identifiant', '$motdepasse', NULL)";
    // Exécution de la requête SQL
    if (mysqli_query($conn, $sql)) {
        echo "Retenez bien votre identifiant " . $identifiant . " et votre mot de passe " . $motdepasse . ". Ceci est necessaire pour chaque connexion a nos services.";
    }
    else {
        echo "Erreur: " . $sql . "<br>" . mysqli_error($conn);
    }
}
// Fermeture de la connexion à la base de données
mysqli_close($conn);
?>
