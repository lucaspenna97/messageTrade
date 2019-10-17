package gui;

import gui.ClientSide.ReceiveMessages;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MessageHunter {

    public static void main(String[] args) {

        //Cria diretorios se necessario
        boolean diretorioMensagens = new File (Constantes.PATH_MESSAGES).mkdirs();

        if (diretorioMensagens) System.out.println("Diretório mensagens criado com sucesso");
        else System.out.println("Diretório mensagens já existente");

        //Cria a telinha
        createGUI();
    }

    private static void createGUI() {

        //Verifica suporte a system tray
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray não é suportado");
            return;
        }

        //Pega imagem do diretório do projeto
        Image imageIcon = Toolkit.getDefaultToolkit().getImage(MessageHunter.class.getResource("/img/email.png"));

        //Setta imagem
        TrayIcon trayIcon = new TrayIcon(imageIcon);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Message Hunter - Version " + Constantes.VERSAO);

        //Instancia popupMenu
        PopupMenu popupMenu = new PopupMenu();

        //Pega systemTray de acordo com o sistema
        SystemTray systemTray = SystemTray.getSystemTray();

        //Componentes Popup
        MenuItem menuItemVersao = new MenuItem("Versão");
        MenuItem itemSair = new MenuItem("Sair");

        //Adiciona componenetes ao popupMenu
        popupMenu.add(menuItemVersao);
        popupMenu.addSeparator();
        popupMenu.add(itemSair);

        //Setta popupMenu ao trayIcon
        trayIcon.setPopupMenu(popupMenu);

        //Tenta adicionar TrayIcon ao SystemTray
        try {systemTray.add(trayIcon);} catch (AWTException e) {e.printStackTrace(); new ErrorLog(e);}

        //Exibe versão em que se encontra a aplicação
        menuItemVersao.addActionListener(e -> JOptionPane.showMessageDialog(null, "Message Hunter - Versão Beta" + Constantes.VERSAO));

        //Função usada para sair da aplicação
        itemSair.addActionListener(e -> {SystemTray.getSystemTray().remove(trayIcon); System.exit(0); });

        //Thread se inicia ao abrir a aplicação, buscando infinitamente as mensagens na @ngulo
        //new FindMessages(true , false);

        //Thread se inicia ao abrir a aplicação. buscando infinitamente as mensagens no webservice
        new ReceiveMessages(true, false);

        //Mensagem ao startar a aplicação
        trayIcon.displayMessage("Angulo Sistemas", "Message Hunter Iniciado", TrayIcon.MessageType.INFO);
    }


}
