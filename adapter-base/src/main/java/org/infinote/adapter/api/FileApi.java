package org.infinote.adapter.api;

import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.FileObject;

import java.io.InputStream;

/**
 * @author santanu
 */
public interface FileApi {
    /**
     * The implementation accesses the underlying file store to get the file based on the identifier passed
     * @param id
     * @return
     */
    FileObject getFile(String id);

    /**
     * The implementation gets the content of the file identified by the identifier passed
     * @param id
     * @return
     */
    InputStream getFileContent(String id);
}
