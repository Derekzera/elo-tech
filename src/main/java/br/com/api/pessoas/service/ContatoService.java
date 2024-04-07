package br.com.api.pessoas.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.pessoas.entidade.Contato;
import br.com.api.pessoas.entidade.RespostaModelo;
import br.com.api.pessoas.repository.ContatoRepository;
import br.com.api.pessoas.util.EmailValidation;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private RespostaModelo respostaModelo;

    // Cadastrar ou alterar contatos
    public ResponseEntity<?> cadastrarAlterar(Contato contato, String acao) {

        String regexPattern = "^(.+)@(\\S+)$";

        if (!EmailValidation.patternMatches(contato.getEmail(), regexPattern)) {
            respostaModelo.setMensagem("Formato de e-mail incorreto");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        if (contato.getNome().equals("")) {
            respostaModelo.setMensagem("O nome do contato é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (contato.getEmail().equals("")) {
            respostaModelo.setMensagem("O email é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (contato.getTelefone().equals("")) {
            respostaModelo.setMensagem("O número de telefone é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        if (acao.equals("cadastrar")) {
            return new ResponseEntity<Contato>(contatoRepository.save(contato), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Contato>(contatoRepository.save(contato), HttpStatus.OK);
        }

    }

    // Remover contato por id
    public ResponseEntity<RespostaModelo> remover(Long id) {

        contatoRepository.deleteById(id);
        respostaModelo.setMensagem("O contato foi removido com sucesso!");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
    }

    // Listar contatos
    public Iterable<Contato> listar() {
        return contatoRepository.findAll();
    }

    // Listar contatos por id
    public Optional<Contato> listarId(Long id) {
        return contatoRepository.findById(id);
    }

    // Lista contatos paginada
    public Iterable<Contato> listarPage(int page, int size) {
        return contatoRepository.findAll();
    }

    public Page<Contato> getContato(int page, int size) {

        Pageable pageRequest = createPageRequestUsing(page, size);

        List<Contato> allCustomers = contatoRepository.findAll();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allCustomers.size());

        List<Contato> pageContent = allCustomers.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allCustomers.size());
    }

    public List<Contato> getPessoaListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);
        Page<Contato> allCustomers = contatoRepository.findAll(pageRequest);

        return allCustomers.hasContent() ? allCustomers.getContent() : Collections.emptyList();
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

}
