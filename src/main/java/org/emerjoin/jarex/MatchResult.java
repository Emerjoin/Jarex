package org.emerjoin.jarex;

import org.emerjoin.jarex.query.Query;

import java.net.URL;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Mário Júnior
 */
public interface MatchResult {

     MatchGroup getGroup(Query query);
     Iterable<MatchGroup> getGroups();
     Stream<MatchGroup> streamGroups();
     Collection<URL> getJarsWithHit();

}
