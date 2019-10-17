package gui.EnterpriseSide;

import bean.ObjetoEnvioCompleto;
import bean.ObjetoEnvioResumido;
import bean.ObjetoRetornoCompleto;
import bean.ObjetoRetornoResumido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import gui.ClientSide.ReceiveMessages;
import gui.Constantes;
import gui.ErrorLog;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;

public class FindMessages {

    public FindMessages(boolean lookingForMessagesForever, boolean lookingForMessagesOneTime){

        //Inicia o processo atraves do metodo construtor
        if (lookingForMessagesForever) lookingForMessagesForever();
        if (lookingForMessagesOneTime) lookingForMessagesOneTime();
    }

    private static void lookingForMessagesForever() {

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
                    readTxt(selectedFile);

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

    private static void lookingForMessagesOneTime() {

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
                readTxt(selectedFile);

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

    public static void readTxt(File message){
        //Atribui cada linha lida ao seu respectivo objeto e o envia para o webservice

        try {
            FileReader fileReader = new FileReader(message);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new GsonBuilder().create();

            while (bufferedReader.ready()) {

                String lineMessage = bufferedReader.readLine();

                if (lineMessage == null || lineMessage.isEmpty()) continue;

                //Se a string possuir mais de 19 caracteres, é um objetoEnvioCompleto
                if (lineMessage.length() > 19) {
                    ObjetoEnvioCompleto objetoEnvioCompleto = splitLineObjetoEnvioCompleto(lineMessage);
                    String json = gson.toJson(objetoEnvioCompleto);
                    sendToWebService(json, true, false);
                    returnFromWebService(true, false);
                }
                //Se possuir menos que 11 caracteres, é um objetoEnvioResumido
                else {
                    ObjetoEnvioResumido objetoEnvioResumido = splitLineObjetoEnvioResumido(lineMessage);
                    String json = gson.toJson(objetoEnvioResumido);
                    sendToWebService(json, true, false);
                    returnFromWebService(true, false);
                }

            }
            bufferedReader.close();
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorLog(e);
        }
    }

    public static String generateDate(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; //Mês sempre é necessario somar 1
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month) + "/" + String.valueOf(year)
                + "_" + String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }

    public static ObjetoEnvioResumido splitLineObjetoEnvioResumido(String txt){
        String[] arrayTxt = txt.split(Pattern.quote("|"));

        ObjetoEnvioResumido objetoEnvioResumido = new ObjetoEnvioResumido();
        objetoEnvioResumido.setCodigoSolicitacao(arrayTxt[0]);

        if (arrayTxt[1].length() > 6) objetoEnvioResumido.setClienteCNPJ(arrayTxt[1]);
        else objetoEnvioResumido.setCodigoCliente(arrayTxt[1]);

        return objetoEnvioResumido;
    }

    public static ObjetoEnvioCompleto splitLineObjetoEnvioCompleto(String txt){
        String[] arrayTxt = txt.split(Pattern.quote("|"));

        ObjetoEnvioCompleto objetoEnvioCompleto = new ObjetoEnvioCompleto();
        objetoEnvioCompleto.setCodigoSolicitacao(arrayTxt[0]);
        objetoEnvioCompleto.setCodigoCliente(arrayTxt[1]);
        objetoEnvioCompleto.setCodigoMensagem(arrayTxt[2]);
        objetoEnvioCompleto.setOpcoes(arrayTxt[3]);
        objetoEnvioCompleto.setDataEnvio(arrayTxt[4]);
        objetoEnvioCompleto.setHorarioEnvio(arrayTxt[5]);
        objetoEnvioCompleto.setUsuario(arrayTxt[6]);
        objetoEnvioCompleto.setMensagem(arrayTxt[7]);

        return objetoEnvioCompleto;
    }

    public static void sendToWebService(String json, boolean enterpriseSide, boolean clientSide){
        
        HttpURLConnection connection = null;

        try {
            System.out.println("Object Json: " + json);

            URL url = null;
            if (enterpriseSide) url = new URL(Constantes.URL_SEND);
            if (clientSide) url = new URL(Constantes.URL_SEND);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","text/plain");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Constantes.TIMEOUT_WEBSERVICE);

            System.out.println("Response Code: " + connection.getResponseCode());

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
            outputStreamWriter.write(json);
            outputStreamWriter.flush();
            outputStreamWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
            new ErrorLog(e);
        } finally {
            if (connection != null) connection.disconnect();
        }
        
    }

    public static void returnFromWebService(boolean enterpriseSide, boolean clientSide){

        HttpURLConnection connection = null;
        
            try {
                //Espera retorno do webservice, transforma em objeto e cria arquivo texto

                URL url = null;
                if (enterpriseSide) url = new URL(Constantes.URL_RECEIVE);
                if (clientSide) url = new URL(Constantes.URL_RECEIVE);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("Accept","application/json");
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(Constantes.TIMEOUT_WEBSERVICE);

                System.out.println("Response Code: " + connection.getResponseCode());

                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
                JsonReader jsonReader = new JsonReader(inputStreamReader);
                jsonReader.beginObject();

                Gson gson = new GsonBuilder().create();

                if (jsonReader.toString().length() > 65) {
                    ObjetoRetornoCompleto objetoRetornoCompleto = gson.fromJson(jsonReader, ObjetoRetornoCompleto.class);
                    ReceiveMessages.generateTxtRetornoCompleto(objetoRetornoCompleto);
                } else {
                    ObjetoRetornoResumido objetoRetornoResumido = gson.fromJson(jsonReader, ObjetoRetornoResumido.class);
                    ReceiveMessages.generateTxtRetornoResumido(objetoRetornoResumido);
                }

            } catch (Exception e) { 
                e.printStackTrace(); 
                new ErrorLog(e); 
            } finally {
                if (connection != null) connection.disconnect();
            }
      
    }

}

