package org.infinote.adapter.ram.api;

import org.infinote.adapter.api.DirectoryApi;
import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.DirectoryChildren;
import org.infinote.adapter.model.FileStoreObject;
import org.infinote.adapter.ram.model.DirectoryEntity;
import org.infinote.adapter.ram.model.FileEntity;
import org.infinote.adapter.ram.model.FileSystem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author santanu
 */
@Component
public class InMemoryDirectoryApi implements DirectoryApi {
    @Override
    public Directory getRoot() {
        return FileSystem.getInstance().getRoot().toDirectory();
    }

    @Override
    public Directory getDirectory(String id) {
        return FileSystem.getInstance().getDirectory(id).map(DirectoryEntity::toDirectory).orElse(null);
    }

    @Override
    public DirectoryChildren getChildren(String id) {
        return new DirectoryChildren(Stream.concat(FileSystem.getInstance().getChildrenDirs(id).stream().map(DirectoryEntity::toDirectory),
                FileSystem.getInstance().getChildrenFiles(id).stream().map(FileEntity::toFile)).collect(Collectors.toList()));
    }

    @Override
    public Directory createDirectory(Directory directory) {
        return directory;
    }

    @Override
    public void deleteDirectory(String id) {
        // implement...
    }

    @Override
    public void copyDirectory(String id, String toDir) {
        // implement...
    }

    @Override
    public void moveDirectory(String id, String toDir) {
        // implement...
    }

    @Override
    public void renameDirectory(String id, String newName) {
        // implement...
    }
}
