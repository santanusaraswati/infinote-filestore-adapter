package org.infinote.adapter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author santanu
 */
public class FileStoreObject {
    private String id;
    // identifier of the parent directory
    private String parentId;
    private String name;
    private String createdBy;
    // time in millis
    private long createdTime;
    private String modifiedBy;
    // time in millis
    private long modifiedTime;
    // some version, can be derived from modified time
    private String version;
    // list of permissions of this file
    private List<Permission> permissions = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getVersion() {
        if (version == null || version.length() == 0)
            version = Long.toString(getModifiedTime());
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
