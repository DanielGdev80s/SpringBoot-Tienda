package edu.tienda.core.domain;

import lombok.*;

//@Setter
//@Getter
//@ToString
//@EqualsAndHashCode
@Data //Reemplaza a las 4 de arriba
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;

}
