package org.infinote.adapter.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.infinote.adapter.api.FileApi;
import org.infinote.adapter.model.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileApi fileApi;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public FileObject file(@PathVariable("id") String id) {
        logger.trace("Getting meta data of file with id {}", id);
        return fileApi.getFile(id);
    }

    @RequestMapping(value = "/{id}/content", method = RequestMethod.GET)
    public void content(@PathVariable("id") String id, HttpServletResponse response) {
        logger.trace("Getting content of file with id {}", id);
        try (InputStream fileContent = fileApi.getFileContent(id);) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            IOUtils.copy(fileContent, response.getOutputStream());
        } catch (IOException e) {
            logger.error("Failed to get content of file with id " + id, e);
            try {
                response.sendError(500, e.getMessage());
            } catch (IOException e1) {
                logger.error("Failed to send error while getting content of file with id " + id, e1);
            }
        }
    }

    @RequestMapping(value = "/{id}/versions", method = RequestMethod.GET)
    public List<FileObject> versions(@PathVariable("id") String id) {
        logger.trace("Getting meta data of versions of file with id {}", id);
        return fileApi.getVersions(id);
    }

    @RequestMapping(value = "/{id}/{version}/content", method = RequestMethod.GET)
    public void versionContent(@PathVariable("id") String id, @PathVariable("version") String version, HttpServletResponse response) {
        logger.trace("Getting content of file with id {} and version {}", id, version);
        try (InputStream fileContent = fileApi.getFileContent(id, version)) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            IOUtils.copy(fileContent, response.getOutputStream());
        } catch (IOException e) {
            logger.error("Failed to get content of file with id " + id + " and version " + version, e);
            try {
                response.sendError(500, e.getMessage());
            } catch (IOException e1) {
                logger.error("Failed to send error while getting content of file with id " + id + " and version " + version, e1);
            }
        }
    }
}