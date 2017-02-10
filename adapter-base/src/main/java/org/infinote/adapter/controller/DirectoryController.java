package org.infinote.adapter.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infinote.adapter.api.DirectoryApi;
import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.DirectoryChildren;
import org.infinote.adapter.model.FileStoreObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author santanu
 */
@RestController
@RequestMapping("/api/folders")
public class DirectoryController {
    @Autowired
    private DirectoryApi directoryApi;

    @RequestMapping
    public Directory root() {
        return directoryApi.getRoot();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Directory getDirectory(@PathVariable("id")String id) {
        return directoryApi.getDirectory(id);
    }

    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    public DirectoryChildren getChildren(@PathVariable("id")String id) {
        return directoryApi.getChildren(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Directory createDirectory(@RequestBody Directory directory) {
        return directoryApi.createDirectory(directory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDirectory(@PathVariable("id")String id) {
        directoryApi.deleteDirectory(id);
    }

    @RequestMapping(value = "/{id}/copy", method = RequestMethod.POST)
    public void copyDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        directoryApi.copyDirectory(id, json.findValue("parentId").asText());
    }

    @RequestMapping(value = "/{id}/move", method = RequestMethod.PUT)
    public void moveDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        directoryApi.moveDirectory(id, json.findValue("parentId").asText());
    }

    @RequestMapping(value = "/{id}/rename", method = RequestMethod.PUT)
    public void renameDirectory(@PathVariable("id")String id, @RequestBody JsonNode json) {
        directoryApi.renameDirectory(id, json.findValue("name").asText());
    }
}
