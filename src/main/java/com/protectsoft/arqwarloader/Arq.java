package com.protectsoft.arqwarloader;

import java.io.File;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.container.ClassContainer;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.container.ResourceContainer;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;

/**
 *
 * @author Avraam Piperidis
 */
public class Arq {
    
    private Archive<?> archive;
    
    private static Arq singleton = null;
    
    /**
     * <b>Default options</b> <br>
     * persistance.xml is loaded from src/main/resources/META-INF/persistence.xml as persistence.xml <br> <br>
     * glassfish-web.xml is loaded from src/test/resources/META-INF/glassfish-web.xml as glassfish-web.xml <br> <br>
     * example-1.0-SNAPSHOT.war is loaded from target/example-1.0-SNAPSHOT.war as example-1.0-SNAPSHOT_test.war <br> <br>
     * @param appNameVersion  eg. mywebapp-1.0-SNAPSHOT <br>
     * @return this
     */
     public static Arq Init(String appNameVersion) {
        if(appNameVersion.endsWith(".war")){
            appNameVersion = appNameVersion.substring(0,appNameVersion.length()-4);
        }
        singleton = new Arq();
        singleton.setArchive(ShrinkWrap.create(ZipImporter.class, appNameVersion+"_test.war").importFrom(new File("target/"+appNameVersion+".war"))
                    .as(WebArchive.class)
                    .addAsResource(new File("src/main/resources/META-INF/persistence.xml"),"persistence.xml")     
                    .addAsWebInfResource(new File("src/test/resources/META-INF/glassfish-web.xml"),"glassfish-web.xml")
                    );
       
        return singleton;
    }
    
    /**
     * 
     * @param archiveTarget the war name to load, must be different than warFile eg mywebapp_test.war
     * @param warFile the path to the original app war eg mywebapp.war
     * @param resourcePath ex. the path to the persistance.xml eg src/main/resources/META-INF/persistence.xml
     * @param resourceTarget load the resourcePath file AS the resourceTarget file.
     * @param webInfResourcePath load a webInfResourcePath eg.  src/test/resources/META-INF/glassfish-web.xml
     * @param webInfTarget load webInfResourcePath as ..the filename
     * @return this
     */
    public static Arq Init(String archiveTarget,String warFile,String resourcePath,String resourceTarget,String webInfResourcePath,String webInfTarget){
        singleton = new Arq();
        singleton.setArchive(ShrinkWrap.create(ZipImporter.class, archiveTarget).importFrom(new File(warFile))
                    .as(WebArchive.class)
                    .addAsResource(new File(resourcePath),resourceTarget)
                    .addAsWebInfResource(new File(webInfResourcePath),webInfTarget));
        
        return singleton;
    }
    
    
    public Arq appendResources(String... resources){
        for(String res: resources){
            ((ResourceContainer<? extends Archive<?>>)this.getArchive()).addAsResource(res);
        }
        return this;
    }
    
    public Arq appendClasses(Class... classes){
        ((ClassContainer<? extends Archive<?>>)this.getArchive()).addClasses(classes);
        return this;
    }
    
    public Arq appendPackages(String... packages){
         for(String res: packages){
            ((ClassContainer<? extends Archive<?>>)this.getArchive()).addPackage(res);
        }
         return this;
    }
    
     /**
     * 
     * @param pomLibraries , 
     * for example to load a library from pom.xml,
     * 
     * {@code 
     *   <dependency>
     *       <groupId>org.mockito</groupId>
     *       <artifactId>mockito-core</artifactId>
     *       <version>2.7.18</version>
     *       <scope>test</scope>
     *   </dependency>
     * }
     *  
     * then the String should be "org.mockito:mockito-core:2.7.18"
     * @return this
     */
    public Arq appendLibraries(String... pomLibraries) {
        MavenResolverSystem resolver = Maven.resolver();
        for(String res: pomLibraries){
            ((LibraryContainer<? extends Archive<?>>)this.getArchive())
                    .addAsLibraries(resolver.loadPomFromFile("pom.xml")
                            .resolve(res)
                            .withTransitivity()
                            .asFile());
        }
        return this;
    }
    
    
    public Archive<?> loadWar() {
        return archive;
    }
    
    private void setArchive(Archive<?> archive) {
        this.archive = archive;
    }
    
    private Archive<?> getArchive() {
        return archive;
    }
    
}
