package org.emerjoin.jarex.query;

import org.emerjoin.jarex.impl.EntryNamePartQuery;

/**
 * @author Mario Junior.
 */
public class EntryNameEndsWithQuery extends EntryNamePartQuery {

    public EntryNameEndsWithQuery(String part){
        super(part);
    }

    public String toString(){

        return String.format("EntryNameEndsWith=%s",getPart());

    }

}
