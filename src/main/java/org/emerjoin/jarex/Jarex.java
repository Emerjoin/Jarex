package org.emerjoin.jarex;

import org.emerjoin.jarex.impl.MatchGroupImpl;
import org.emerjoin.jarex.impl.MatchItemImpl;
import org.emerjoin.jarex.impl.MatchResultImpl;
import org.emerjoin.jarex.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Mário Júnior
 */
public class Jarex {

    private MatchContext matchContext;

    private List<Class> disabledMatchers = new ArrayList<Class>();
    private List<Matcher> matchers = new ArrayList<Matcher>();
    private Collection<URL> urlList = null;

    private Map<Query,MatchMode> queriesMap = new HashMap<Query, MatchMode>();
    private Map<Query,List<MatchItem>> matchGroups = new HashMap<>();

    private Map<String,JarFile> jarFileMap = new HashMap<>();
    private List<URL> jarsWithHits = new ArrayList<>();

    private MatchResult result = null;
    private ClassLoader matchersClassLoader;

    private static Logger LOG = LoggerFactory.getLogger(Jarex.class);

    public static Jarex createInstance(ClassLoader classLoader){

        if(!(classLoader instanceof URLClassLoader))
            throw new IllegalArgumentException("URLClassLoader required");
        return new Jarex(Arrays.asList(((URLClassLoader) classLoader).getURLs()));

    }

    public static Jarex createInstance(Collection<URL> classpath){

        return new Jarex(classpath);

    }

    public static Jarex createInstance(URL[] classpath){

        return new Jarex(Arrays.asList(classpath));

    }

    private Jarex(Collection<URL> urls){

        if(urls.size()==0)
            throw new IllegalArgumentException("At least one URL is required to proceed");

        this.urlList = urls;

    }

    public Jarex disable(Class<? extends Matcher> matcherClass){

        this.disabledMatchers.add(matcherClass);
        return this;

    }

    public Jarex reset(){

        this.result = null;
        this.disabledMatchers.clear();
        this.queriesMap.clear();
        this.matchers.clear();
        this.matchGroups.clear();
        this.jarsWithHits.clear();
        this.closeJars();
        return this;

    }

    private void mapQueries(MatchMode mode, Query... queries){

        if(queries==null)
            throw new IllegalArgumentException("queries array must not be null");

        if(queries.length==0)
            throw new IllegalArgumentException("queries array must have length higher than 0");


        for(Query q: queries){
            if(q==null)
                throw new IllegalArgumentException("null Query instances are not allowed");
            queriesMap.put(q,mode);
            matchGroups.put(q,new ArrayList<>());
        }


    }

    public Jarex one(Query... queries){

        mapQueries(MatchMode.FIRST,queries);
        return this;

    }


    public Jarex all(Query... queries){

        mapQueries(MatchMode.ALL,queries);
        return this;

    }


    private Map<Query,MatchGroup> createMatchGroups(){

        Map<Query,MatchGroup> groups = new HashMap<>();
        for(Query key: matchGroups.keySet())
            groups.put(key, new MatchGroupImpl(key,matchGroups.get(key)));
        return groups;

    }


    private boolean matchJar(Query query, URL jarURL){

        MatchMode mode = queriesMap.get(query);
        for(Matcher matcher : matchers){

            if(!matcher.supports(query))
                continue;

            boolean matched = matcher.doMatch(matchContext,query,jarURL);
            if(matched)
                jarsWithHits.add(jarURL);

            if(matched&&mode==MatchMode.FIRST){
                if(LOG.isDebugEnabled())
                    LOG.debug(String.format("First Match goal achieved for query [%s] using [%s]",query.toString(),
                            matcher.getClass().getName()));
                return true;
            }

        }

        return false;

    }

    private void doMatch(Query query){

        for(URL url: urlList){

            if(matchJar(query,url))
                return;

        }

    }


    private void prepareMatchers(){

        this.matchers.clear();
        Iterator<Matcher> matcherIterator = null;
        if(matchersClassLoader!=null)
             matcherIterator = ServiceLoader.load(Matcher.class,matchersClassLoader).iterator();
        else matcherIterator = ServiceLoader.load(Matcher.class).iterator();

        while (matcherIterator.hasNext()){
            Matcher matcher = matcherIterator.next();
            if(disabledMatchers.contains(matcher.getClass()))
                continue;
            matchers.add(matcher);
        }

    }

    protected JarFile getJar(URL url){
        try {

            JarFile jarFile = jarFileMap.get(url.toString());

            if (jarFile == null) {
                File file = null;
                if(!url.getProtocol().equals("file")){
                    throw new JarexException(String.format("URL protocol not supported : %s",
                            url.getProtocol()));
                }

                file = new File(url.toURI());
                if(file.exists()&&file.isDirectory())
                    return null;

                jarFile = new JarFile(file);
                jarFileMap.put(url.toString(), jarFile);
            }

            return jarFile;

        }catch (FileNotFoundException ex){

            LOG.warn("Jar File not found : "+url.toString());
            return null;

        }catch (IOException | URISyntaxException ex){

            throw new JarexException(String.format("Failed to get JarFile instance for URL: %s",url),ex);

        }
    }

    private void createContext(){

        matchContext = new MatchContext() {

            @Override
            public MatchMode getMatchMode(Query query) {
                return queriesMap.get(query);
            }

            @Override
            public JarFile getJar(URL url) {
                return Jarex.this.getJar(url);
            }

            @Override
            public void publishMatch(Query q, MatchItem matchItem) {
                List<MatchItem> items = matchGroups.get(q);
                if(items==null)
                    return;
                items.add(matchItem);
                matchGroups.put(q,items);
            }

            @Override
            public MatchItem createMatch(URL jar, JarEntry entry) {
                return new MatchItemImpl(jar,entry);
            }

        };
    }

    public MatchResult getResult(){
        if(result !=null)
            return result;

        if(queriesMap.size()==0)
            throw new IllegalStateException("No Query set! Please submit a query before attempting to retrieve result");

        prepareMatchers();
        if(matchers.size()==0)
            throw new JarexException("No Matchers available to proceed. " +
                    "Make sure there Matcher service implementations in your classpath and that they aren't all disabled for the current Jarex instance");
        createContext();
        queriesMap.keySet().stream().forEach(this::doMatch);
        return result = new MatchResultImpl(createMatchGroups(),jarsWithHits);

    }

    public ResultsWrapper withResults(){

        return new ResultsWrapper(this);

    }

    private void closeJars(){

        for(JarFile file : jarFileMap.values()){

            try {

                file.close();

            }catch (IOException ex){

                throw new JarexException(String.format("Failed to close JarFile instance: %s",
                            file.getName()),ex);
            }
        }
    }

    public Jarex setMatchersClassLoader(ClassLoader classLoader){

        this.matchersClassLoader = classLoader;
        return this;
    }

    public void close(){

        closeJars();

    }



}
