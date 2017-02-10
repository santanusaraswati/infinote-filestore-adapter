package org.infinote.adapter.ram.model;

import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.Permission;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author santanu
 */
public class DirectoryEntity {
    private String id;
    private DirectoryEntity parent;
    private String name;
    private String createdBy;
    private long createdTime;
    private String modifiedBy;
    private long modifiedTime;
    private List<DirectoryEntity> dirChildren = new ArrayList<>();
    private List<FileEntity> fileChildren = new ArrayList<>();
    private List<PermissionEntity> permissions = new ArrayList<>();

    public DirectoryEntity() {
    }

    public DirectoryEntity(String id, Path directoryPath, BasicFileAttributes attrs) {
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

    public List<DirectoryEntity> getDirChildren() {
        return dirChildren;
    }

    public void setDirChildren(List<DirectoryEntity> dirChildren) {
        this.dirChildren = dirChildren;
    }

    public List<FileEntity> getFileChildren() {
        return fileChildren;
    }

    public void setFileChildren(List<FileEntity> fileChildren) {
        this.fileChildren = fileChildren;
    }

    public void addChild(DirectoryEntity dir) {
        this.dirChildren.add(dir);
        dir.setParent(this);
    }

    public void addChild(FileEntity file) {
        this.fileChildren.add(file);
        file.setParent(this);
    }

    public List<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public Directory toDirectory() {
        Directory dir = new Directory();
        dir.setId(getId());
        dir.setName(getName());
        if (getParent() != null)
            dir.setParentId(getParent().getId());
        dir.setCreatedBy(getCreatedBy());
        dir.setCreatedTime(getCreatedTime());
        dir.setModifiedBy(getModifiedBy());
        dir.setModifiedTime(getModifiedTime());
        dir.setPermissions(getPermissions().stream().map(PermissionEntity::toPermission).collect(Collectors.toList()));
        return dir;
    }
}
