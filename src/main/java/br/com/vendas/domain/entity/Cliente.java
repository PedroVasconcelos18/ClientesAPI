package br.com.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity // quando a tabela tem o mesmo nome da entidade apenas a annotation Entity serve
@Table(name = "cliente")//, schema = "nome do shcema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    // Quando existe a annotation Entity na classe ela entende
    // que as propriedades dessa classe são as colunas da sua tabela

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") //Mesma lógica da table, se o nome for o mesmo da coluna na base de dados, já faz a ligação automática
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "Campo nome é obrigatório.")
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    // O FetchType.LAZY faz com que toda vez que se obtenha os clientes da base de dados ele n vai trazer o pedido
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Pedido> pedidos;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
