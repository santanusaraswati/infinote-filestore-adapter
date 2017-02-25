package org.infinote.adapter.api;

import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.FileObject;

import java.io.InputStream;
import java.util.List;

/**
 * @author santanu
 */
public interface FileApi {
    /**
     * The implementation accesses the underlying file store to get the latest verions of the file based on the
     * identifier passed. The identifier remains same for the file and it does not chnage with versions.
     * @param id the identifier of the file, should not be null
     * @return the {@link FileObject} with the metadata of the file
     */
    FileObject getFile(String id);

    /**
     * The implementation gets the content of the latest version of the file identified by the identifier passed
     * @param id the identifier of the file
     * @return the content of the the file
     */
    InputStream getFileContent(String id);

    /**
     * Gets all the versions of the file.
     * @param id the identifier of the file
     * @return a {@link List} of {@link FileObject}s containing the metadata of all the versions
     */
    List<FileObject> getVersions(String id);

    /**
     * The contnet of the given version of a file.
     * @param id the identifier of the file
     * @param version the version of the file
     * @return the content of the specific version passed
     */
    InputStream getFileContent(String id, String version);
}
