import org.pivaprototype.piv.InfoConexao;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PivaGui extends JFrame{
    private JPanel painelPrincipal;
    private JButton selecionarArquivoButton;
    private JRadioButton a360RadioButton;
    private JRadioButton a480RadioButton;
    private JRadioButton a720RadioButton;
    private JRadioButton a1080RadioButton;
    private JButton converterButton;
    private JTextField textField1;
    private JLabel nomeArquivo;
    private JLabel resolucaoVideo;
    private JLabel resolucaoImagem;
    private JTextField ip;
    private JButton adicionarConexãoButton;
    private JTextField porta;
    private JButton limparConexõesButton;
    private JTextPane conexõesTextPane;
    private File file;
    private List<InfoConexao> conexoes = new LinkedList<>();
    private String textoConexao = new String();

    private PivaGui(String nome){
        super(nome);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(painelPrincipal);
        this.pack();
        groupButton();
        selecionarArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Videos & Imagens", "mp4", "jpg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }
                nomeArquivo.setText("Arquivo : " + file.getName());
                String fileName = file.toString();
                int index = fileName.lastIndexOf('.');
                if(index > 0) {
                    String extension = fileName.substring(index + 1);
                    if (extension.contentEquals("mp4")){
                        resolucaoImagem.setVisible(false);
                        textField1.setVisible(false);
                        a360RadioButton.setVisible(true);
                        a480RadioButton.setVisible(true);
                        a720RadioButton.setVisible(true);
                        a1080RadioButton.setVisible(true);
                        resolucaoVideo.setVisible(true);
                    }else {
                        resolucaoImagem.setVisible(true);
                        textField1.setVisible(true);
                        a360RadioButton.setVisible(false);
                        a480RadioButton.setVisible(false);
                        a720RadioButton.setVisible(false);
                        a1080RadioButton.setVisible(false);
                        resolucaoVideo.setVisible(false);
                    }
                }

            }


        });

        adicionarConexãoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(porta.getText().isEmpty() || ip.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Ambos os campos de ip e porta precisão ser preenchidos.",
                            "P.I.V.A Error",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    InfoConexao conexao = new InfoConexao();
                    conexao.setIp(ip.getText());
                    conexao.setPorta(Integer.parseInt(porta.getText()));
                    conexoes.add(conexao);
                    escreverConexoes();
                }
            }
        });

        limparConexõesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InfoConexao conexao = new InfoConexao();
                conexao.setIp(ip.getText());
                conexao.setPorta(Integer.parseInt(porta.getText()));
                conexoes.remove(conexao);
                escreverConexoes();
            }
        });
    }

    private void groupButton( ) {
        ButtonGroup bg1 = new ButtonGroup( );
        bg1.add(a360RadioButton);
        bg1.add(a480RadioButton);
        bg1.add(a720RadioButton);
        bg1.add(a1080RadioButton);
    }

    private void escreverConexoes(){
        textoConexao = "";
        for (InfoConexao conexao:conexoes){
            textoConexao += conexao.getIp() + " " + conexao.getPorta() + "\n";
        }
        conexõesTextPane.setText(textoConexao);
    }

    public static void main(String[] args){
        JFrame painel = new PivaGui("P.I.V.A");
        painel.setVisible(true);
    }
}
