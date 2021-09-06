package com.wepdev.dscatalog.dto;

import com.wepdev.dscatalog.entities.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Classe que faz o acesso ao Resource e o Service, o acesso do recurso e diretamente feito por essa classe
 */
@Getter
@Setter
public class CategoriaDTO implements Serializable {

    private Long id;
    private String nome;



    public CategoriaDTO() {
    }


    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Construtor que ja insere os dados no CategoriaDTO ao passar a Categoria(Entidade) como
     * argumento
     * @param entity
     */
    public CategoriaDTO(Categoria entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }


}
