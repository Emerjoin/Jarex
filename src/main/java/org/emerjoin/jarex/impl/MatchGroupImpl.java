package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.MatchGroup;
import org.emerjoin.jarex.MatchItem;
import org.emerjoin.jarex.query.Query;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Mário Júnior
 */
public class MatchGroupImpl implements MatchGroup {

    public MatchGroupImpl(Query query, Collection<MatchItem> items){
        this.query = query;
        this.items = items;
    }

    private Query query;
    private Collection<MatchItem> items = null;

    @Override
    public Query getQuery() {

        return query;

    }

    @Override
    public Iterable<MatchItem> iterable() {

        return items;

    }

    @Override
    public Stream<MatchItem> stream() {

        return items.stream();

    }
}
