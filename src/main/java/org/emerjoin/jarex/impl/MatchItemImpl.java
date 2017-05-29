package org.emerjoin.jarex.impl;

import org.emerjoin.jarex.MatchItem;

import java.net.URL;
import java.util.jar.JarEntry;

/**
 * @author Mário Júnior
 */
public class MatchItemImpl implements MatchItem {

    private URL url;
    private JarEntry entry;


    public MatchItemImpl(URL url, JarEntry entry){
        this.url = url;
        this.entry = entry;
    }

    @Override
    public URL getJarURL() {
        return url;
    }

    @Override
    public JarEntry getEntry() {

        return entry;

    }

}
