import org.emerjoin.jarex.Jarex;
import org.emerjoin.jarex.JarexException;
import org.emerjoin.jarex.ResultsWrapper;
import org.emerjoin.jarex.impl.FileEntryMatcher;
import org.emerjoin.jarex.query.Queries;
import org.emerjoin.jarex.query.Query;
import org.junit.Test;
import test.classes.BaseTestClazz;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Mário Júnior
 */
public class JarexTest extends BaseTestClazz {

    private static final String BASE_DIR = "test-jar-contents"+File.separator;
    private static final String JAR_1_EXAMPLE_XML = BASE_DIR+"jar-1"+File.separator+"example.xml";
    private static final String JAR_2_EXAMPLE_XML = BASE_DIR+"jar-2"+File.separator+"example.xml";
    private static final String JAR_3_WELCOME_XML = BASE_DIR+"jar-3"+File.separator+"welcome.xml";


    private URL getURL(File file) throws Exception {

        return file.toURI().toURL();

    }

    @Test
    public void find_all_must_return_a_single_hit() throws Exception {

        Query exampleXml = Queries.fileEntry("META-INF/example.xml");

        File jarFile =  createJar(new File(JAR_1_EXAMPLE_XML));
        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jarFile)));
        jarex.find(exampleXml);
        long total = jarex.withResults().of(exampleXml).count();
        assertEquals(1,total);
        Optional<ResultsWrapper.Item> item = jarex.withResults().of(exampleXml).findFirst();
        assertTrue(item.isPresent());
        InputStream xmlInputStream = item.get().getInputStream();
        assertNotNull(xmlInputStream);
    }

    @Test
    public void find_all_and_find_first_must_return_2_and_1_hits_respectively() throws Exception{

        Query exampleXml = Queries.fileEntry("META-INF/example.xml");
        Query welcomeXml = Queries.fileEntry("META-INF/welcome.xml");

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));
        File jar2 =  createJar(new File(JAR_2_EXAMPLE_XML));
        File jar3 =  createJar(new File(JAR_3_WELCOME_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1), getURL(jar2), getURL(jar3)));
        jarex.find(exampleXml);
        jarex.findFirst(welcomeXml);
        assertEquals(2,jarex.withResults().of(exampleXml).count());
        assertEquals(1,jarex.withResults().of(welcomeXml).count());

    }


    @Test
    public void find_first_must_return_2_hits() throws Exception{

        Query exampleXml = Queries.fileEntry("META-INF/example.xml");

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));
        File jar2 =  createJar(new File(JAR_2_EXAMPLE_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1), getURL(jar2)));
        jarex.findFirst(exampleXml);
        assertEquals(1,jarex.withResults().of(exampleXml).count());

    }


    @Test(expected = IllegalArgumentException.class)
    public void cant_create_an_instance_with_an_empty_urls_list(){

        Jarex.createInstance(new ArrayList<URL>());

    }

    @Test(expected = IllegalArgumentException.class)
    public void cant_create_an_instance_with_an_empty_urls_array(){

        Jarex.createInstance(new URL[]{});

    }

    @Test(expected = IllegalArgumentException.class)
    public void cant_create_an_instance_with_a_non_url_classloader(){

        Jarex.createInstance(new ClassLoader(){});

    }

    @Test(expected = IllegalStateException.class)
    public void cant_retrieve_results_without_specifying_queries() throws Exception{

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));
        File jar2 =  createJar(new File(JAR_2_EXAMPLE_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1), getURL(jar2)));
        jarex.getResult();

    }

    @Test(expected = JarexException.class)
    public void cant_proceed_with_no_active_matcher() throws Exception{

        Query exampleXml = Queries.fileEntry("META-INF/example.xml");

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));
        File jar2 =  createJar(new File(JAR_2_EXAMPLE_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1), getURL(jar2)));
        jarex.disable(FileEntryMatcher.class)
                .findFirst(exampleXml)
                .getResult();


    }

    @Test(expected = IllegalArgumentException.class)
    public void at_least_one_query_should_be_passed_when_calling_find() throws Exception{

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1)));
        jarex.disable(FileEntryMatcher.class)
                .find()
                .getResult();


    }

    @Test(expected = IllegalArgumentException.class)
    public void at_least_one_query_should_be_passed_when_calling_find_first() throws Exception{

        File jar1 =  createJar(new File(JAR_1_EXAMPLE_XML));

        Jarex jarex = Jarex.createInstance(Arrays.asList(getURL(jar1)));
        jarex.disable(FileEntryMatcher.class)
                .findFirst()
                .getResult();

    }




}
