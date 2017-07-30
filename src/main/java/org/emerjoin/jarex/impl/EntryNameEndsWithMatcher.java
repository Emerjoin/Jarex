package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.MatchContext;
import org.emerjoin.jarex.Matcher;
import org.emerjoin.jarex.query.EntryNameEndsWithQuery;
import org.emerjoin.jarex.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Mário Júnior
 */
public class EntryNameEndsWithMatcher implements Matcher {

    private static Logger _log = LoggerFactory.getLogger(EntryNameEndsWithMatcher.class);

    public boolean supports(Query query) {

        return query instanceof EntryNameEndsWithQuery;

    }

    @Override
    public boolean doMatch(MatchContext context, Query query, URL url) {

        EntryNameEndsWithQuery q = (EntryNameEndsWithQuery) query;
        JarFile jarFile = context.getJar(url);
        String part = q.getPart();

        List<JarEntry> entryList = jarFile.stream().filter(entry -> entry.getName().endsWith(part))
                .collect(Collectors.toList());

        if(_log.isDebugEnabled())
            _log.debug("No match for entry name ends with : "+part);

        entryList.forEach(entry -> {
            if (_log.isDebugEnabled())
                _log.debug(String.format("Entry name [%s] ends with [%s]", entry.getName(), part));
            context.publishMatch(q, context.createMatch(url, entry));
        });


        return entryList.size()>0;

    }

}
