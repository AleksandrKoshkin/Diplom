package controller;

import dto.FileNameInRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.AuthorizationService;
import service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@Validated
public class FileController {
    private final FileService fileService;
    private final AuthorizationService authorizationService;

    public FileController(FileService fileService, AuthorizationService authorizationService) {
        this.fileService = fileService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public void uploadFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @NotBlank @RequestBody MultipartFile file) throws IOException {
        authorizationService.checkToken(authToken);
        fileService.addFile(filename, file.getBytes());
    }

    @DeleteMapping
    public void deleteFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        authorizationService.checkToken(authToken);
        fileService.deleteFile(filename);
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        authorizationService.checkToken(authToken);
        return fileService.getFile(filename);
    }

    @PutMapping
    public void editFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @Valid @RequestBody FileNameInRequest newFilename) {
        authorizationService.checkToken(authToken);
        fileService.editFileName(filename, newFilename.getFilename());
    }
}
