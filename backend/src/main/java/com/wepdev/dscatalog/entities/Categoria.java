package com.wepdev.dscatalog.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Data // Anotacao do LOMBOK que possui gets , sets , equals&HashCode e ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Habilita os campos explicidamente que serao utilizados no Equals e hashcode
@Entity
@Table(name = "tb_categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Quem gera a chave e o provedor do banco de dados
    @EqualsAndHashCode.Include // O Campo id sera o unico utilizado no equals e hashcode
    private Long id;

    private String nome;

    @Setter(value = AccessLevel.NONE) // Esse atributo não gera o set()
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // Sera armazenado no banco por padrão no horario UTC
    private Instant criadoEm;

    @Setter(value = AccessLevel.NONE) // Esse atributo não gera o set()
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // Sera armazenado no banco por padrão no horario UTC
    private Instant atualizadoEm;


    public Categoria() {
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    /**
     * Sempre que o metodo do JPA save() for chamado, ou seja salvar uma Categoria e for a primeira vez, esse metodo sera invocado, para salvar a data e hora atual
     * @PrePersist -> antes de salvar
     */
   @PrePersist
   public void prePersist(){
        criadoEm = Instant.now();
   }

    /**
     * Sempre que o metodo do JPA save() for chamado para atualizar, ou seja atualizar uma Categoria esse metodo sera invocado para atualizar a data e hora atual
     * @PreUpdate -> antes de atualizar
     */
    @PreUpdate
    public void preUpdate(){
        atualizadoEm = Instant.now();
   }


}
