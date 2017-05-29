package org.emerjoin.jarex;

import org.emerjoin.jarex.query.Query;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 * @author Mário Júnior
 */
public class ResultsWrapper {

    public static class Item {

        private Jarex jarex = null;
        private MatchItem matchItem = null;

        public Item(MatchItem item, Jarex jarex){
            this.jarex = jarex;
            this.matchItem = item;
        }

        public InputStream getInputStream() {
            try {

                return jarex.getJar(matchItem.getJarURL())
                        .getInputStream(matchItem.getEntry());

            }catch (IOException ex){

                throw new JarexException("Failed to get JarEntry InputStream",ex);
            }
        }

        public MatchItem getMatchItem() {

            return matchItem;
        }

    }


    protected ResultsWrapper(Jarex jarex){

        this.jarex = jarex;

    }

    private Jarex jarex = null;

    public Stream<Item> of(Query query){
        MatchGroup group = jarex.getResult().getGroup(query);
        if(group == null)
            throw new JarexException("No matching group found for supplied Query");

        return group.stream().map(item -> new Item(item,jarex));

    }

    public MatchResult get(){

        return jarex.getResult();

    }


}
