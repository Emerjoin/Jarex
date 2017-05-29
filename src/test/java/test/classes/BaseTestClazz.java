package test.classes;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.io.File;

/**
 * @author Mário Júnior
 */
public class BaseTestClazz {

    protected File createJar(File metaInfXml, Class... classes){

        try {

            File tempJarFile = File.createTempFile("jarex", "test");

            JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
            ArchivePath pathToPickersXML = ArchivePaths.create("META-INF/"+metaInfXml.getName());
            archive.addAsResource(metaInfXml, pathToPickersXML);
            for(Class clazz : classes)
                archive.addClass(clazz);
            archive.as(ZipExporter.class).exportTo(tempJarFile,true);

            return tempJarFile;

        }catch (Throwable ex){

            throw new RuntimeException("Failed to create Jar Archive",ex);

        }

    }

}
