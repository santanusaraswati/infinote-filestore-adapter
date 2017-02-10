package org.infinote.adapter.api;

import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.FileObject;

/**
 * @author santanu
 */
public interface FileApi {
    /**
     * The implementation accesses the underlying file store to get the file based on the idemtifier passed
     * @param id
     * @return
     */
    FileObject getFile(String id);
}
