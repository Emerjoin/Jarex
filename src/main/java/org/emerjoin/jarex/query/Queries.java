package org.emerjoin.jarex.query;

/**
 * @author Mário Júnior
 */
public class Queries {

    public static Query fileEntry(String path){

        return new EntryNameEqualsQuery(path);

    }

    public static Query entryNameEquals(String path){

        return fileEntry(path);

    }

    public static Query entryNameStartsWith(String name){

        return new EntryNameStartsWithQuery(name);

    }

    public static Query entryNameEndsWith(String name){

        return new EntryNameEndsWithQuery(name);

    }

}
