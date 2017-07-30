package org.emerjoin.jarex.query;

/**
 * @author Mário Júnior
 */
public class EntryNameEqualsQuery implements Query {

    private String path;

    public EntryNameEqualsQuery(String path){

        this.path = path;

    }

    public String getPath(){

        return this.path;

    }

    public String toString(){

        return String.format("FileEntry=%s",path);

    }


}
