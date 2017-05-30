package org.infinote.adapter.ram.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private WatchService watcher;

    private FileSystem() {
        try {
            String userHome = "/home/santanu/Documents";
            watcher = FileSystems.getDefault().newWatchService();
            Files.walkFileTree(Paths.get(userHome), new FileSystemVisitor());
            System.out.println("Finished creating file system tree!");
            addWatcher();
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

    public Optional<FileEntity> getFile(String id) {
        return Optional.ofNullable(files.get(id));
    }

    public Optional<InputStream> getFileContent(String id) {
        return Optional.ofNullable(files.get(id)).map(f -> {
            try {
                return Files.newInputStream(f.getFilePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public List<FileEntity> getChildrenFiles(String id) {
        return Optional.ofNullable(directories.get(id)).map(DirectoryEntity::getFileChildren).orElse(Collections.emptyList());
    }

    private class FileSystemVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attr) throws IOException {
            if (attr.isRegularFile() && !Files.isHidden(file)) {
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
                dir.register(watcher,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
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

    private void addWatcher() {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 100, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(2));
            executor.execute(() -> {
                for (;;) {

                    // wait for key to be signaled
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException x) {
                        return;
                    }

                    for (WatchEvent<?> event: key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        // This key is registered only
                        // for ENTRY_CREATE events,
                        // but an OVERFLOW event can
                        // occur regardless if events
                        // are lost or discarded.
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        // The filename is the
                        // context of the event.
                        WatchEvent<Path> ev = (WatchEvent<Path>)event;
                        Path filename = ev.context();

                        try {
                            // Resolve the filename against the directory.
                            // If the filename is "test" and the directory is "foo",
                            // the resolved name is "test/foo".
                            Path dir = (Path) key.watchable();
                            Path child = dir.resolve(filename);
                            System.out.println("Got file " + child.toString() + " operation " + kind.name());
                            if (Files.isDirectory(child) && kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                DirectoryEntity directoryEntity = new DirectoryEntity(Base64.getEncoder().encodeToString(child.toString().getBytes("UTF-8")), child, Files.readAttributes(child, BasicFileAttributes.class));
                                directories.put(directoryEntity.getId(), directoryEntity);
                                String parentId = Base64.getEncoder().encodeToString(dir.toString().getBytes("UTF-8"));
                                DirectoryEntity parentDirectory = directories.get(parentId);
                                parentDirectory.addChild(directoryEntity);
                                child.register(watcher,
                                        StandardWatchEventKinds.ENTRY_CREATE,
                                        StandardWatchEventKinds.ENTRY_DELETE,
                                        StandardWatchEventKinds.ENTRY_MODIFY);
                            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                    String id = Base64.getEncoder().encodeToString(child.toString().getBytes("UTF-8"));
                                    directories.remove(id);
                                    String parentId = Base64.getEncoder().encodeToString(dir.toString().getBytes("UTF-8"));
                                    DirectoryEntity parentDirectory = directories.get(parentId);
                                    parentDirectory.getDirChildren().removeIf(d -> id.equals(d.getId()));
                                    files.remove(id);
                                    parentDirectory.getFileChildren().removeIf(d -> id.equals(d.getId()));
                            }
                            continue;
                        } catch (IOException x) {
                            System.err.println(x);
                            continue;
                        }
                    }

                    // Reset the key -- this step is critical if you want to
                    // receive further watch events.  If the key is no longer valid,
                    // the directory is inaccessible so exit the loop.
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            });
    }
}
