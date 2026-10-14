
package com.mycompany.tarefa;

public class Tarefa {
    private String descricao;
    private String dataVencimento;
    private boolean concluida;
    
    public Tarefa(String descricao, String dataVencimento) {
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.concluida = false; // Inicia como pendente
    }
    
    public Tarefa(String descricao, String dataVencimento, boolean concluida) {
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.concluida = concluida;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getDataVencimento() {
        return dataVencimento;
    }
    
    public boolean isConcluida() {
        return concluida;
    }
    
    public void alterarStatus() {
        this.concluida = !this.concluida;
    }
    
    @Override
    public String toString() {
        String status = concluida ? "[CONCLUÍDA]" : "[PENDENTE]";
        return status + " " + descricao + " (Vence em: " + dataVencimento + ")";
    }
}