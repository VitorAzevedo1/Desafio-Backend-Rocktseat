package service;

import domain.dto.BeneficiarioRequest;
import domain.dto.BeneficiarioResponse;
import domain.dto.DocumentoDto;
import domain.entity.Beneficiario;
import domain.entity.Documento;
import jakarta.persistence.EntityNotFoundException;
import mapper.BeneficiarioMapper;
import org.springframework.stereotype.Service;
import repository.BeneficiarioRepository;

import java.util.List;

@Service
public class BeneficiarioService {

    private final BeneficiarioRepository repository;
    private final BeneficiarioMapper mapper;

    public BeneficiarioService(BeneficiarioRepository repository, BeneficiarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BeneficiarioResponse salvar(BeneficiarioRequest request) {
        Beneficiario b = mapper.toEntity(request);
        return mapper.toResponse(repository.save(b));
    }

    public List<BeneficiarioResponse> listarTodos() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<DocumentoDto> listarDocumentos(Long id) {
        Beneficiario b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Beneficiário não encontrado"));
        return b.getDocumentos().stream().map(d -> new DocumentoDto(d.getTipoDocumento(), d.getDescricao())).toList();
    }

    public void deletar(Long id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException("Id do beneficiário não enconytrado!");
        }
        repository.deleteById(id);
    }

    public BeneficiarioResponse atualizar(Long id, BeneficiarioRequest request) {

        Beneficiario b = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Beneficiário não encontrado"));

        b.setNome(request.nome());
        b.setDataNascimento(request.dataNascimento());
        b.getDocumentos().clear();

        for(DocumentoDto doc : request.documentos()) {
            Documento d = new Documento();
            d.setTipoDocumento(doc.tipoDocumento());
            d.setDescricao(doc.descricao());
            d.setBeneficiario(b);
            b.getDocumentos().add(d);
        }
        return mapper.toResponse(repository.save(b));
    }
}
