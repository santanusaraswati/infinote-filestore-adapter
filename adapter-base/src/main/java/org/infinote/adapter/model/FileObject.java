package org.infinote.adapter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author santanu
 */
public class FileObject extends FileStoreObject {

    private String contentType;
    // version identifier
    private String version;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
