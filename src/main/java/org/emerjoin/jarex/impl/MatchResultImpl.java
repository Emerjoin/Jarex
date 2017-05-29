package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.MatchGroup;
import org.emerjoin.jarex.MatchResult;
import org.emerjoin.jarex.query.Query;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Mário Júnior
 */
public class MatchResultImpl implements MatchResult {

    public MatchResultImpl(Map<Query,MatchGroup> groups, List<URL> matchedJars){

        this.matchedJars = matchedJars;
        this.matchGroups = groups;

    }

    private Map<Query,MatchGroup> matchGroups;
    private List<URL> matchedJars;

    @Override
    public MatchGroup getGroup(Query query) {

        return matchGroups.get(query);

    }

    @Override
    public Iterable<MatchGroup> getGroups() {

        return matchGroups.values();

    }

    @Override
    public Stream<MatchGroup> streamGroups() {

        return matchGroups.values().stream();

    }

    @Override
    public Collection<URL> getJarsWithHit() {

        return matchedJars;

    }

}
