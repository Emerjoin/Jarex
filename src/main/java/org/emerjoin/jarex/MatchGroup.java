package org.emerjoin.jarex;

import org.emerjoin.jarex.query.Query;

import java.util.stream.Stream;

/**
 * @author Mário Júnior
 */
public interface MatchGroup {

    Query getQuery();
    Iterable<MatchItem> iterable();
    Stream<MatchItem> stream();

}
