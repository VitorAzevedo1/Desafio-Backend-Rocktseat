package controler;

import domain.dto.BeneficiarioRequest;
import domain.dto.BeneficiarioResponse;
import domain.dto.DocumentoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BeneficiarioService;

import java.util.List;

@RestController
@RequestMapping("/beneficiarios")
public class BeneficiarioController {

    private final BeneficiarioService service;

    public BeneficiarioController(BeneficiarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BeneficiarioResponse> criar(@RequestBody BeneficiarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @GetMapping
    public List<BeneficiarioResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}/documentos")
    public List<DocumentoDto> listarDocumentos(@PathVariable Long id) {
        return service.listarDocumentos(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiarioResponse> atualizar(@PathVariable Long id, @RequestBody BeneficiarioRequest request) {
        return ResponseEntity.ok(service.atualizar(id,request));
    }
}
