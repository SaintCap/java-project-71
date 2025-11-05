package hexlet.code.gendiff.utils;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public final class FileInfo {
    private final Path filePath;
    private final String ext;
    
    public FileInfo(String path) throws NoSuchFileException {

        filePath = Paths.get(path).toAbsolutePath();
        var file = filePath.toFile();
        if (!file.exists()) {
            throw new NoSuchFileException("File not found: " + filePath);
        }

        ext = FilenameUtils.getExtension(file.getName());
    }
}
