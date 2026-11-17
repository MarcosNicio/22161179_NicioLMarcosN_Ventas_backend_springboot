package com.ventasconfig.spring.ventas.ventas_springboot.ventas;


import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
}