package org.infinote.adapter.ram.api;

import org.infinote.adapter.api.FileApi;
import org.infinote.adapter.model.FileObject;
import org.springframework.stereotype.Component;

/**
 * @author santanu
 */
@Component
public class InMemoryFileApi implements FileApi {
    @Override
    public FileObject getFile(String id) {
        return null;
    }
}
