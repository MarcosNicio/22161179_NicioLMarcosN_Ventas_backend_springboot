package com.ventasconfig.spring.ventas.ventas_springboot.productos;

import com.ventasconfig.spring.ventas.ventas_springboot.comun.exception.BusinessException;
import com.ventasconfig.spring.ventas.ventas_springboot.comun.exception.ResourceNotFoundException;
import com.ventasconfig.spring.ventas.ventas_springboot.productos.dto.ProductoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + id));
    }

    public Producto crear(ProductoRequest request) {
        Producto producto = new Producto();
        aplicarDatos(producto, request);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Integer id, ProductoRequest request) {
        Producto producto = buscarPorId(id);
        aplicarDatos(producto, request);
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Integer id) {
        Producto producto = buscarPorId(id);
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(
                    "No se puede eliminar el producto porque tiene ventas asociadas",
                    HttpStatus.CONFLICT);
        }
    }

    private void aplicarDatos(Producto producto, ProductoRequest request) {
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
    }
}