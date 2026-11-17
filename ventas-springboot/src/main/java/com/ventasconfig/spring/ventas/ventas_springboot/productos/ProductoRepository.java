package com.ventasconfig.spring.ventas.ventas_springboot.productos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}