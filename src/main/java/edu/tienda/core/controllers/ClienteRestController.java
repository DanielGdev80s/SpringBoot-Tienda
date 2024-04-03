package edu.tienda.core.controllers;

import edu.tienda.core.domain.Cliente;
import edu.tienda.core.exceptions.BadRequestException;
import edu.tienda.core.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    private List<Cliente> clientes = new ArrayList<>(Arrays.asList(
            new Cliente("arm", "1234", "Armstrong"),
            new Cliente("ald", "1234", "Aladrin"),
            new Cliente("col", "1234", "Collins")
    ));

    @GetMapping
    public ResponseEntity<?> getClientes() {

        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/{userName}")
    public ResponseEntity<?> getClientes(@PathVariable String userName) {

        if (userName.length() != 3){
            throw new BadRequestException("El parametro nombre de usuario debe tener 3 caracteres");
        }

        for (Cliente cliente : clientes) {
            if (cliente.getUsername().equalsIgnoreCase(userName)) {
                return ResponseEntity.ok(cliente);
            }
        }

        throw new  ResourceNotFoundException("Cliente " + userName + " no encontrado");
//        return ResponseEntity.ok(clientes.stream().
//                filter(cliente -> cliente.getUsername().equalsIgnoreCase(userName))
//                .findFirst()
//                .map(ResponseEntity::ok)
//                .orElseThrow(() -> new ResourceNotFoundException("Cliente " + userName + " no encontrado")));
    }

    @PostMapping
    public ResponseEntity<?> altaCliente(@RequestBody Cliente cliente) {
        clientes.add(cliente);
        // Obteniendo URL del servicio.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userName}")
                .buildAndExpand(cliente.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(cliente);
    }

    @PutMapping
    public ResponseEntity<?> modificacionCliente(@RequestBody Cliente cliente) {
//        for (Cliente cli : clientes) {
//            if (cli.getUsername().equalsIgnoreCase(cliente.getUsername())) {
//                cli.setPassword(cliente.getPassword());
//                cli.setNombre(cliente.getNombre());
//                return ResponseEntity.ok(cli);
//            }
//        }
//        System.out.println("username no encontrado");
//        return null;

        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(cliente.getUsername())).
                findFirst().orElseThrow();

        clienteEncontrado.setPassword(cliente.getPassword());
        clienteEncontrado.setNombre(cliente.getNombre());

        return ResponseEntity.ok(clienteEncontrado);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity deleteCliente(@PathVariable String userName){
//        boolean clienteEncontrado = false;
//        for(int i=0; i < clientes.size(); i++){
//            Cliente cli = clientes.get(i);
//            if(cli.getUsername().equalsIgnoreCase(userName)){
//                clientes.remove(i);
//                clienteEncontrado = true;
//                break;
//            }
//        }
//        if(!clienteEncontrado){
//            System.out.println("Cliente no encontrado");
//        }
        Cliente clienteEncontrado = clientes.stream().
                filter(cli -> cli.getUsername().equalsIgnoreCase(userName)).
                findFirst().orElseThrow();

        clientes.remove(clienteEncontrado);

        return ResponseEntity.noContent().build();
    }

}
