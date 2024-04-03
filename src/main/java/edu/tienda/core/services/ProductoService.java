package edu.tienda.core.services;

import edu.tienda.core.domain.Producto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductoService {

    public List<Producto> getProductos();
    public void saveProducto(Producto producto);
    public void deleteProductoById(Integer id);
    public void updateProducto(Producto producto);
}
