package edu.tienda.core.controllers;

import edu.tienda.core.configurations.ConfigurationsParameters;
import edu.tienda.core.domain.Producto;
import edu.tienda.core.services.ProductoService;
import edu.tienda.core.services.ProductosServiceImpl;
import edu.tienda.core.services.ProductosServiceBDImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {

    @Autowired
    @Lazy
    //@Qualifier("MEMORY")
    private ProductoService productosService;

    @Autowired
    private ConfigurationsParameters configurationsParameters;

    @GetMapping
    public ResponseEntity<?> getProductos(){

        //solo para probar el funcinamiento del bean ConfigurationsParameters
        System.out.println("params " + configurationsParameters.toString());

        //List<Producto> productos = productosService.getProductos();

        return ResponseEntity.ok(productosService.getProductos());
    }


    //para ver como se recibe un api externa (la esta procesando ProductosServiceImplApiExterna)
    @GetMapping("/fake-productos")
    public ResponseEntity<?> fakeProductosAPI(){

        List<Producto> productos = new ArrayList<>(Arrays.asList(
           new Producto(1,"Camiseta Juventus",1200.0,4),
           new Producto(2,"Camiseta River Plate", 1000.0,8),
           new Producto(3, "Camiseta Boca Juniors", 900.0,1)
        ));

        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> altaProducto(@RequestBody Producto producto) {

        productosService.saveProducto(producto);

        //Obtener URI para que la respuesta sea profesional??

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(producto.getId())
                .toUri();

        return ResponseEntity.created(location).body(producto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id){
        productosService.deleteProductoById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id); // Asegúrate de que el ID del producto sea el mismo que el ID proporcionado en la URL
        productosService.updateProducto(producto);
        return ResponseEntity.ok().build();
    }

}
