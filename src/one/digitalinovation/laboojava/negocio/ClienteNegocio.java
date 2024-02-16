package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cliente;
import one.digitalinovation.laboojava.entidade.Produto;

import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Cliente}.
 * @author thiago leite
 */
public class ClienteNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     * @param banco Banco de dados para ter acesso aos clientes cadastrados
     */
    public ClienteNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    /**
     * Consulta o cliente pelo seu CPF.
     * @param cpf CPF de um cliente
     * @return O cliente que possuir o CPF passado.
     */
    public Optional<Cliente> consultar(String cpf) {
        for (Cliente c : bancoDados.getClientes()) {
            if (c.getCpf().equals(cpf)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    /**
     * Cadastra um novo cliente.
     * @param cliente Novo cliente que terá acesso a aplicação
     */
    public void cadastrar(Cliente cliente) {

        boolean clienteJaCadastrado = false;
        for (Cliente c : bancoDados.getClientes()) {
            if (c.getCpf().equals(cliente.getCpf())) {
                clienteJaCadastrado = true;
                System.out.println("Cliente já cadastrado.");
                break;
            }
        }

        if(!clienteJaCadastrado) {
            this.bancoDados.adicionarCliente(cliente);
            System.out.println("Cliente cadastrado com sucesso.");
        }
    }

    /**
     * Exclui um cliente específico.
     * @param cpf CPF do cliente
     */

    public void excluirCliente(String cpf) {
        this.bancoDados.removerCliente(cpf);
        System.out.println("Cliente excluido com sucesso.");
    }
}
