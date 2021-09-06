package com.wepdev.dscatalog.services;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.repositories.CategoriaRepository;
import com.wepdev.dscatalog.services.exceptions.EntidadeEmUsoException;
import com.wepdev.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id) {
        Optional<Categoria> obj = repository.findById(id); // O retorno dessa busca e um objeto Optional que nunca e nulo
        /*
        Acessa o objeto que esta dentro Categoria, se ele nao existir lança a exception customizada
         */
        Categoria entity = obj.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setNome(dto.getNome());
        entity = repository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO update(Long id ,CategoriaDTO dto) {
        try {
        /*
        Instancia um objeto provisorio com os dados e o id, a vantagem disso e que ele nao acessa o banco de dados,
        o que evita que ele acesse o banco para buscar e depois salvar
         */
            Categoria entity = repository.getOne(id);
            entity.setNome(dto.getNome());
            entity = repository.save(entity);

            return new CategoriaDTO(entity);
        }catch (EntityNotFoundException e){
            throw new EntidadeNaoEncontradaException("Id não encontrado " + id);
        }

    }

    /**
     * Nao foi colocado o @Transactional para poder ser capturada excessao vinda do banco de dados
     * @param id
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException("Id não encontrado " + id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException("Entidade com id em uso" + id);

        }

    }
}
