package org.infinote.adapter.ram.api;

import org.infinote.adapter.api.FileApi;
import org.infinote.adapter.model.FileObject;
import org.infinote.adapter.ram.model.FileEntity;
import org.infinote.adapter.ram.model.FileSystem;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author santanu
 */
@Component
public class InMemoryFileApi implements FileApi {
    @Override
    public FileObject getFile(String id) {
        return FileSystem.getInstance().getFile(id).map(FileEntity::toFile).orElse(null);
    }

    @Override
    public InputStream getFileContent(String id) {
        return FileSystem.getInstance().getFileContent(id).orElse(null);
    }
}