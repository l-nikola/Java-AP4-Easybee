<?php

// Vérification que l'id de l'utilisateur est passé en paramètre
if (!isset($_POST['idUtilisateur']) || !is_numeric($_POST['idUtilisateur'])) {
    die("Erreur : id de l'utilisateur invalide.");
}

// Vérification que l'id de l'essaim est passé en paramètre
if (!isset($_POST['idEssaim']) || !is_numeric($_POST['idEssaim'])) {
    die("Erreur : id de l'essaim invalide.");
}

// Vérification que l'état de l'essaim est passé en paramètre
if (!isset($_POST['etat']) || !is_numeric($_POST['etat'])) {
    die("Erreur : état de l'essaim invalide.");
}

// Récupération des paramètres
$idUtilisateur = $_POST['idUtilisateur'];
$idEssaim = $_POST['idEssaim'];
$etat = $_POST['etat'];

// Connexion à la base de données
$bdd = new PDO('mysql:host=localhost;dbname=easybeebdd3', 'root', '');

// Modification de l'état de l'essaim dans la base de données
$req = $bdd->prepare('UPDATE essaim SET etat = :etat WHERE id_essaim = :idEssaim');
$req->execute(array(
    'etat' => $etat,
    'idEssaim' => $idEssaim
));

// Modification de l'essaim en charge pour l'utilisateur dans la base de données
$req = $bdd->prepare('UPDATE apiculteur SET essaimEnCharge = :idEssaim WHERE id_apiculteur = :idUtilisateur');
$req->execute(array(
    'idEssaim' => $idEssaim,
    'idUtilisateur' => $idUtilisateur
));

// Envoi de la réponse
echo "Modification réussie.";

?>
