import java.time.LocalDate;

public class Conta {
    private Long id;
    private String agencia;
    private String nomeTitular;
    private String cpfTitular;
    private LocalDate dataAbertura;
    private double saldo;
    private boolean ativa;
    private String tipo;  
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    
    public String getNomeTitular() { return nomeTitular; }
    public void setNomeTitular(String nomeTitular) { this.nomeTitular = nomeTitular; }
    
    public String getCpfTitular() { return cpfTitular; }
    public void setCpfTitular(String cpfTitular) { this.cpfTitular = cpfTitular; }
    
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
