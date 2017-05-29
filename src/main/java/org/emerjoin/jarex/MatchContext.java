package org.emerjoin.jarex;

import org.emerjoin.jarex.query.Query;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @author Mário Júnior
 */
public interface MatchContext {

    MatchMode getMatchMode(Query query);
    JarFile getJar(URL url);
    void publishMatch(Query q, MatchItem matchItem);
    MatchItem createMatch(URL jar, JarEntry entry);

}
