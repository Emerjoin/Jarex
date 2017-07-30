package org.emerjoin.jarex.query;

import org.emerjoin.jarex.impl.EntryNamePartQuery;

/**
 * @author Mario Junior.
 */
public class FileEntryNameEndsWithQuery extends EntryNamePartQuery {

    public FileEntryNameEndsWithQuery(String part){
        super(part);
    }

    public String toString(){

        return String.format("EntryNameEndsWith=%s",getPart());

    }

}
