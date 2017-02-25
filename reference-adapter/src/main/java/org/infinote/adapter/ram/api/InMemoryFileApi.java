package org.infinote.adapter.ram.api;

import org.infinote.adapter.api.FileApi;
import org.infinote.adapter.model.FileObject;
import org.infinote.adapter.ram.model.FileEntity;
import org.infinote.adapter.ram.model.FileSystem;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author santanu
 */
@Component
public class InMemoryFileApi implements FileApi {
    @Override
    public FileObject getFile(String id) {
        return getFileObject(id).orElse(null);
    }

    @Override
    public InputStream getFileContent(String id) {
        return FileSystem.getInstance().getFileContent(id).orElse(null);
    }

    @Override
    public List<FileObject> getVersions(String id) {
        // as FileSystem does not support version we just have the latest version available to serve
        return getFileObject(id).map(f -> Collections.singletonList(f)).orElse(Collections.emptyList());
    }

    @Override
    public InputStream getFileContent(String id, String version) {
        // version is not supported by file systems, so we just get whatever content available ignoring the version
        return getFileContent(id);
    }

    private Optional<FileObject> getFileObject(String id) {
        return FileSystem.getInstance().getFile(id).map(FileEntity::toFile);
    }
}