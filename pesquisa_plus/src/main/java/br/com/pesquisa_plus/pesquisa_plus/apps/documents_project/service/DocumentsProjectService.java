package br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.models.DocumentsProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import jakarta.transaction.Transactional;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.repository.DocumentsProjectRepository;
import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.io.InputStream;

@Service
public class DocumentsProjectService extends AbstractService<DocumentsProjectModel, Integer> {

    private DocumentsProjectRepository documentProjectRepository;
    private static final String UPLOAD_DIR = "uploads/documents/project/";

    public DocumentsProjectService(DocumentsProjectRepository documentProjectRepository) {
        super();
        this.documentProjectRepository = documentProjectRepository;
    }

    @Transactional
    public String uploadDocument(Long idProject, Long id, MultipartFile file) throws IOException {

        Optional<DocumentsProjectModel> documentProjectModel = documentProjectRepository.findById(id);
        if (documentProjectModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        DocumentsProjectModel document = documentProjectModel.get();

        // Criar o diretório se não existir
        Path uploadDirPath = Paths.get(UPLOAD_DIR + idProject + '/');
        Files.createDirectories(uploadDirPath);

        // Gerar um nome único para o arquivo
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDirPath.resolve(fileName);

        // Remover o arquivo antigo, se existir
        if (document.getDocumentDocumentProject() != null) {
            Path oldFilePath = Paths.get(document.getDocumentDocumentProject());
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                System.err.println("Erro ao deletar o documento antigo: " + e.getMessage());
                // Considere logar este erro e potencialmente lançar uma exceção
                // se deletar o arquivo antigo for crítico. Por enquanto, vamos continuar.
            }        }

        // Salvar o novo arquivo
        try (InputStream is = file.getInputStream()) { // Use try-with-resources para auto-fechar
            Files.copy(is, filePath);
        } catch (IOException e) {
            throw new IOException("Falha ao salvar documento: " + e.getMessage(), e); // Envolve a exceção
        }

        // Atualizar o caminho do arquivo no banco de dados
        document.setDocumentDocumentProject(filePath.toString());
        super.update(document);

        return filePath.toString(); // Retorna o caminho do arquivo
    }

    public ResponseEntity<String> getDocument(Long id) throws IOException {
        Optional<DocumentsProjectModel> documentProjectModel = documentProjectRepository.findById(id);
        if (documentProjectModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        String documentPath = documentProjectModel.get().getDocumentDocumentProject();
        if (documentPath == null || documentPath.isEmpty())
        {
             throw new RequestDataInvalidException("Caminho do documento está vazio ou nulo.");
        }
        Path path = Paths.get(documentPath);

        if (!Files.exists(path)) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        try {
            String base64Image = getImageAsBase64(documentPath);
            return ResponseEntity.ok(base64Image);
        } catch (IOException e) {
            throw new RequestDataInvalidException("Erro ao ler documento: " + e.getMessage());
        }
    }

    public String getImageAsBase64(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);
        String mimeType = Files.probeContentType(path);

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(fileContent);
    }

    @Override
    @Transactional
    public void deleteByID(Integer id) {
        Optional<DocumentsProjectModel> documentProjectModel = documentProjectRepository.findById(id);

        if (documentProjectModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        DocumentsProjectModel documentProject = documentProjectModel.get();

        // Deleta o arquivo se existir
        if (documentProject.getDocumentDocumentProject() != null) {
            try {
                Path photoPath = Paths.get(documentProject.getDocumentDocumentProject());
                Files.deleteIfExists(photoPath);
            } catch (IOException e) {
                System.err.println("Erro ao deletar documento: " + e.getMessage());
                // IMPORTANTE: Decida como lidar com isso.
                // - Logar o erro e continuar? (como feito aqui)
                // - Lançar uma exceção para impedir a exclusão do registro do banco de dados?
                // - Reverter a transação?
                // A melhor abordagem depende dos requisitos da sua aplicação.
                // Se você quer garantir que o banco de dados e o sistema de arquivos estejam consistentes,
                // você deve lançar uma exceção aqui e tratá-la no código que chama deleteByID.
            }
        }

        // Deleta o usuário do banco de dados
        super.deleteByID(id);
    }
}

