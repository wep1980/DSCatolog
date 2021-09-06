package com.wepdev.dscatalog.resources;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController // Controlador Rest onde fica os endpoints
@RequestMapping(value = "/categorias") // Rota do recurso
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    /**
     * ResponseEntity<> -> Encapsula uma resposta HTTP, e o corpo da resposta
     */
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<CategoriaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * ResponseEntity<> -> Encapsula uma resposta HTTP, e o corpo da resposta
     * @PathVariable Long id -> concatena o id passado no postman com a variavel id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity <CategoriaDTO> findById(@PathVariable Long id){
        CategoriaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping
    public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri(); // Enviando o caminho do recurso no cabeçalho da resposta
        return ResponseEntity.created(uri).body(dto);
    }

}
