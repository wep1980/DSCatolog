package com.wepdev.dscatalog.resources;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
