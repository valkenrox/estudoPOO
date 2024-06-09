import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppGUI extends JFrame {
    private RepositorioDeLinks repositorio;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;

    public AppGUI() {
        repositorio = new RepositorioDeLinks();
        setTitle("Repositório de Links");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Define o layout com imagem de fundo
        setContentPane(new JPanel() {
            private BufferedImage image;

            {
                try {
                    image = ImageIO.read(new File("C:\\Users\\e053610\\Desktop\\Java\\Repositorio de links\\RepoLink\\src\\1621877602266.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        });

        setLayout(new BorderLayout());

        // Header com campo de busca
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Pesquisar o link:");
        searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLink(searchField.getText());
            }
        });
        headerPanel.add(headerLabel);
        headerPanel.add(searchField);
        add(headerPanel, BorderLayout.NORTH);

        // Tabela de links
        tableModel = new DefaultTableModel(new String[]{"Índice", "URL", "Descrição"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botões na parte inferior
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton adicionarButton = new JButton("Adicionar Link");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = JOptionPane.showInputDialog(AppGUI.this, "Digite a URL do link:");
                String descricao = JOptionPane.showInputDialog(AppGUI.this, "Digite a descrição do link:");
                repositorio.adicionarLink(new Link(url, descricao));
                atualizarLista();
                JOptionPane.showMessageDialog(AppGUI.this, "Link adicionado com sucesso.");
            }
        });
        buttonsPanel.add(adicionarButton);

        JButton removerButton = new JButton("Remover Link");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int indice = (int) table.getValueAt(linhaSelecionada, 0);
                    Link link = repositorio.getLinks().get(indice);
                    repositorio.removerLink(link);
                    atualizarLista();
                    JOptionPane.showMessageDialog(AppGUI.this, "Link removido com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(AppGUI.this, "Selecione um link para remover.");
                }
            }
        });
        buttonsPanel.add(removerButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void atualizarLista() {
        tableModel.setRowCount(0);
        for (int i = 0; i < repositorio.getLinks().size(); i++) {
            Link link = repositorio.getLinks().get(i);
            tableModel.addRow(new Object[]{i, link.getUrl(), link.getDescricao()});
        }
    }

    private void buscarLink(String textoBusca) {
        tableModel.setRowCount(0);
        for (int i = 0; i < repositorio.getLinks().size(); i++) {
            Link link = repositorio.getLinks().get(i);
            if (link.getUrl().contains(textoBusca) || link.getDescricao().contains(textoBusca)) {
                tableModel.addRow(new Object[]{i, link.getUrl(), link.getDescricao()});
            }
        }
    }

    public void mostrar() {
        setVisible(true);
        atualizarLista();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppGUI app = new AppGUI();
                app.mostrar();
            }
        });
    }
}
