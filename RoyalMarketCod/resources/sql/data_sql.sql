
use database royalmarket;



INSERT INTO `producto` (`id`, `precio`, `stock`, `activo`, `nombre`, `id_almacen`) VALUES
(1, '14.40', 1, 1, 'Prueba Unitaria 1', 1),
(31, '15.50', 40, 1, 'Chocolate Nestle ', 1),
(139, '14.40', 1, 0, 'Prueba Unitaria 1', 1),
(140, '14.40', 3, 1, 'Prueba Unitaria 3', 1),
(141, '14.40', 4, 1, 'Prueba Unitaria 4', 1),
(142, '14.40', 5, 1, 'Prueba Unitaria 5', 1),
(143, '14.40', 6, 1, 'Prueba Unitaria 6', 1),
(144, '14.40', 0, 1, 'Prueba Unitaria 0', 1),
(145, '14.40', 8, 1, 'Prueba Unitaria 8', 1),
(146, '14.40', 9, 1, 'Prueba Unitaria 9', 1),
(147, '14.40', 10, 1, 'Prueba Unitaria 10', 1),
(148, '14.40', 11, 1, 'Prueba Unitaria 11', 1),
(149, '14.40', 12, 1, 'Prueba Unitaria 12', 1),
(150, '14.40', 13, 1, 'Prueba Unitaria 13', 1),
(151, '14.40', 14, 1, 'Prueba Unitaria 14', 1),
(152, '14.40', 15, 0, 'Prueba Unitaria 15', 1),
(153, '14.40', 16, 0, 'Prueba Unitaria 16', 1);


INSERT INTO `proveedor` (`id`, `telefono`, `nif`, `direccion`, `activo`) VALUES
(1, 12345678, 'X8248828Q', 'C/Fernando Paz', 1);


INSERT INTO `proveedor_producto` (`id_proveedor`, `id_producto`) VALUES
(1, 31);
