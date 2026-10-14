

package com.mycompany.tarefa;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class GerenciadorTarefasFrame extends JFrame {

    private JTextField txtDescricao;
    private JTextField txtDataVencimento;
    private JButton btnAdicionar;
    private JButton btnAlterarStatus;
    private JButton btnRemover;
    
    private JList<Tarefa> listaTarefasGUI;
    private DefaultListModel<Tarefa> modelLista;
    
    private final String NOME_ARQUIVO = "tarefa.txt";
    
    public GerenciadorTarefasFrame() {
        
        //Construtor: Cria e organiza os elementos na janela
        
        setTitle("Gerenciador de Tarefas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // PAINEL SUPERIOR (Campo para digitar)
        
        JPanel painelEntrada = new JPanel(new GridLayout(2, 2, 5,5));
        
        painelEntrada.add(new JLabel(" Descrição:"));
        txtDescricao = new JTextField();
        painelEntrada.add(txtDescricao);
        
        painelEntrada.add(new JLabel(" Data de Vencimento"));
        txtDataVencimento = new JTextField();
        painelEntrada.add(txtDataVencimento);
        
        add(painelEntrada, BorderLayout.NORTH);
        
        //PAINEL CENTRAL (Lista que exibe as tarefas)
        modelLista = new DefaultListModel<>();
        carregarTarefas();
        listaTarefasGUI = new JList<>(modelLista);
        add(new JScrollPane(listaTarefasGUI), BorderLayout.CENTER);
       
        //PAINEL INFERIOR
        JPanel painelBotoes = new JPanel();
        btnAdicionar = new JButton("Adicionar Tarefa");
        btnAlterarStatus = new JButton("Concluir / Restaurar");
        btnRemover = new JButton("Remover Tarefa");
        
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAlterarStatus);
        painelBotoes.add(btnRemover);
        add(painelBotoes, BorderLayout.SOUTH);
        
        //AÇÕES DOS BOTÕES (LISTENERS)
        btnAdicionar.addActionListener(e -> adicionarNovaTarefa());
        btnAlterarStatus.addActionListener(e -> alterarStatusSelecionado());
        btnRemover.addActionListener(e -> removerTarefaSelecionada());

    }
    
    //METODO PARA PEGAR O TEXTO DIGITADO E CRIAR UM OBJETO TAREFA
    
    private void adicionarNovaTarefa() {
        String desc = txtDescricao.getText().trim();
        String data = txtDataVencimento.getText().trim();
        
        if (!desc.isEmpty() && !data.isEmpty()) {
            Tarefa novaTarefa = new Tarefa(desc, data);
            modelLista.addElement(novaTarefa);
            txtDescricao.setText("");
            txtDataVencimento.setText("");
            salvarTarefas();
            
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            
        }
    }
    
    private void alterarStatusSelecionado() {
        int indiceSelecionado = listaTarefasGUI.getSelectedIndex();

        if (indiceSelecionado != -1) {
            Tarefa tarefa = modelLista.getElementAt(indiceSelecionado);
            tarefa.alterarStatus();
            listaTarefasGUI.repaint();
            salvarTarefas();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa na lista!");

        }
    }
    
    private void removerTarefaSelecionada() {
        int indiceSelecionado = listaTarefasGUI.getSelectedIndex();
        
        if (indiceSelecionado != -1) {
            int resposta = JOptionPane.showConfirmDialog(this,
                    "Tem a certeza de que deseja excluir está tarefa?","Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
                 
            if (resposta == JOptionPane.YES_OPTION) {
                modelLista.remove(indiceSelecionado);
                salvarTarefas();
            }
                    
        }   else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa na lista para remover!");
        }
    
    }
    
    private void salvarTarefas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOME_ARQUIVO))) {
            for (int i = 0; i < modelLista.getSize(); i++) {
                Tarefa t = modelLista.getElementAt(i);
                
                writer.println(t.getDescricao() + ";" + t.getDataVencimento() + ";" + t.isConcluida());
              
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo de tarefas!");
            
        }
    }
    
    private void carregarTarefas() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String desc = partes [0];
                    String data = partes [1];
                    boolean concluida = Boolean.parseBoolean(partes[2]);
                    
                    Tarefa tarefa = new Tarefa(desc, data, concluida);
                    modelLista.addElement(tarefa);
                }
            }
            
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(this, "Erro ao carregar o arquivo de tarefa!");
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefasFrame().setVisible(true);
        });
    }
}
