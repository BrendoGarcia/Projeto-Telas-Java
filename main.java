import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CadastroFuncionariosGUI {
    private JFrame frame;
    private JTextField nomeTextField, emailTextField, salarioTextField;
    private JRadioButton masculinoRadioButton, femininoRadioButton;
    private JPasswordField senhaPasswordField;
    private JComboBox<String> departamentoComboBox;
    private JTextArea detalhesTextArea;

    // Armazenar os funcionários cadastrados em uma lista
    private List<Funcionario> funcionarios = new ArrayList<>();

    public CadastroFuncionariosGUI() {
        frame = new JFrame("Cadastro de Funcionários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField(20);

        JLabel sexoLabel = new JLabel("Sexo:");
        masculinoRadioButton = new JRadioButton("Masculino");
        femininoRadioButton = new JRadioButton("Feminino");
        ButtonGroup sexoButtonGroup = new ButtonGroup();
        sexoButtonGroup.add(masculinoRadioButton);
        sexoButtonGroup.add(femininoRadioButton);

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaPasswordField = new JPasswordField(20);

        JLabel departamentoLabel = new JLabel("Departamento:");
        String[] departamentos = {"RH", "Marketing", "Financeiro", "TI"};
        departamentoComboBox = new JComboBox<>(departamentos);

        JLabel salarioLabel = new JLabel("Salário:");
        salarioTextField = new JTextField(10);

        JLabel detalhesLabel = new JLabel("Detalhes:");
        detalhesTextArea = new JTextArea(5, 20);
        JScrollPane detalhesScrollPane = new JScrollPane(detalhesTextArea);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar um novo objeto Funcionario com os dados fornecidos
                String nome = nomeTextField.getText();
                String email = emailTextField.getText();
                String sexo = masculinoRadioButton.isSelected() ? "Masculino" : "Feminino";
                String senha = new String(senhaPasswordField.getPassword());
                String departamento = (String) departamentoComboBox.getSelectedItem();
                double salario = Double.parseDouble(salarioTextField.getText());
                String detalhes = detalhesTextArea.getText();

                Funcionario novoFuncionario = new Funcionario(nome, email, sexo, senha, departamento, salario, detalhes);

                // Adicionar o funcionário à lista
                funcionarios.add(novoFuncionario);

                // Limpar os campos de entrada
                limparCampos();

                // Exibir uma mensagem de sucesso
                JOptionPane.showMessageDialog(frame, "Funcionário cadastrado com sucesso!");
            }
        });

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Construir uma string com os dados de todos os funcionários cadastrados
                StringBuilder sb = new StringBuilder();
                for (Funcionario funcionario : funcionarios) {
                    sb.append("Nome: ").append(funcionario.getNome()).append("\n");
                    sb.append("Email: ").append(funcionario.getEmail()).append("\n");
                    sb.append("Sexo: ").append(funcionario.getSexo()).append("\n");
                    sb.append("Departamento: ").append(funcionario.getDepartamento()).append("\n");
                    sb.append("Salário: ").append(funcionario.getSalario()).append("\n");
                    sb.append("Detalhes: ").append(funcionario.getDetalhes()).append("\n");
                    sb.append("--------------------\n");
                }

                // Exibir uma mensagem com os dados dos funcionários
                if (funcionarios.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhum funcionário cadastrado.");
                } else {
                    JOptionPane.showMessageDialog(frame, sb.toString());
                }
            }
        });

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter o índice do funcionário selecionado
                int index = JOptionPane.showOptionDialog(frame, "Selecione o funcionário a ser atualizado:", "Atualizar Cadastro", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, funcionarios.toArray(), null);

                if (index >= 0) {
                    Funcionario funcionarioSelecionado = funcionarios.get(index);

                    // Atualizar os campos de entrada com os dados do funcionário selecionado
                    nomeTextField.setText(funcionarioSelecionado.getNome());
                    emailTextField.setText(funcionarioSelecionado.getEmail());
                    if (funcionarioSelecionado.getSexo().equals("Masculino")) {
                        masculinoRadioButton.setSelected(true);
                    } else {
                        femininoRadioButton.setSelected(true);
                    }
                    senhaPasswordField.setText(funcionarioSelecionado.getSenha());
                    departamentoComboBox.setSelectedItem(funcionarioSelecionado.getDepartamento());
                    salarioTextField.setText(Double.toString(funcionarioSelecionado.getSalario()));
                    detalhesTextArea.setText(funcionarioSelecionado.getDetalhes());

                    // Remover o funcionário da lista
                    funcionarios.remove(index);

                    // Exibir mensagem de sucesso
                    JOptionPane.showMessageDialog(frame, "Funcionário atualizado. Preencha os campos e clique em 'Cadastrar'.");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(sexoLabel);
        panel.add(masculinoRadioButton);
        panel.add(femininoRadioButton);
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(senhaLabel);
        panel.add(senhaPasswordField);
        panel.add(departamentoLabel);
        panel.add(departamentoComboBox);
        panel.add(salarioLabel);
        panel.add(salarioTextField);
        panel.add(detalhesLabel);
        panel.add(detalhesScrollPane);
        panel.add(cadastrarButton);
        panel.add(consultarButton);
        panel.add(atualizarButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void limparCampos() {
        nomeTextField.setText("");
        emailTextField.setText("");
        masculinoRadioButton.setSelected(true);
        senhaPasswordField.setText("");
        departamentoComboBox.setSelectedIndex(0);
        salarioTextField.setText("");
        detalhesTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroFuncionariosGUI();
        });
    }

    class Funcionario {
        private String nome;
        private String email;
        private String sexo;
        private String senha;
        private String departamento;
        private double salario;
        private String detalhes;

        public Funcionario(String nome, String email, String sexo, String senha, String departamento, double salario, String detalhes) {
            this.nome = nome;
            this.email = email;
            this.sexo = sexo;
            this.senha = senha;
            this.departamento = departamento;
            this.salario = salario;
            this.detalhes = detalhes;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getSexo() {
            return sexo;
        }

        public String getSenha() {
            return senha;
        }

        public String getDepartamento() {
            return departamento;
        }

        public double getSalario() {
            return salario;
        }

        public String getDetalhes() {
            return detalhes;
        }
    }
}
