package com.ventasconfig.spring.ventas.ventas_springboot.comun.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
