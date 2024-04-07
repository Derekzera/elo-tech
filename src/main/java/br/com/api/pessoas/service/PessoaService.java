package br.com.api.pessoas.service;

import java.time.LocalDate;
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

import br.com.api.pessoas.entidade.Pessoa;
import br.com.api.pessoas.entidade.RespostaModelo;
import br.com.api.pessoas.repository.PessoaRepository;
import br.com.api.pessoas.util.ValidarCPF;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private RespostaModelo respostaModelo;

    // Listar pessoas
    public Iterable<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    // Cadastrar ou alterar pessoas
    public ResponseEntity<?> cadastrarAlterar(Pessoa pessoa, String acao) {

        // primeiro teste
        if (pessoa.getCpf() != null && !ValidarCPF.isCPF(pessoa.getCpf())) {
            respostaModelo.setMensagem("Erro, CPF inválido!");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (pessoa.getCpf().equals("")) {
            respostaModelo.setMensagem("O CPF da Pessoa é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (pessoa.getCpf().equals("")) {
            respostaModelo.setMensagem("O CPF da Pessoa é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (pessoa.getDataNascimento().isAfter(LocalDate.now())) {
            respostaModelo.setMensagem("A data de nascimento não pode ser uma data futura");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else {
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<Pessoa>(pessoaRepository.save(pessoa), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Pessoa>(pessoaRepository.save(pessoa), HttpStatus.OK);
            }
        }

    }

    // Remover pessoas por id
    public ResponseEntity<RespostaModelo> remover(Long id) {

        pessoaRepository.deleteById(id);
        respostaModelo.setMensagem("A pessoa foi removida com sucesso!");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
    }

    // Listar pessoas por id
    public Optional<Pessoa> listarId(Long id) {
        return pessoaRepository.findById(id);
    }

    // Listar pessoas paginada
    public Iterable<Pessoa> listarPage(int page, int size) {
        return pessoaRepository.findAll();
    }

    public Page<Pessoa> getPessoa(int page, int size) {

        Pageable pageRequest = createPageRequestUsing(page, size);

        List<Pessoa> allCustomers = pessoaRepository.findAll();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allCustomers.size());

        List<Pessoa> pageContent = allCustomers.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allCustomers.size());
    }

    public List<Pessoa> getPessoaListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);
        Page<Pessoa> allCustomers = pessoaRepository.findAll(pageRequest);

        return allCustomers.hasContent() ? allCustomers.getContent() : Collections.emptyList();
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

}
