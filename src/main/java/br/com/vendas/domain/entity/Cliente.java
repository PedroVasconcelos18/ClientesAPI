package br.com.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity // quando a tabela tem o mesmo nome da entidade apenas a annotation Entity serve
@Table(name = "cliente")//, schema = "nome do shcema")
public class Cliente {

    // Quando existe a annotation Entity na classe ela entende
    // que as propriedades dessa classe são as colunas da sua tabela

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") //Mesma lógica da table, se o nome for o mesmo da coluna na base de dados, já faz a ligação automática
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    // O FetchType.LAZY faz com que toda vez que se obtenha os clientes da base de dados ele n vai trazer o pedido
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Pedido> pedidos;

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public Cliente() {
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
