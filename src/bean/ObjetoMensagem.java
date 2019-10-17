package bean;

public abstract class ObjetoMensagem {

    private String codigoSolicitacao;
    private String codigoCliente;

    public ObjetoMensagem() {}

    public ObjetoMensagem(String codigoSolicitacao, String codigoCliente) {
        this.codigoSolicitacao = codigoSolicitacao;
        this.codigoCliente = codigoCliente;
    }

    public String getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public void setCodigoSolicitacao(String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public String toString() {
        return "ObjetoMensagem { " +
               "codigoSolicitacao: " + codigoSolicitacao + ", " +
               "codigoCliente: " + codigoCliente + " }";
    }
}
