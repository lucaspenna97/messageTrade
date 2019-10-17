package gui;

import gui.EnterpriseSide.FindMessages;

import java.io.*;

public class ErrorLog {
    private Exception exception;

    public ErrorLog(Exception exception){
        this.exception = exception;
        generateTxtLogError(exception);
    }

    private static void generateTxtLogError(Exception exception){

        final String diretorio = Constantes.PATH_MESSAGES;

        try {

            File file = new File(diretorio);

            FilenameFilter filenameFilter = (dir, name) -> name.equalsIgnoreCase(Constantes.NOME_TXT_ERROR);
            File[] files = file.listFiles(filenameFilter);

            if (files != null && files.length != 0) {

                for (File arquivoErro: files) {

                    FileWriter fileWriter = new FileWriter(arquivoErro, true);
                    fileWriter.append("\r\n");
                    fileWriter.append("Data e Hora: ").append(FindMessages.generateDate()).append(" ##########################################################");
                    fileWriter.append("\r\n");
                    fileWriter.append("Erro: ").append(exception.getMessage());
                    fileWriter.flush();
                    fileWriter.close();

                }

            } else {

                FileWriter fileWriter = new FileWriter(diretorio + "/" + Constantes.NOME_TXT_ERROR);
                fileWriter.append("\r\n");
                fileWriter.append("Data e Hora: ").append(FindMessages.generateDate()).append(" ##########################################################");
                fileWriter.append("\r\n");
                fileWriter.append("Erro: ").append(exception.getMessage());
                fileWriter.flush();
                fileWriter.close();

            }


        } catch (Exception e) {
            System.out.println("Erro ao gravar arquivo de log: " + e.getMessage());
        }

    }


}
