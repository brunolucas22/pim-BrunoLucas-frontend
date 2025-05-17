package br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.models.DocumentsUserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import jakarta.transaction.Transactional;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.repository.DocumentsUserRepository;
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
public class DocumentsUserService extends AbstractService<DocumentsUserModel, Integer> {

    private DocumentsUserRepository documentUserRepository;
    private static final String UPLOAD_DIR = "uploads/documents/user/";

    public DocumentsUserService(DocumentsUserRepository documentUserRepository) {
        super();
        this.documentUserRepository = documentUserRepository;
    }

    @Transactional
    public String uploadDocument(Long idUser, Long id, MultipartFile file) throws IOException {

        Optional<DocumentsUserModel> documentUserModel = documentUserRepository.findById(id);
        if (documentUserModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        DocumentsUserModel document = documentUserModel.get();

        // Criar o diretório se não existir
        Path uploadDirPath = Paths.get(UPLOAD_DIR + idUser + '/');
        Files.createDirectories(uploadDirPath);

        // Gerar um nome único para o arquivo
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDirPath.resolve(fileName);

        // Remover o arquivo antigo, se existir
        if (document.getDocumentDocumentUser() != null) {
            Path oldFilePath = Paths.get(document.getDocumentDocumentUser());
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
        document.setDocumentDocumentUser(filePath.toString());
        super.update(document);

        return filePath.toString(); // Retorna o caminho do arquivo
    }

    public ResponseEntity<String> getDocument(Long id) throws IOException {
        Optional<DocumentsUserModel> documentUserModel = documentUserRepository.findById(id);
        if (documentUserModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        String documentPath = documentUserModel.get().getDocumentDocumentUser();
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
        Optional<DocumentsUserModel> documentUserModel = documentUserRepository.findById(id);

        if (documentUserModel.isEmpty()) {
            throw new RequestDataInvalidException("Documento não encontrado.");
        }

        DocumentsUserModel documentUser = documentUserModel.get();

        // Deleta o arquivo se existir
        if (documentUser.getDocumentDocumentUser() != null) {
            try {
                Path photoPath = Paths.get(documentUser.getDocumentDocumentUser());
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

