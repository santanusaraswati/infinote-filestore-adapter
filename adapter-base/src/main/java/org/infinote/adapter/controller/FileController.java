package org.infinote.adapter.controller;

import org.infinote.adapter.api.FileApi;
import org.infinote.adapter.model.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileApi fileApi;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public FileObject file(@PathVariable("id") String id) {
        return fileApi.getFile(id);
    }

    @RequestMapping(value = "/{id}/content", method = RequestMethod.GET)
    public InputStream content(@PathVariable("id") String id) {
        return fileApi.getFileContent(id);
    }
}