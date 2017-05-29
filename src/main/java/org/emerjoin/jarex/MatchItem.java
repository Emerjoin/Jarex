package org.emerjoin.jarex;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mário Júnior
 */
public interface MatchItem {

    URL getJarURL();
    JarEntry getEntry();
}
