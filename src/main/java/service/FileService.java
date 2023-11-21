package service;

import dto.FileDescriptionInResponse;
import entiti.File;
import org.springframework.stereotype.Service;
import repository.FileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public synchronized void addFile(String filename, byte[] file) {
        fileRepository.save(new File(filename, file));
    }

    public synchronized void deleteFile(String filename) {
        if (!fileRepository.existsById(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteById(filename);
    }

    public byte[] getFile(String filename) {
        final File file = getFileByName(filename);
        return file.getFileContent();
    }

    public synchronized void editFileName(String oldFilename, String newFilename) {
        final File fileEntity = getFileByName(oldFilename);
        final File newFileEntity = new File(newFilename, fileEntity.getFileContent());
        fileRepository.delete(fileEntity);
        fileRepository.save(newFileEntity);
    }

    public List<FileDescriptionInResponse> getFileList(int limit) {
        final List<File> files = fileRepository.getFiles(limit);
        return files.stream()
                .map(file -> new FileDescriptionInResponse(file.getFileName(), file.getFileContent().length))
                .collect(Collectors.toList());
    }

    private File getFileByName(String filename) {
        return fileRepository.findById(filename).orElseThrow(() ->
                new RuntimeException("File " + filename + " not found"));
    }
}
