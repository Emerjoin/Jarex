package org.emerjoin.jarex.query;

import org.emerjoin.jarex.impl.EntryNamePartQuery;

/**
 * @author Mario Junior.
 */
public class EntryNameStartsWithQuery extends EntryNamePartQuery {


    public EntryNameStartsWithQuery(String part){
        super(part);
    }

    public String toString(){

        return String.format("EntryNameStartsWith=%s",getPart());

    }

}
