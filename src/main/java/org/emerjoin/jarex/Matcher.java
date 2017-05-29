package org.emerjoin.jarex;
import org.emerjoin.jarex.query.Query;

import java.net.URL;
import java.util.jar.JarFile;

/**
 * @author Mário Júnior
 */
public interface Matcher {

    boolean supports(Query query);
    boolean doMatch(MatchContext context, Query query, URL url);

}
