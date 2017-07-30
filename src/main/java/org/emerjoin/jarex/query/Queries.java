package org.emerjoin.jarex.query;

/**
 * @author Mário Júnior
 */
public class Queries {

    public static Query fileEntry(String path){

        return new FileEntryNameEqualsQuery(path);

    }

    public static Query fileEntryNameEquals(String path){

        return fileEntry(path);

    }

    public static Query fileEntryNameStartsWith(String name){

        return new FileEntryNameStartsWithQuery(name);

    }

    public static Query fileEntryNameEndsWith(String name){

        return new FileEntryNameEndsWithQuery(name);

    }

}
