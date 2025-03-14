import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private Map<Long, Conta> contas = new HashMap<>();
    private Long idCounter = 1L;

    
    @PostMapping
    public Conta cadastrarConta(@RequestBody Conta conta) {
        
        if (conta.getNomeTitular() == null || conta.getCpfTitular() == null || conta.getTipo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }
        if (conta.getDataAbertura().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de abertura não pode ser no futuro.");
        }
        if (conta.getSaldo() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo inicial não pode ser negativo.");
        }
        if (!(conta.getTipo().equals("corrente") || conta.getTipo().equals("poupanca") || conta.getTipo().equals("salario"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de conta inválido.");
        }

        conta.setId(idCounter++);
        contas.put(conta.getId(), conta);
        return conta;
    }

    
    @GetMapping
    public List<Conta> listarContas() {
        return new ArrayList<>(contas.values());
    }

    
    @GetMapping("/{id}")
    public Conta buscarContaPorId(@PathVariable Long id) {
        return contas.get(id);
    }

    
    @GetMapping("/cpf/{cpf}")
    public Conta buscarContaPorCpf(@PathVariable String cpf) {
        for (Conta conta : contas.values()) {
            if (conta.getCpfTitular().equals(cpf)) {
                return conta;
            }
        }
        return null;
    }

    
    @PatchMapping("/encerrar/{id}")
    public Conta encerrarConta(@PathVariable Long id) {
        Conta conta = contas.get(id);
        if (conta != null) {
            conta.setAtiva(false);
        }
        return conta;
    }

    
    @PostMapping("/depositar")
    public Conta depositar(@RequestBody Map<String, Object> deposito) {
        Long id = Long.valueOf(deposito.get("id").toString());
        double valor = Double.valueOf(deposito.get("valor").toString());
        
        Conta conta = contas.get(id);
        if (conta != null && valor > 0) {
            conta.setSaldo(conta.getSaldo() + valor);
            return conta;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor inválido");
    }

    
    @PostMapping("/sacar")
    public Conta sacar(@RequestBody Map<String, Object> saque) {
        Long id = Long.valueOf(saque.get("id").toString());
        double valor = Double.valueOf(saque.get("valor").toString());
        
        Conta conta = contas.get(id);
        if (conta != null && valor > 0 && conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            return conta;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor inválido ou saldo insuficiente");
    }

    
    @PostMapping("/pix")
    public String realizarPix(@RequestBody Map<String, Object> pix) {
        Long idOrigem = Long.valueOf(pix.get("idOrigem").toString());
        Long idDestino = Long.valueOf(pix.get("idDestino").toString());
        double valor = Double.valueOf(pix.get("valor").toString());

        Conta contaOrigem = contas.get(idOrigem);
        Conta contaDestino = contas.get(idDestino);

        if (contaOrigem != null && contaDestino != null && valor > 0 && contaOrigem.getSaldo() >= valor) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            return "PIX realizado com sucesso!";
        }
        return "Erro no PIX!";
    }
}
