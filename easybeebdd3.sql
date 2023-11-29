-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 21 mai 2023 à 01:47
-- Version du serveur : 8.0.32
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `easybeebdd3`
--

-- --------------------------------------------------------

--
-- Structure de la table `apiculteur`
--

DROP TABLE IF EXISTS `apiculteur`;
CREATE TABLE IF NOT EXISTS `apiculteur` (
  `id_apiculteur` int NOT NULL AUTO_INCREMENT,
  `nom_apiculteur` varchar(20) DEFAULT NULL,
  `prenom_apiculteur` varchar(20) DEFAULT NULL,
  `adr_apiculteur` varchar(255) DEFAULT NULL,
  `ville_apiculteur` varchar(20) DEFAULT NULL,
  `CP_apiculteur` int DEFAULT NULL,
  `telephone_apiculteur` int DEFAULT NULL,
  `identifiant_apiculteur` varchar(20) DEFAULT NULL,
  `motdepasse_apiculteur` varchar(20) DEFAULT NULL,
  `essaimEnCharge` int DEFAULT NULL,
  PRIMARY KEY (`id_apiculteur`),
  KEY `essaimEnCharge` (`essaimEnCharge`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `apiculteur`
--

INSERT INTO `apiculteur` (`id_apiculteur`, `nom_apiculteur`, `prenom_apiculteur`, `adr_apiculteur`, `ville_apiculteur`, `CP_apiculteur`, `telephone_apiculteur`, `identifiant_apiculteur`, `motdepasse_apiculteur`, `essaimEnCharge`) VALUES
(1, 'testnom', 'testprenom', 'test', 'test', 45555, 232125454, 'test', 'test', NULL),
(6, 'Andrieu', 'Thomas', '687 rue de la croix de buis', 'Emanville', 76570, 643962562, 'admin', 'root', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `essaim`
--

DROP TABLE IF EXISTS `essaim`;
CREATE TABLE IF NOT EXISTS `essaim` (
  `id_essaim` int NOT NULL AUTO_INCREMENT,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `dateCreation` date DEFAULT NULL,
  `etat` varchar(20) DEFAULT NULL,
  `nomParticulier` varchar(40) DEFAULT NULL,
  `prenomParticulier` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id_essaim`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `essaim`
--

INSERT INTO `essaim` (`id_essaim`, `latitude`, `longitude`, `dateCreation`, `etat`, `nomParticulier`, `prenomParticulier`) VALUES
(1, 37.422, -122.084, '2023-04-21', '1', 'Dupre', 'Laura'),
(13, 37.422, -122.084, '2023-05-21', '1', 'test', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `reception`
--

DROP TABLE IF EXISTS `reception`;
CREATE TABLE IF NOT EXISTS `reception` (
  `datePriseEnCharge` varchar(255) DEFAULT NULL,
  `dateFinPriseEnCharge` varchar(255) DEFAULT NULL,
  `apiculteurEnCharge` int DEFAULT NULL,
  `essaimAssocie` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `apiculteur`
--
ALTER TABLE `apiculteur`
  ADD CONSTRAINT `apiculteur_ibfk_1` FOREIGN KEY (`essaimEnCharge`) REFERENCES `essaim` (`id_essaim`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
