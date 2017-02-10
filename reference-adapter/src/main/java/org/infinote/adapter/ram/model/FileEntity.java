package org.infinote.adapter.ram.model;

import org.infinote.adapter.model.FileObject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author santanu
 */
public class FileEntity {
    private String id;
    private DirectoryEntity parent;
    private String name;
    private String createdBy;
    private long createdTime;
    private String modifiedBy;
    private long modifiedTime;
    private String content;
    private List<PermissionEntity> permissions = new ArrayList<>();

    public FileEntity() {
    }

    public FileEntity(String id, Path directoryPath, BasicFileAttributes attrs) {
        this.id = id;
        File dir = directoryPath.toFile();
        this.name = dir.getName();
        this.createdTime = attrs.creationTime().toMillis();
        this.modifiedTime = attrs.lastModifiedTime().toMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DirectoryEntity getParent() {
        return parent;
    }

    public void setParent(DirectoryEntity parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileObject toFile() {
        FileObject fileObject = new FileObject();
        fileObject.setId(getId());
        fileObject.setName(getName());
        fileObject.setParentId(getParent().getId());
        fileObject.setCreatedBy(getCreatedBy());
        fileObject.setCreatedTime(getCreatedTime());
        fileObject.setModifiedBy(getModifiedBy());
        fileObject.setModifiedTime(getModifiedTime());
        fileObject.setVersion(Long.toString(getModifiedTime()));
        fileObject.setPermissions(getPermissions().stream().map(PermissionEntity::toPermission).collect(Collectors.toList()));
        return fileObject;
    }
}

