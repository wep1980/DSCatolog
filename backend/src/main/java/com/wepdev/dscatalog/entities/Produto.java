package com.wepdev.dscatalog.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data // Anotacao do LOMBOK que possui gets , sets , equals&HashCode e ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Habilita os campos explicidamente que serao utilizados no Equals e hashcode
@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // O Campo id sera o unico utilizado no equals e hashcode
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT") // Aceita valores em texto mais longos
    private String descricao;
    private Double preco;
    private String imgUrl;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // Sera armazenado no banco por padrão no horario UTC
    private Instant data;

    /*
    Set e um tipo de conjunto que não aceita repetições, por isso o uso dele no lugar do List
    Em uma relação de tabelas muitos para muitos (*..*) e criada uma terceira tabela no meio, que contem os ids das 2 tabelas
     */
    @Setter(value = AccessLevel.NONE) // Esse atributo não gera o set()
    @ManyToMany
    @JoinTable(name = "tb_produto_categoria", // Nome da tabela que sera criada
    joinColumns = @JoinColumn(name = "produto_id"), // nome da chave estrangeira relacionada a classe Produto
    inverseJoinColumns = @JoinColumn(name = "categoria_id")) // nome da chave estrangeira relacionada a classe Categoria
    Set<Categoria> categorias = new HashSet<>();


    public Produto() {
    }


    public Produto(Long id, String nome, String descricao, Double preco, String imgUrl, Instant data) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imgUrl = imgUrl;
        this.data = data;
    }



}
