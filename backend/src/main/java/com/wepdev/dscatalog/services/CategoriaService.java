package com.wepdev.dscatalog.services;

import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Registra a classe como componente do sistema de injeção do Spring
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;



    public List<Categoria> findAll(){
        return repository.findAll();
    }
}
