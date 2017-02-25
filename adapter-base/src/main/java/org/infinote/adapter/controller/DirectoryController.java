package org.infinote.adapter.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infinote.adapter.api.DirectoryApi;
import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.DirectoryChildren;
import org.infinote.adapter.model.FileStoreObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author santanu
 */
@RestController
@RequestMapping("/api/folders")
public class DirectoryController {
    private static Logger logger = LoggerFactory.getLogger(DirectoryController.class);

    @Autowired
    private DirectoryApi directoryApi;

    @RequestMapping
    public Directory root() {
        logger.trace("Getting root directory");
        return directoryApi.getRoot();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Directory getDirectory(@PathVariable("id")String id) {
        logger.trace("Getting directory with id {}", id);
        return directoryApi.getDirectory(id);
    }

    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    public DirectoryChildren getChildren(@PathVariable("id")String id) {
        logger.trace("Getting children of directory with id {}", id);
        return directoryApi.getChildren(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Directory createDirectory(@RequestBody Directory directory) {
        logger.debug("Creating directory with name {}", directory.getName());
        return directoryApi.createDirectory(directory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDirectory(@PathVariable("id")String id) {
        logger.debug("Deleting directory with id {}", id);
        directoryApi.deleteDirectory(id);
    }

    @RequestMapping(value = "/{id}/copy", method = RequestMethod.POST)
    public void copyDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        logger.debug("Copying directory with id {}", id);
        directoryApi.copyDirectory(id, json.findValue("parentId").asText());
    }

    @RequestMapping(value = "/{id}/move", method = RequestMethod.PUT)
    public void moveDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        logger.debug("Moving directory with id {}", id);
        directoryApi.moveDirectory(id, json.findValue("parentId").asText());
    }

    @RequestMapping(value = "/{id}/rename", method = RequestMethod.PUT)
    public void renameDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        logger.debug("Renaming directory with id {}", id);
        directoryApi.renameDirectory(id, json.findValue("name").asText());
    }
}
