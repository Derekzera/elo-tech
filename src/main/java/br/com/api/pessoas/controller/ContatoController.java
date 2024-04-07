package br.com.api.pessoas.controller;

import java.util.Optional;

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

import br.com.api.pessoas.entidade.Contato;
import br.com.api.pessoas.entidade.RespostaModelo;
import br.com.api.pessoas.service.ContatoService;

@RestController
@CrossOrigin(origins = "*")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @PutMapping("contato/alterar")
    public ResponseEntity<?> alterar(@RequestBody Contato contato) {
        return contatoService.cadastrarAlterar(contato, "alterar");
    }

    @PostMapping("contato/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Contato contato) {
        return contatoService.cadastrarAlterar(contato, "cadastrar");
    }

    @DeleteMapping("contato/remover/{id}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
        return contatoService.remover(id);
    }

    @GetMapping("contato/listar")
    public ResponseEntity<Page<Contato>> listar(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Contato> customerPage = contatoService.getContato(page, size);

        return ResponseEntity.ok(customerPage);
    }

    @GetMapping("contato/listar/{id}")
    public Optional<Contato> listarIndividual(@PathVariable long id) {

        return contatoService.listarId(id);

    }

}
