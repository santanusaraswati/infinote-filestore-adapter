package org.infinote.adapter.api;

import org.infinote.adapter.model.Directory;
import org.infinote.adapter.model.DirectoryChildren;
import org.infinote.adapter.model.FileStoreObject;

import java.util.List;

/**
 * @author santanu
 */
public interface DirectoryApi {
    /**
     * Gets the root directory.
     * One potential change is that it might expect a list going forward
     * @return
     */
    Directory getRoot();

    /**
     * Gets the directory by the identifier passed
     * @param id the id of the directory
     * @return
     */
    Directory getDirectory(String id);

    /**
     * Gets the children of the directory
     * @param id the id of the parent directory
     * @return a {@link DirectoryChildren} object containing the list of files and directories under the directory
     * identified by the id passed
     */
    DirectoryChildren getChildren(String id);

    /**
     * Creates the passed directory
     * @param directory the directory to be created
     * @return the created directory - some fields might get populated/updated
     */
    Directory createDirectory(Directory directory);

    /**
     * Delets the directory identified the id passed
     * @param id
     */
    void deleteDirectory(String id);

    /**
     * Copies a directory
     * @param id the id of the directory to be copied
     * @param toDir the id of the destination directory
     */
    void copyDirectory(String id, String toDir);

    /**
     * Moves a directory
     * @param id the identifier of the directory to be moved
     * @param toDir the identifier of the destination directory
     */
    void moveDirectory(String id, String toDir);

    /**
     * Renames a directory
     * @param id the id of the directory to be renamed
     * @param newName the new name of the directory
     */
    void renameDirectory(String id, String newName);
}
