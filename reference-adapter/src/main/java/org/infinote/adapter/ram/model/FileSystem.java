package org.infinote.adapter.ram.model;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author santanu
 */
public class FileSystem {
    private static FileSystem ourInstance = new FileSystem();

    public static FileSystem getInstance() {
        return ourInstance;
    }

    private DirectoryEntity root;
    private Map<String, DirectoryEntity> directories = new HashMap<>();
    private Map<String, FileEntity> files = new HashMap<>();

    private FileSystem() {
        try {
            String userHome = System.getProperty("user.home");
            Files.walkFileTree(Paths.get(userHome), new FileSystemVisitor());
            System.out.println("Finished creating file system tree!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DirectoryEntity getRoot() {
        return root;
    }

    public Optional<DirectoryEntity> getDirectory(String id) {
        return Optional.ofNullable(directories.get(id));
    }

    public List<DirectoryEntity> getChildrenDirs(String id) {
        return Optional.ofNullable(directories.get(id)).map(DirectoryEntity::getDirChildren).orElse(Collections.emptyList());
    }

    public List<FileEntity> getChildrenFiles(String id) {
        return Optional.ofNullable(directories.get(id)).map(DirectoryEntity::getFileChildren).orElse(Collections.emptyList());
    }

    private class FileSystemVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attr) throws IOException {
            if (attr.isRegularFile()) {
                FileEntity fileEntity = new FileEntity(Base64.getEncoder().encodeToString(file.toString().getBytes("UTF-8")), file, attr);
                files.put(fileEntity.getId(), fileEntity);
                String parentId = Base64.getEncoder().encodeToString(file.getParent().toString().getBytes("UTF-8"));
                DirectoryEntity parentDirectory = directories.get(parentId);
                parentDirectory.addChild(fileEntity);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (root == null) {
                root = new DirectoryEntity(Base64.getEncoder().encodeToString(dir.toString().getBytes("UTF-8")), dir, attrs);
                directories.put(root.getId(), root);
                return FileVisitResult.CONTINUE;
            }
            if (!Files.isHidden(dir)) {
                DirectoryEntity directoryEntity = new DirectoryEntity(Base64.getEncoder().encodeToString(dir.toString().getBytes("UTF-8")), dir, attrs);
                directories.put(directoryEntity.getId(), directoryEntity);
                String parentId = Base64.getEncoder().encodeToString(dir.getParent().toString().getBytes("UTF-8"));
                DirectoryEntity parentDirectory = directories.get(parentId);
                parentDirectory.addChild(directoryEntity);
                return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
        }
    }
}
