package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.query.Query;

/**
 * @author Mario Junior.
 */
public abstract class EntryNamePartQuery implements Query {

    private String part = null;

    public EntryNamePartQuery(String part){
        if(part==null||part.isEmpty())
            throw new IllegalArgumentException("Entry name part must not be null nor empty");
        this.part = part;
    }


    public String getPart(){

        return part;

    }



}
