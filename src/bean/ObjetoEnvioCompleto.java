package bean;

public class ObjetoEnvioCompleto extends ObjetoMensagem {

    private String codigoMensagem;
    private String opcoes;
    private String dataEnvio;
    private String horarioEnvio;
    private String usuario;
    private String mensagem;

    public ObjetoEnvioCompleto() {}

    public ObjetoEnvioCompleto(String codigoMensagem, String opcoes, String dataEnvio, String horarioEnvio, String usuario, String mensagem){
        this.codigoMensagem = codigoMensagem;
        this.opcoes = opcoes;
        this.dataEnvio = dataEnvio;
        this.horarioEnvio = horarioEnvio;
        this.usuario = usuario;
        this.mensagem = mensagem;
    }

    public String getCodigoMensagem() {
        return codigoMensagem;
    }

    public void setCodigoMensagem(String codigoMensagem) {
        this.codigoMensagem = codigoMensagem;
    }

    public String getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(String opcoes) {
        this.opcoes = opcoes;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getHorarioEnvio() {
        return horarioEnvio;
    }

    public void setHorarioEnvio(String horarioEnvio) {
        this.horarioEnvio = horarioEnvio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "ObjetoEnvioCompleto { " +
                " codigoMensagem: " + codigoMensagem + ", " +
                " opcoes: "         + opcoes         + ", " +
                " dataEnvio: "      + dataEnvio      + ", " +
                " horarioEnvio: "   + horarioEnvio   + ", " +
                " usuario: "        + usuario        + ", " +
                " mensagem: "       + mensagem       + " }";
    }
}
