package br.com.api.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.pessoas.entidade.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
