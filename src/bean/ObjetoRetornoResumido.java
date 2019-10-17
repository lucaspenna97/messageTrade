package bean;

public class ObjetoRetornoResumido extends ObjetoMensagem {

    private String validadeChave;
    private String restricaoFinanceira;
    private String bloqueioSistema;

    public ObjetoRetornoResumido(){}

    public ObjetoRetornoResumido(String validadeChave, String restricaoFinanceira, String bloqueioSistema){
        this.validadeChave = validadeChave;
        this.restricaoFinanceira = restricaoFinanceira;
        this.bloqueioSistema = bloqueioSistema;
    }

    public String getValidadeChave() {
        return validadeChave;
    }

    public void setValidadeChave(String validadeChave) {
        this.validadeChave = validadeChave;
    }

    public String getRestricaoFinanceira() {
        return restricaoFinanceira;
    }

    public void setRestricaoFinanceira(String restricaoFinanceira) {
        this.restricaoFinanceira = restricaoFinanceira;
    }

    public String getBloqueioSistema() {
        return bloqueioSistema;
    }

    public void setBloqueioSistema(String bloqueioSistema) {
        this.bloqueioSistema = bloqueioSistema;
    }

    @Override
    public String toString() {
        return "ObjetoRetornoResumido { " +
                "validadeChave: "       + validadeChave       + ", " +
                "restricaoFinanceira: " + restricaoFinanceira + ", " +
                "bloqueioSistema: "     + bloqueioSistema     + " }";
    }
}
