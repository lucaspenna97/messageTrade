package gui;

public interface Constantes {

    //Diversos
    String VERSAO = "1.0";
    String NOME_TXT = "mensagem.txt";
    String NOME_TXT_RETORNO = "mesagem_retorno.txt";
    String NOME_TXT_ERROR = "errorLog.txt";
    String PATH_MESSAGES = "C:/broadcastMessages";
    String URL_SEND = "http://exemplo...";
    String URL_RECEIVE = "http://exemplo...";
    int TIMEOUT_WEBSERVICE = 10000;

    //Bean ObjetoMensagem
    String CODIGO_SOLICITACAO = "codigoSolicitacao";
    String CODIGO_CLIENTE = "codigoCliente";

    //Bean ObjetoEnvioResumido
    String CLIENTE_CNPJ = "clienteCNPJ";

    //Bean ObjetoEnvioCompleto
    String CODIGO_MENSAGEM = "codigoMensagem";
    String OPCOES = "opcoes";
    String DATA_ENVIO = "dataEnvio";
    String HORARIO_ENVIO = "horarioEnvio";
    String USUARIO = "usuario";
    String MENSAGEM = "mensagem";

    //Bean ObjetoRetornoResumido
    String VALIDADE_CHAVE = "validadeChave";
    String RESTRICAO_FINANCEIRA = "restricaoFinanceira";
    String BLOQUEIO_SISTEMA = "bloqueioSistema";

    //Bean ObjetoRetornadoCompleto
    String CODIGO_MENSAGEM_RETORNO = "codigoMensagem";
    String OPCOES_RETORNO = "opcoes";
    String DATA_MENSAGEM = "dataMensagem";
    String HORA_MENSAGEM = "horaMensagem";
    String REMETENTE = "remetente";
    String DESTINATARIO = "destinatario";
    String MENSAGEM_RETORNO = "mensagem";


}
