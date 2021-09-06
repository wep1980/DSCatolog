package com.wepdev.dscatalog.services;

import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Registra a classe como componente do sistema de injeção do Spring
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    /**
     * Garante que o metodo vai executar em uma transaçao com o banco de dados.
     * readOnly = true -> Evita o locking(travamento com o banco de dodos) em opereções de leitura
     * @return
     */
    @Transactional(readOnly = true)
    public List<Categoria> findAll(){
        return repository.findAll();
    }
}
