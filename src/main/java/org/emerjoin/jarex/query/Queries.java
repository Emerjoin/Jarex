package org.emerjoin.jarex.query;

/**
 * @author Mário Júnior
 */
public class Queries {

    public static Query fileEntry(String path){

        return new FileEntryQuery(path);

    }

}
