package br.com.api.pessoas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.pessoas.entidade.Pessoa;
import br.com.api.pessoas.entidade.RespostaModelo;
import br.com.api.pessoas.service.PessoaService;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PutMapping("pessoa/alterar")
    public ResponseEntity<?> alterar(@RequestBody Pessoa pessoa) {
        return pessoaService.cadastrarAlterar(pessoa, "alterar");
    }

    @PostMapping("pessoa/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa pessoa) {
        return pessoaService.cadastrarAlterar(pessoa, "cadastrar");
    }

    @DeleteMapping("pessoa/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
        return pessoaService.remover(id);
    }

    @GetMapping("pessoa/listar")
    public ResponseEntity<Page<Pessoa>> listar(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Pessoa> customerPage = pessoaService.getPessoa(page, size);

        return ResponseEntity.ok(customerPage);
    }

    @GetMapping("pessoa/listar/{id}")
    public Optional<Pessoa> listarIndividual(@PathVariable long id) {

        return pessoaService.listarId(id);

    }

}
