package javacore.controller;

import javacore.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class MainFrameController extends ArduinoController {

    private MainFrame mainFrame = new MainFrame();


    private JButton btnEnviar = mainFrame.getBtnEnviar();
    private JButton btnConectar = mainFrame.getBtncConectar();
    private JButton btnStart = mainFrame.getBtnStart();

    private JLabel lblPot = mainFrame.getLblPot();
    private JLabel lblValidade = mainFrame.getLblValidade();
    private JLabel lblGastoAtual = mainFrame.getLblGastoAtual();
    private JLabel lblGastoTotal = mainFrame.getLblGastoTotal();

    private JRadioButton btnCen = mainFrame.getBtnCen();

    private JTabbedPane tabbedPane = mainFrame.getTabbedPane1();

    private JTextField txtfEnviarPot = mainFrame.getTxtfEnviarPot();



    private boolean isConectected = false;
    private boolean isGettingPot = false;

    private double potencia;
    private double tensao;
    private double gastoTotal = 0;
    private double potDesejada = 0;


    private static final double precoWS = 0.0000001464;

    //Construtor
    public MainFrameController() {
        closePanels();
        initListners();
    }

    public void showMainFrame() {
        mainFrame.setVisible(true);
    }

    //Inicia a fucao dos botoes
    private void initListners() {
        btnConectar.addActionListener(new BtnConectarListener());
        btnStart.addActionListener(new BtnStartListener());
        btnEnviar.addActionListener(new BtnEnviarListener());
    }
    //Fecha os paineis de configuracao e de afericao da potencia
    private void closePanels(){
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
    }
    //O que o botao conectar ira fazer quando clicado
    private class BtnConectarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            isConectected = !isConectected;
            if (isConectected) {
                openComPort();
                btnConectar.setText("Desconectar");
                while(getComSerial() == null) { //Assim que o programa estiver pronto para funcionar ele sai do whille
                    tabbedPane.setEnabledAt(1, false);
                }
                tabbedPane.setEnabledAt(1, true);
            } else {
                closeComPort();
                closePanels();
                btnConectar.setText("Conectar");
                btnStart.setText("Começar");
            }
        }
    }
    //O que o botao start ira fazer quando conectado
    private class BtnStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            isGettingPot = !isGettingPot;
            if (btnCen.isSelected())
                tensao = 127;
            else
                tensao = 220;
            tabbedPane.setEnabledAt(2, isGettingPot);
            lblPot.setText("0.00");
            if (isGettingPot)
                btnStart.setText("Parar");
            else
                btnStart.setText("Começar");
            Thread thread = new Thread(() -> {

                while (isGettingPot) {
                    String num = getComSerial();
                    if (num == null)
                        break;
                    setPotPrice(num);
                    verificarPotencias();
                }
            });
            thread.start();
        }
        //Metodo de calculo da potencia e preco
        private void setPotPrice(String num) {
            double gastoAtual;
            potencia = Double.parseDouble(num) * tensao;
            gastoAtual = potencia * precoWS;
            gastoTotal += gastoAtual;
            setLabels(gastoAtual);
        }
        //Metodo para mudar os textos da aplicacao
        private void setLabels(double gastoAtual) {
            DecimalFormat dfPot = new DecimalFormat("0.##");
            DecimalFormat dfMoney = new DecimalFormat("0.#######");
            lblGastoAtual.setText("R$" + dfMoney.format(gastoAtual));
            lblGastoTotal.setText("R$" + dfMoney.format(gastoTotal));
            lblPot.setText(dfPot.format(potencia) + "W");
        }
        //Verificar se a potencia do aparelho, atende ou não a medida
        private void verificarPotencias() {
            if (potencia > potDesejada) {
                lblPot.setForeground(Color.red);
                lblGastoAtual.setForeground(Color.red);
            } else if (potDesejada == 0) {
                lblPot.setForeground(Color.black);
                lblGastoAtual.setForeground(Color.black);
            } else {
                lblPot.setForeground(Color.green);
                lblGastoAtual.setForeground(Color.green);
            }
        }
    }
    //Enviar o valor de potencia
    private class BtnEnviarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                potDesejada = Double.parseDouble(txtfEnviarPot.getText());
                txtfEnviarPot.setText("");
                lblValidade.setText("");
            } catch (NumberFormatException ex) {
                lblValidade.setText("Número invalido");
            }
        }
    }
}
