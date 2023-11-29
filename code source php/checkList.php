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

// Requête pour récupérer les essaims correspondants
$sql = "SELECT id_essaim, latitude, longitude, dateCreation FROM essaim WHERE nomParticulier = '$nom' AND prenomParticulier = '$prenom' AND etat = 1";
$result = mysqli_query($conn, $sql);

// Vérification des résultats
if ($result) {
  if (mysqli_num_rows($result) > 0) {
    // Construction de la liste des essaims
    $listeEssaims = array();
    while ($row = mysqli_fetch_assoc($result)) {
      $essaim = array(
        'id_essaim' => $row['id_essaim'],
        'latitude' => $row['latitude'],
        'longitude' => $row['longitude'],
        'dateCreation' => $row['dateCreation']
      );
      $listeEssaims[] = $essaim;
    }

    // Conversion de la liste en JSON
    $jsonEssaims = json_encode($listeEssaims);

    // Envoi de la réponse JSON
    header('Content-Type: application/json');
    echo $jsonEssaims;
  } else {
    echo "Aucun essaim trouvé pour cet utilisateur.";
  }
} else {
  echo "Erreur de requête: " . mysqli_error($conn);
}

// Fermeture de la connexion à la base de données
mysqli_close($conn);
?>
