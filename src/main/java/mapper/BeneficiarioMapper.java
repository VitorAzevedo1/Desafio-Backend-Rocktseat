package mapper;

import domain.dto.BeneficiarioRequest;
import domain.dto.BeneficiarioResponse;
import domain.dto.DocumentoDto;
import domain.entity.Beneficiario;
import domain.entity.Documento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BeneficiarioMapper {

    public Beneficiario toEntity(BeneficiarioRequest req) {
        Beneficiario b = new Beneficiario();

        b.setNome(req.nome());
        b.setTelefone(req.telefone());
        b.setDataNascimento(req.dataNascimento());

        List<Documento> documentos = req.documentos().stream().map(d -> {
            Documento doc = new Documento();
            doc.setTipoDocumento(d.tipoDocumento());
            doc.setDescricao(d.descricao());
            doc.setBeneficiario(b);
            return doc;
        }).collect(Collectors.toList());

        b.setDocumentos(documentos);
        return b;
    }

    public BeneficiarioResponse toResponse(Beneficiario b) {
        if (b == null) return null;

        List<DocumentoDto> docs = (b.getDocumentos() == null)
                ? List.of()
                : b.getDocumentos().stream()
                .filter(Objects::nonNull)
                .map(d -> new DocumentoDto(d.getTipoDocumento(), d.getDescricao()))
                .collect(Collectors.toList());
        return new BeneficiarioResponse(b.getId(), b.getNome(), b.getTelefone(), b.getDataNascimento(), docs);
    }
}
