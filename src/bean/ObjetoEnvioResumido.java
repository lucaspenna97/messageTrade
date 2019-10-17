package bean;

public class ObjetoEnvioResumido extends ObjetoMensagem {

    private String clienteCNPJ;

    public ObjetoEnvioResumido() {}

    ObjetoEnvioResumido(String clienteCNPJ){
        this.clienteCNPJ = clienteCNPJ;
    }

    public String getClienteCNPJ() {
        return clienteCNPJ;
    }

    public void setClienteCNPJ(String clienteCNPJ) {
        this.clienteCNPJ = clienteCNPJ;
    }

    @Override
    public String toString() {
        return "ObjetoEnvioResumido { " +
               "clienteCNPJ: " + clienteCNPJ + " }";
    }
}
