package gui.ClientSide;

import bean.ObjetoEnvioCompleto;
import bean.ObjetoEnvioResumido;
import bean.ObjetoRetornoCompleto;
import bean.ObjetoRetornoResumido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.EnterpriseSide.FindMessages;
import gui.Constantes;
import gui.ErrorLog;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ReceiveMessages {

    public ReceiveMessages(boolean receiveMessagesForever, boolean receiveMessagesOneTime){

        //Inicia o processo atraves do metodo construtor
        if (receiveMessagesForever) receivingMessagesForever();
        if (receiveMessagesOneTime) receivingMessagesOneTime();
    }

    private static void receivingMessagesForever() {

        final String diretorio = Constantes.PATH_MESSAGES;

        while (true) {

            try {

                //Thread sleep for 2.5 seconds
                Thread.sleep(2500);

                if (!ReceiveMessages.internetConnected()) {
                    System.err.println("Sem conexão com a internet, não é possivel enviar a mensagem.");
                    continue;
                }

                File file = new File(diretorio);
                File[] listFiles = file.listFiles();

                //Iteração na lista de arquivos
                if (listFiles == null || listFiles.length == 0) {
                    System.err.println("Não há nenhum arquivo na pasta.");
                    continue;
                }

                for (File selectedFile : listFiles) {

                    if (!selectedFile.getName().equalsIgnoreCase(Constantes.NOME_TXT)) continue;

                    System.out.println("Arquivo " + Constantes.NOME_TXT + " encontrado.");

                    //Lê e intera informações em objeto
                    readTxtClient(selectedFile);

                    //Exclui arquivo da pasta
                    boolean deletedFile = selectedFile.delete();
                    if (deletedFile) System.out.println("Arquivo deletado");

                }

            } catch (Exception e) {
                System.err.println("Erro durante a procura por novas mensagens: " + e.getMessage());
                new ErrorLog(e);
            }
        }
    }

    private static void receivingMessagesOneTime() {
        final String diretorio = Constantes.PATH_MESSAGES;

        try {

            if (!ReceiveMessages.internetConnected()) {
                System.err.println("Sem conexão com a internet, não é possivel enviar a mensagem.");
                System.exit(0);
            }

            File file = new File(diretorio);
            File[] listFiles = file.listFiles();

            //Iteração na lista de arquivos
            if (listFiles == null || listFiles.length == 0) {
                System.err.println("Não há nenhum arquivo na pasta.");
                System.exit(0);
            }

            for (File selectedFile : listFiles) {

                if (!selectedFile.getName().equalsIgnoreCase(Constantes.NOME_TXT)) continue;

                System.out.println("Arquivo " + Constantes.NOME_TXT + " encontrado.");

                //Lê e intera informações em objeto
                readTxtClient(selectedFile);

                //Exclui arquivo da pasta
                boolean deletedFile = selectedFile.delete();
                if (deletedFile) System.out.println("Arquivo deletado");

            }

        } catch (Exception e) {
            System.err.println("Erro durante a procura por novas mensagens: " + e.getMessage());
            new ErrorLog(e);
        }

        System.exit(0);
    }

    private static String receiveFromWebservice() {
        int responseCode = 0;

        try {
            URL url = new URL(Constantes.URL_RECEIVE);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","text/plain");
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(Constantes.TIMEOUT_WEBSERVICE);

            responseCode = connection.getResponseCode();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.readLine();
            //return "{'codigoSolicitacao':'004','codigoCliente':'111','validadeChave':'25','restricaoFinanceira':'N','bloqueioSistema':'S'}";

        } catch (IOException e) {
            e.printStackTrace();
            new ErrorLog(e);
            JOptionPane.showMessageDialog(null, "Atenção! Não foi possível receber informações do webservice, " +
                    "por favor tente mais tarde. Código de Resposta: " + responseCode);
        }
        return null;
    }

    private static int receveidFromRobot(String json){
        System.out.println("Json Recebido: " + json);

        final int CONVERTED_TO_OBJETO_COMPLETO = 1;
        final int CONVERTED_TO_OBJETO_RESUMIDO = 2;

        if (json.length() > 20){
            ObjetoEnvioCompleto objetoEnvioCompleto = convertJsonToEnvioCompleto(json);
            if (objetoEnvioCompleto != null) System.out.println(objetoEnvioCompleto.toString());
            return CONVERTED_TO_OBJETO_COMPLETO;
        } else {
            ObjetoEnvioResumido objetoEnvioResumido = convertJsonToEnvioResumido(json);
            if (objetoEnvioResumido != null) System.out.println(objetoEnvioResumido.toString());
            return CONVERTED_TO_OBJETO_RESUMIDO;
        }
    }

    private static ObjetoEnvioResumido convertJsonToEnvioResumido(String json){

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(json, ObjetoEnvioResumido.class);
        }
        return null;
    }

    private static ObjetoEnvioCompleto convertJsonToEnvioCompleto(String json){

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(json, ObjetoEnvioCompleto.class);
        }
        return null;
    }

    public static boolean internetConnected() {
        try {
            URL url = new URL("https://www.google.com.br/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (IOException e) { e.printStackTrace(); new ErrorLog(e); return false; }
    }

    public static void generateTxtRetornoResumido(ObjetoRetornoResumido objeto){
        System.out.println(objeto.toString());

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(objeto.getCodigoSolicitacao()).append("|");

        switch (objeto.getCodigoSolicitacao()){
            case "001" : stringBuilder.append(objeto.getCodigoCliente()); break;
            case "002" : stringBuilder.append(objeto.getValidadeChave()); break;
            case "003" : stringBuilder.append(objeto.getRestricaoFinanceira()); break;
            case "004" : stringBuilder.append(objeto.getBloqueioSistema()); break;
        }
        try {
            File file = new File(Constantes.PATH_MESSAGES + "/" + Constantes.NOME_TXT_RETORNO);
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(stringBuilder);
            printWriter.flush();
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new ErrorLog(e);
        }
    }

    public static void generateTxtRetornoCompleto(ObjetoRetornoCompleto objeto){
        System.out.println(objeto.toString());

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(objeto.getCodigoSolicitacao() + "|");
        stringBuilder.append(objeto.getCodigoCliente() + "|");
        stringBuilder.append(objeto.getCodigoMensagem() + "|");
        stringBuilder.append(objeto.getOpcoes() + "|");
        stringBuilder.append(objeto.getDataMensagem() + "|");
        stringBuilder.append(objeto.getHoraMensagem() + "|");
        stringBuilder.append(objeto.getRemetente() + "|");
        stringBuilder.append(objeto.getDestinatario() + "|");
        stringBuilder.append(objeto.getMensagem());

        try {
            File file = new File(Constantes.PATH_MESSAGES + "/" + Constantes.NOME_TXT_RETORNO);
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new ErrorLog(e);
        }

    }

    private static void readTxtClient(File message){
        //Atribui cada linha lida ao seu respectivo objeto e o envia para o webservice

        try {
            FileReader fileReader = new FileReader(message);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new GsonBuilder().create();

            do{ String lineMessage = bufferedReader.readLine();

                if (lineMessage != null && !lineMessage.isEmpty()) {
                    //Se a string possuir mais de 19 caracteres, é um objetoEnvioCompleto
                    if (lineMessage.length() > 19) {
                        ObjetoEnvioCompleto objetoEnvioCompleto = FindMessages.splitLineObjetoEnvioCompleto(lineMessage);
                        String json = gson.toJson(objetoEnvioCompleto);
                        FindMessages.sendToWebService(json, false, true);
                        FindMessages.returnFromWebService(false, true);
                    }
                    //Se possuir menos que 11 caracteres, é um objetoEnvioResumido
                    else {
                        ObjetoEnvioResumido objetoEnvioResumido = FindMessages.splitLineObjetoEnvioResumido(lineMessage);
                        String json = gson.toJson(objetoEnvioResumido);
                        FindMessages.sendToWebService(json,false, true);
                        FindMessages.returnFromWebService(false, true);
                    }
                }
            }while (bufferedReader.ready());
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            new ErrorLog(e);
        }
    }

}
