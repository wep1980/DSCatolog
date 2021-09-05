package com.wepdev.dscatalog.resources;

import com.wepdev.dscatalog.entities.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController // Controlador Rest onde fica os endpoints
@RequestMapping(value = "/categorias") // Rota do recurso
public class CategoriaResource {

    /**
     * ResponseEntity<> -> Encapsula uma resposta HTTP, e o corpo da resposta
     */
    @GetMapping
    public ResponseEntity<List<Categoria>> findAll(){
        List<Categoria> list = new ArrayList<>();
        list.add(new Categoria(1L, "Livros"));
        list.add(new Categoria(2L, "Eletronicos"));

        return ResponseEntity.ok().body(list);
    }

}
