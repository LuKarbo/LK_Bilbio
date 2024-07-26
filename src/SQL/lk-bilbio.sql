-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 26-07-2024 a las 21:16:02
-- Versión del servidor: 8.0.31
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `lk-bilbio`
--
CREATE DATABASE IF NOT EXISTS `lk-bilbio` DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
USE `lk-bilbio`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `id_book` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(45) NOT NULL,
  `autor` varchar(45) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `status` tinyint NOT NULL,
  PRIMARY KEY (`id_book`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `book`
--

INSERT INTO `book` (`id_book`, `titulo`, `autor`, `price`, `status`) VALUES
(1, 'Test', 'Yo', '2.50', 0),
(3, 'asd', 'asd', '2.00', 1),
(4, 'test2', 'qwe', '22.00', 0),
(5, 'ddd', '34s', '23.66', 0),
(6, 'ddddd', '22d', '222.30', 1),
(7, 'se', 'sasdad', '22.40', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`id_category`, `nombre`) VALUES
(1, 'Científicos'),
(2, 'Literatura y lingüísticos'),
(3, 'De viaje'),
(4, 'Biografías'),
(5, 'Libro de texto'),
(6, 'Monografías'),
(7, 'Recreativos'),
(8, 'Poéticos'),
(9, 'Acción'),
(10, 'Comedia'),
(11, 'Ficción'),
(12, 'Terror'),
(13, 'Aventuras'),
(14, 'Juveniles'),
(15, 'Fantasía');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category_book`
--

DROP TABLE IF EXISTS `category_book`;
CREATE TABLE IF NOT EXISTS `category_book` (
  `id_category_book` int NOT NULL AUTO_INCREMENT,
  `id_book` int NOT NULL,
  `id_category` int NOT NULL,
  PRIMARY KEY (`id_category_book`),
  KEY `fk_category_book_book1_idx` (`id_book`),
  KEY `fk_category_book_category1_idx` (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `category_book`
--

INSERT INTO `category_book` (`id_category_book`, `id_book`, `id_category`) VALUES
(1, 1, 1),
(2, 1, 6),
(3, 1, 4),
(5, 3, 1),
(6, 3, 5),
(7, 3, 7),
(8, 3, 9),
(9, 3, 11),
(10, 4, 1),
(11, 4, 6),
(12, 4, 10),
(13, 4, 13),
(14, 5, 15),
(15, 5, 14),
(16, 6, 2),
(17, 7, 15),
(18, 7, 14),
(19, 7, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permisos`
--

DROP TABLE IF EXISTS `permisos`;
CREATE TABLE IF NOT EXISTS `permisos` (
  `id_permisos` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_permisos`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `permisos`
--

INSERT INTO `permisos` (`id_permisos`, `nombre`) VALUES
(1, 'usuario'),
(2, 'bibliotecario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
CREATE TABLE IF NOT EXISTS `prestamo` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_book` int NOT NULL,
  `inicioDate` datetime DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id_prestamo`),
  KEY `fk_prestamo_user1_idx` (`id_user`),
  KEY `fk_prestamo_book1_idx` (`id_book`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id_prestamo`, `id_user`, `id_book`, `inicioDate`, `status`) VALUES
(1, 3, 1, '2024-07-20 00:00:00', 0),
(2, 1, 4, '2024-07-20 00:00:00', 0),
(3, 3, 3, '2024-07-20 00:00:00', 0),
(4, 3, 5, '2024-07-22 00:00:00', 1),
(5, 1, 1, '2024-07-26 00:00:00', 1),
(6, 1, 4, '2024-07-26 00:00:00', 1),
(7, 3, 7, '2024-07-26 00:00:00', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `contrasena` varchar(260) NOT NULL,
  `id_permisos` int NOT NULL,
  PRIMARY KEY (`id_user`),
  KEY `fk_user_permisos_idx` (`id_permisos`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id_user`, `nombre`, `contrasena`, `id_permisos`) VALUES
(1, 'test', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 1),
(3, 'lucas', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 2),
(6, 'yo', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 2),
(7, 'cliente', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `category_book`
--
ALTER TABLE `category_book`
  ADD CONSTRAINT `fk_category_book_book1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_category_book_category1` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`) ON DELETE CASCADE;

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `fk_prestamo_book1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_prestamo_user1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_permisos` FOREIGN KEY (`id_permisos`) REFERENCES `permisos` (`id_permisos`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
