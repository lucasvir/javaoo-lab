package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cupom;
import one.digitalinovation.laboojava.entidade.Pedido;
import one.digitalinovation.laboojava.entidade.Produto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe para manipular a entidade {@link Pedido}.
 *
 * @author thiago leite
 */
public class PedidoNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     *
     * @param banco Banco de dados para ter armazenar e ter acesso os pedidos
     */
    public PedidoNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    private double calcularTotal(List<Produto> produtos, Cupom cupom) {

        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.calcularFrete();
        }

        if (cupom != null) {
            return total * (1 - cupom.getDesconto());
        } else {
            return total;
        }
    }

    /**
     * Salva um novo pedido sem cupom de desconto.
     *
     * @param novoPedido Pedido a ser armazenado
     */
    public void salvar(Pedido novoPedido) {
        salvar(novoPedido, null);
    }

    /**
     * Salva um novo pedido com cupom de desconto.
     *
     * @param novoPedido Pedido a ser armazenado
     * @param cupom      Cupom de desconto a ser utilizado
     */
    public void salvar(Pedido novoPedido, Cupom cupom) {

        String codigo = "PE%4d%02d%04d";
        LocalDate hoje = LocalDate.now();
        codigo = String.format(
                codigo,
                hoje.getYear(),
                hoje.getMonthValue(),
                bancoDados.getPedidos().length
        );

        novoPedido.setCodigo(codigo);

        double totalPedido = calcularTotal(novoPedido.getProdutos(), cupom);
        novoPedido.setTotal(totalPedido);

        bancoDados.adicionarPedido(novoPedido);
        System.out.println("Pedido concluído com sucesso.");
    }

    /**
     * Exclui um pedido a partir de seu código de rastreio.
     *
     * @param codigo Código do pedido
     */
    public void excluir(String codigo) {

        int pedidoExclusao = -1;
        for (int i = 0; i < bancoDados.getPedidos().length; i++) {

            Pedido pedido = bancoDados.getPedidos()[i];
            if (pedido.getCodigo().equals(codigo)) {
                pedidoExclusao = i;
                break;
            }
        }

        if (pedidoExclusao != -1) {
            bancoDados.removerPedido(pedidoExclusao);
            System.out.println("Pedido excluído com sucesso.");
        } else {
            System.out.println("Pedido inexistente.");
        }
    }

    /**
     * Lista todos os pedidos realizados.
     */

    public void listarTodos() {
        if (bancoDados.getPedidos().length == 0)
            System.out.println("Não a pedidos cadastrados");

        for (Pedido p : bancoDados.getPedidos())
            System.out.println(p.toString());
    }

    /**
     * Consultar um pedido a partir de seu código de rastreio.
     *
     * @param codigo Código do pedido
     */
    public void consultarPedido(String codigo) {
        Pedido pedido = null;
        for (Pedido p : bancoDados.getPedidos()) {
            if (p.getCodigo().equalsIgnoreCase(codigo))
                pedido = p;
        }

        if (pedido == null) {
            System.out.println("Pedido não encontrado");
        } else {
            System.out.println(pedido);
        }
    }
}
