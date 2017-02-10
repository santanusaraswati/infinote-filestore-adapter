package org.infinote.adapter.ram.model;

import org.infinote.adapter.model.Permission;

/**
 * @author santanu
 */
public class PermissionEntity {
    private String userId;
    private boolean read;
    private boolean update;
    private boolean delete;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Permission toPermission() {
        Permission permission = new Permission();
        permission.setUserId(getUserId());
        permission.setRead(isRead());
        permission.setUpdate(isUpdate());
        permission.setDelete(isDelete());
        return permission;
    }
}
