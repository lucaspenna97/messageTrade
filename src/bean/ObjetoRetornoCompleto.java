package bean;

public class ObjetoRetornoCompleto extends ObjetoMensagem{

    private String codigoMensagem;
    private String opcoes;
    private String dataMensagem;
    private String horaMensagem;
    private String remetente;
    private String destinatario;
    private String mensagem;

    public ObjetoRetornoCompleto(){}

    public ObjetoRetornoCompleto(String codigoMensagem, String opcoes, String dataMensagem, String horaMensagem, String remetente, String destinatario, String mensagem){
        this.codigoMensagem = codigoMensagem;
        this.opcoes = opcoes;
        this.dataMensagem = dataMensagem;
        this.horaMensagem = horaMensagem;
        this.remetente = remetente;
        this.destinatario = destinatario;
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

    public String getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(String dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getHoraMensagem() {
        return horaMensagem;
    }

    public void setHoraMensagem(String horaMensagem) {
        this.horaMensagem = horaMensagem;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "ObjetoRetornoCompleto { " +
                "codigoMensagem: " + codigoMensagem + ", " +
                "opcoes: "         + opcoes         + ", " +
                "dataMensagem: "   + dataMensagem   + ", " +
                "horaMensagem: "   + horaMensagem   + ", " +
                "remetente: "      + remetente      + ", " +
                "destinatario: "   + destinatario   + ", " +
                "mensagem: "       + mensagem       + " }";
    }
}
