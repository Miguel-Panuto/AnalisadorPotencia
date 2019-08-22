package javacore.view;


import javax.swing.*;

public class MainFrame extends JFrame {


    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    //Componentes da tela
    private JPanel mainPanel;
    private JPanel conexaoPanel;
    private JPanel configPanel;
    private JPanel potenciaPanel;

    private JTabbedPane tabbedPane1;

    private JButton btnStart;
    private JButton btnConectar;
    private JButton btnEnviar;

    private JRadioButton btnCen;
    private JRadioButton btnDuz;

    private JLabel lblPot;
    private JLabel lblTen;
    private JLabel lblValidade;
    private JLabel lblGastoAtualText;
    private JLabel lblGastoAtual;
    private JLabel lblGastoTotalText;
    private JLabel lblGastoTotal;
    private JLabel lblPotText;

    private JTextField txtfEnviarPot;

    //Construtor fixando tamanho
    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);

    }

    //Getters dos componentes a ser usado

    public JButton getBtncConectar() {
        return btnConectar;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public JLabel getLblPot() {
        return lblPot;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JRadioButton getBtnCen() {
        return btnCen;
    }

    public JTextField getTxtfEnviarPot() {
        return txtfEnviarPot;
    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public JLabel getLblGastoAtual() {
        return lblGastoAtual;
    }

    public JLabel getLblGastoTotal() {
        return lblGastoTotal;
    }

    public JLabel getLblValidade() {
        return lblValidade;
    }

}
