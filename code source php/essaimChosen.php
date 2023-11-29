<?php
// Récupération des données POST envoyées par l'application Android
$id_essaim = $_POST['id_essaim'];
$id_apiculteur = $_POST['id_apiculteur'];

// Connexion à la base de données MySQL
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "easybeebdd3";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Requête SQL pour vérifier si l'essaim est vide ou existe
if (empty($id_essaim)) {
    die("Aucun essaim saisi");
} else {
    $sql = "SELECT * FROM essaim WHERE essaim='$id_essaim'";
    $result = mysqli_query($conn, $sql);
    if($result == null || $result == "") {
        die("Essaim non obtenu");
    }
    else {
        $sqlUpdate1 = "UPDATE essaim SET etat = 2 WHERE id_essaim = :id_essaim";
        $stmt = $db->prepare($sqlUpdate1);
        $stmt->bindParam(':id_essaim', $id_essaim, PDO::PARAM_INT);
        $stmt->execute();
    }
}

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
