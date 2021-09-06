package com.wepdev.dscatalog.resources;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     * @PathVariable Long id -> concatena o id passado no postman com a variavel id, ele vai na url com a /
     * parametro de URL, o dado que sera passado na requisão e obrigatório
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity <CategoriaDTO> findById (@PathVariable Long id){
        CategoriaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping
    public ResponseEntity<CategoriaDTO> insert (@RequestBody CategoriaDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri(); // Enviando o caminho do recurso no cabeçalho da resposta
        return ResponseEntity.created(uri).body(dto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update (@PathVariable Long id, @RequestBody CategoriaDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * @RequestParam -> é um dado passado na requisição que não obrigatório ser passado(Opicional)
     * value = "page", defaultValue = "0" -> se o valor da pagina nao for informado a pagina inicial listada sera a 0.
     * value = "linesPerPage", defaultValue = "12" -> Quantidade de 12 registros por pagina.
     * value = "orderBy", defaultValue = "nome" -> A Categoria sera ordenada por Nome.
     * value = "direction", defaultValue = "DESC" -> DESC ordena em ordem decrescente de nome, ASC ordena em ordem crescente de nome
     * @return
     */
    @GetMapping(value = "paginada")
    public ResponseEntity<Page<CategoriaDTO>> findAll(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "12") Integer linhasPorPagina,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor

    ){
        /**
         * Instanciando o objeto com o metodo de builder of() do proprio objeto
         */
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        Page<CategoriaDTO> list = service.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

}
