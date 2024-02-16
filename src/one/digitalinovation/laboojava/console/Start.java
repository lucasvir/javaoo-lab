package one.digitalinovation.laboojava.console;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.*;
import one.digitalinovation.laboojava.negocio.ClienteNegocio;
import one.digitalinovation.laboojava.negocio.PedidoNegocio;
import one.digitalinovation.laboojava.negocio.ProdutoNegocio;
import one.digitalinovation.laboojava.utilidade.LeitoraDados;

import java.util.Optional;

/**
 * Classe responsável por controlar a execução da aplicação.
 *
 * @author thiago leite
 */
public class Start {

    private static Cliente clienteLogado = null;

    private static Banco banco = new Banco();

    private static ClienteNegocio clienteNegocio = new ClienteNegocio(banco);
    private static PedidoNegocio pedidoNegocio = new PedidoNegocio(banco);
    private static ProdutoNegocio produtoNegocio = new ProdutoNegocio(banco);

    /**
     * Método utilitário para inicializar a aplicação.
     *
     * @param args Parâmetros que podem ser passados para auxiliar na execução.
     */
    public static void main(String[] args) {

        System.out.println("Bem vindo ao e-Compras");

        String opcao = "";

        System.out.println("Você já tem cadastro? (s/n)");
        String cadastro = LeitoraDados.lerDado();

        if (cadastro.equalsIgnoreCase("n")) {
            Cliente cliente = LeitoraDados.lerCliente();
            clienteNegocio.cadastrar(cliente);
        }

        while (true) {

            while(clienteLogado == null) {
                System.out.println("Login");
                System.out.println("Digite o cpf:");

                String cpf = "";
                cpf = LeitoraDados.lerDado();

                identificarUsuario(cpf);
            }

            System.out.println("Selecione uma opção:");
            System.out.println("1 - Cadastrar Livro");
            System.out.println("2 - Excluir Livro");
            System.out.println("3 - Consultar Livro (nome)");
            System.out.println("4 - Cadastrar Caderno");
            System.out.println("5 - Excluir Caderno");
            System.out.println("6 - Consultar caderno (materia)");
            System.out.println("7 - Fazer pedido");
            System.out.println("8 - Excluir pedido");
            System.out.println("9 - Consultar pedido (código)");
            System.out.println("10 - Listar produtos");
            System.out.println("11 - Listar pedidos");
            System.out.println("12 - Deslogar");
            System.out.println("13 - Sair");
            System.out.println("14 - Excluir Cliente");

            opcao = LeitoraDados.lerDado();

            switch (opcao) {
                case "1":
                    Livro livro = LeitoraDados.lerLivro();
                    produtoNegocio.salvar(livro);
                    break;
                case "2":
                    System.out.println("Digite o código do livro");
                    String codigoLivro = LeitoraDados.lerDado();
                    produtoNegocio.excluir(codigoLivro);
                    break;
                case "3":
//                    System.out.println("Digite o nome do livro");
//                    String nomeLivro = LeitoraDados.lerDado();
//                    Optional<Produto> livroConsulta = produtoNegocio.consultarLivro(nomeLivro);
//                    livroConsulta.ifPresentOrElse(System.out::println, () -> System.out.println("Livro não encontrado"));
                    break;
                case "4":
                    Caderno caderno = LeitoraDados.lerCaderno();
                    produtoNegocio.salvar(caderno);
                    break;
                case "5":
                    System.out.println("Digite o código do caderno");
                    String codigoCaderno = LeitoraDados.lerDado();
                    produtoNegocio.excluir(codigoCaderno);
                    break;
                case "6":
//                    System.out.println("Digite o tipo do caderno (M2, M5 ou M10)");
//                    String tipo = LeitoraDados.lerDado();
//                    Optional<Produto> cadernoConsulta = produtoNegocio.consultarCaderno(tipo);
//                    cadernoConsulta.ifPresentOrElse(System.out::println, () -> System.out.println("Caderno não econtrado."));
                    break;
                case "7":
                    Pedido pedido = LeitoraDados.lerPedido(banco, clienteLogado);
                    Optional<Cupom> cupom = LeitoraDados.lerCupom(banco);

                    if (cupom.isPresent()) {
                        pedidoNegocio.salvar(pedido, cupom.get());
                    } else {
                        pedidoNegocio.salvar(pedido);
                    }
                    break;
                case "8":
                    System.out.println("Digite o código do pedido");
                    String codigoPedido = LeitoraDados.lerDado();
                    pedidoNegocio.excluir(codigoPedido);
                    break;
                case "9":
                    System.out.println("Digite o código do pedido");
                    String codigopedido = LeitoraDados.lerDado();
                    pedidoNegocio.consultarPedido(codigopedido);
                case "10":
                    produtoNegocio.listarTodos();
                    break;
                case "11":
                    pedidoNegocio.listarTodos();
                    break;
                case "12":
                    System.out.println(String.format("Volte sempre %s!", clienteLogado.getNome()));
                    clienteLogado = null;
                    break;
                case "13":
                    System.out.println("Aplicação encerrada.");
                    System.exit(0);
                    break;
                case "14":
                    System.out.println("Digite o cpf do cliente");
                    String cpf = LeitoraDados.lerDado();
                    clienteNegocio.excluirCliente(cpf);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

    }

    /**
     * Procura o usuário na base de dados.
     *
     * @param cpf CPF do usuário que deseja logar na aplicação
     */
    private static void identificarUsuario(String cpf) {

        Optional<Cliente> resultado = clienteNegocio.consultar(cpf);

        if (resultado.isPresent()) {
            Cliente cliente = resultado.get();
            System.out.println(String.format("Olá %s! Você está logado.", cliente.getNome()));
            clienteLogado = cliente;
        } else {
            System.out.println("Usuário não cadastrado.");
//            System.exit(0);
            clienteLogado = null;
        }
    }
}
