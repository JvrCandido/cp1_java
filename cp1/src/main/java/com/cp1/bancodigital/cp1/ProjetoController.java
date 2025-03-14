import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjetoController {
    @GetMapping("/")
    public String info() {
        return "Projeto Banco Digital - Integrantes: [João Victor Rocha Cândido RM:554727 , 
                João Victor Santis        RM:555287]";
    }
}
