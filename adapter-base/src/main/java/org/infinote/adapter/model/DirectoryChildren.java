package org.infinote.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author santanu
 */
public class DirectoryChildren {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("entries")
    private List<FileStoreObject> children = new ArrayList<>();

    public DirectoryChildren() {
    }

    public DirectoryChildren(List<FileStoreObject> children) {
        this(children.size(), children);
    }

    public DirectoryChildren(int totalCount, List<FileStoreObject> children) {
        this.totalCount = totalCount;
        this.children = children;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<FileStoreObject> getChildren() {
        if (children == null) children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<FileStoreObject> children) {
        this.children = children;
    }
}
