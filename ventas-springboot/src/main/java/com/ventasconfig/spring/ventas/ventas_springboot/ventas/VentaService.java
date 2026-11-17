package com.ventasconfig.spring.ventas.ventas_springboot.ventas;

import com.ventasconfig.spring.ventas.ventas_springboot.comun.exception.BusinessException;
import com.ventasconfig.spring.ventas.ventas_springboot.comun.exception.ResourceNotFoundException;
import com.ventasconfig.spring.ventas.ventas_springboot.productos.Producto;
import com.ventasconfig.spring.ventas.ventas_springboot.productos.ProductoRepository;
import com.ventasconfig.spring.ventas.ventas_springboot.ventas.dto.VentaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id " + id));
    }

    @Transactional
    public Venta registrar(VentaRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con id " + request.productoId()));

        if (producto.getStock() < request.cantidad()) {
            throw new BusinessException(
                    "Stock insuficiente. Disponible: " + producto.getStock()
                    + ", solicitado: " + request.cantidad());
        }

        // Al estar dentro de la transacción, este cambio se guarda automáticamente
        producto.setStock(producto.getStock() - request.cantidad());

        BigDecimal total = producto.getPrecio()
                .multiply(BigDecimal.valueOf(request.cantidad()))
                .setScale(2, RoundingMode.HALF_UP);

        Venta venta = new Venta();
        venta.setProductoId(producto.getId());
        venta.setCantidad(request.cantidad());
        venta.setTotal(total);
        venta.setFecha(LocalDateTime.now());

        return ventaRepository.save(venta);
    }
}