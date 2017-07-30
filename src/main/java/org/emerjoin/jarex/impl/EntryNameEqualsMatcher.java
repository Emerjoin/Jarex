package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.MatchContext;
import org.emerjoin.jarex.Matcher;
import org.emerjoin.jarex.query.EntryNameEqualsQuery;
import org.emerjoin.jarex.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mário Júnior
 */
public class EntryNameEqualsMatcher implements Matcher {

    private static Logger _log = LoggerFactory.getLogger(EntryNameEqualsMatcher.class);

    public boolean supports(Query query) {

        return query instanceof EntryNameEqualsQuery;

    }

    @Override
    public boolean doMatch(MatchContext context, Query query, URL url) {

        EntryNameEqualsQuery q = (EntryNameEqualsQuery) query;
        JarFile jarFile = context.getJar(url);
        String path = q.getPath();

        JarEntry entry = jarFile.getJarEntry(path);
        if(entry==null)
            return false;

        if(_log.isDebugEnabled())
            _log.debug(String.format("Matching JarEntry for %s found in %s",path,url));
        context.publishMatch(q,context.createMatch(url,entry));
        return true;

    }

}
