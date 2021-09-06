package com.wepdev.dscatalog.services;

import com.wepdev.dscatalog.dto.CategoriaDTO;
import com.wepdev.dscatalog.dto.ProdutoDTO;
import com.wepdev.dscatalog.entities.Categoria;
import com.wepdev.dscatalog.entities.Produto;
import com.wepdev.dscatalog.repositories.CategoriaRepository;
import com.wepdev.dscatalog.repositories.ProdutoRepository;
import com.wepdev.dscatalog.services.exceptions.EntidadeEmUsoException;
import com.wepdev.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Registra a classe como componente do sistema de injeção do Spring
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    /**
     * Garante que o metodo vai executar em uma transaçao com o banco de dados.
     * readOnly = true -> Evita o locking(travamento com o banco de dodos) em opereções de leitura
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAll(){
        List<Produto> list = repository.findAll();

        /**
         * stream() -> Converte a list em uma stream que trabalha com funções de alta ordem
         * map(x -> new ProdutoDTO(x)) -> Transforma a lista original em outra coisa, aplica uma função em cada elemento da lista. x -> (para cada elemento x da lista original)
         * ele sera levado para a nova list de CategoriaDTO.
         * collect(Collectors.toList()) -> transformando a stream em uma lista
         *
         */ // Utilizando o stream
//         List<ProdutoDTO> listDto = list.stream().map(x -> new ProdutoDTO(x)).collect(Collectors.toList());
//         return listDto;

        // Retorno direto
         return list.stream().map(x -> new ProdutoDTO(x)).collect(Collectors.toList());

         //Forma tradicional de fazer
//        List<ProdutoDTO> listDto = new ArrayList<>(); // Cria uma lista de ProdutoDTO
//        for(Produto prod : list){
//            listDto.add(new ProdutoDTO(cat)); // Adicionando cada Categoria na lista de ProdutoDTO, existe um construtor na ProdutoDTO que recebe como argumento um Produto
//        }
//         return listDto;
    }


    /**
     * Como o Page ja e um stream nao e necessario colocar o stream() e o collect(Collectors.toList(),
     * como no metodo do findAll() acima
     * @param pageRequest
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAllPaged(PageRequest pageRequest) {

        Page<Produto> list = repository.findAll(pageRequest);

        return list.map(x -> new ProdutoDTO(x));
    }


    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        Optional<Produto> obj = repository.findById(id); // O retorno dessa busca e um objeto Optional que nunca e nulo
        /*
        Acessa o objeto que esta dentro Categoria, se ele nao existir lança a exception customizada
         */
        Produto entity = obj.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
        return new ProdutoDTO(entity, entity.getCategorias()); // Retonando tambem as categorias do produto com a chamada do construtor especifico do ProdutoDTO
    }


    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto) {
        Produto entity = new Produto();
        //entity.setNome(dto.getNome());
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }


    @Transactional
    public ProdutoDTO update(Long id ,ProdutoDTO dto) {
        try {
        /*
        Instancia um objeto provisorio com os dados e o id, a vantagem disso e que ele nao acessa o banco de dados,
        o que evita que ele acesse o banco para buscar e depois salvar
         */
            Produto entity = repository.getOne(id);
            //entity.setNome(dto.getNome());
            entity = repository.save(entity);

            return new ProdutoDTO(entity);
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
