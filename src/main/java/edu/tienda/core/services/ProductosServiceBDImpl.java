package edu.tienda.core.services;

import edu.tienda.core.domain.Producto;
import edu.tienda.core.persistance.entities.ProductoEntity;
import edu.tienda.core.persistance.repositories.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BD")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_BD"
)
public class ProductosServiceBDImpl implements ProductoService {

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<Producto> getProductos() {
        List<ProductoEntity> productosEntities = productosRepository.findAll();
        //Este metodo este metodo funciona sin codearlo por que esta en repository y usa las palabras findBy y LessThan y Precio que es el nombre del atributo
        //List<ProductoEntity> productosEntities = productosRepository.findByPrecioLessThan(50.0);

        //List<ProductoEntity> productosEntities = productosRepository.findByNombreLike("Teclado");

        //List<ProductoEntity> productosEntities = productosRepository.FindByPrecionGreaterThanAndStockLessThan(1000, 10);

        List<Producto> productos = new ArrayList<>();

        for (ProductoEntity productEntity : productosEntities) {
            Producto producto = new Producto();
            producto.setId(productEntity.getId());
            producto.setNombre(productEntity.getNombre());
            producto.setPrecio(productEntity.getPrecio());
            producto.setStock(productEntity.getStock());
            productos.add(producto);
        }

        return productos;
    }

    @Override
    public void saveProducto(Producto producto) {
        //Mapeo de Producto a ProductoEntity
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombre(producto.getNombre());
        productoEntity.setPrecio(producto.getPrecio());
        productoEntity.setStock(producto.getStock());

        //Persistencia
        productosRepository.save(productoEntity);
    }

    @Override
    public void deleteProductoById(Integer id) {
        productosRepository.deleteById(id);
    }

    @Override
    public void updateProducto(Producto producto) {
        ProductoEntity productoEntity = productosRepository.findById(producto.getId()).orElse(null);

        if (productoEntity != null) {
            productoEntity.setNombre(producto.getNombre());
            productoEntity.setPrecio(producto.getPrecio());
            productoEntity.setStock(producto.getStock());

            productosRepository.save(productoEntity);
        }
    }
}
