package com.ventasconfig.spring.ventas.ventas_springboot.ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record VentaRequest(
        @NotNull(message = "El productoId es obligatorio")
        Integer productoId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer cantidad
) {}
