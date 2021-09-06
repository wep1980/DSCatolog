package com.wepdev.dscatalog.dto;

import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.entities.Produto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProdutoDTO implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String imgUrl;
    private Instant data;

    private List<CategoriaDTO> categorias = new ArrayList<>();



    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String descricao, Double preco, String imgUrl, Instant data) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.data = data;
    }


    public ProdutoDTO(Produto entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.imgUrl = entity.getImgUrl();
        this.data = entity.getData();
    }


    public ProdutoDTO(Produto entity, Set<Categoria> categorias){
         this(entity); // Chama o construtor que recebe so a entidade Produto
        /**
         * Transforma cada elemento de categorias para um CategoriaDTO e adiciona na lista
         */
        categorias.forEach(cat -> this.categorias.add(new CategoriaDTO(cat)));
    }
}
