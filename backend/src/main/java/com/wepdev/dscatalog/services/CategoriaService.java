package com.wepdev.dscatalog.services;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<CategoriaDTO> findAll(){
        List<Categoria> list = repository.findAll();

        /**
         * stream() -> Converte a list em uma stream que trabalha com funções de alta ordem
         * map(x -> new CategoriaDTO(x)) -> Transforma a lista original em outra coisa, aplica uma função em cada elemento da lista. x -> (para cada elemento x da lista original)
         * ele sera levado para a nova list de CategoriaDTO.
         * collect(Collectors.toList()) -> transformando a stream em uma lista
         *
         */ // Utilizando o stream
//         List<CategoriaDTO> listDto = list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
//         return listDto;

        // Retorno direto
         return list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());

         //Forma tradicional de fazer
//        List<CategoriaDTO> listDto = new ArrayList<>(); // Cria uma lista de CategoriaDTO
//        for(Categoria cat : list){
//            listDto.add(new CategoriaDTO(cat)); // Adicionando cada Categoria na lista de CategoriaDTO, existe um construtor na CategoriaDTO que recebe como argumento uma Categoria
//        }
//         return listDto;
    }
}
