
# Adding to your project

## Repository
```xml
   <repositories>
        <repository>
            <id>Emerjoin</id>
            <name>maven-repo</name>
            <url>https://github.com/Emerjoin/maven-repo/raw/master</url>
        </repository>
    </repositories>
```xml


## Dependency
```xml
    <dependency>
         <groupId>org.emerjoin</groupId>
         <artifactId>Jarex</artifactId>
         <version>1.0.0</version>
    </dependency>
```xml

# Usage Examples

## Looking for beans.xml in all jars

```java
    
    Query beansXML = Queries.fileEntry("META-INF/beans.xml");
    
    ClassLoader classLoader = //get classloader here
    Jarex.createInstance(classLoader)
         .all(beansXML) //Find all
         .withResults().of(beansXML).forEach(item -> {
            
            InputStream stream = item.getInputStream();
            //do whatever you want here
            
         });
    
```

## Looking for persistence.xml

```java
    
    Query persistenceXML = Queries.fileEntry("META-INF/persistence.xml");
    
    ClassLoader classLoader = //get classloader here
    Optional<ResultsWrapper.Item> persistenceDescriptor = Jarex.createInstance(classLoader)
         .one(persistenceXML)
         .withResults().of(persistenceXML).findFirst();
    
```

## Finding both beans.xml and persistence.xml using a single Jarex Instance

```java

    Query persistenceXML = Queries.fileEntry("META-INF/persistence.xml");
    Query beansXML = Queries.fileEntry("META-INF/beans.xml");
    
    ClassLoader classLoader = //get classloader here
    Jarex jarex = Jarex.createInstance(classLoader)
        .one(persistenceXML)
        .all(beansXML);
   
    Optional<ResultsWrapper.Item> persistenceDescriptor = jarex.withResults()
        .of(persistenceXML).findFirst();
     
    jarex.withResults().of(beansXML).forEach(item -> {
                         
         InputStream stream = item.getInputStream();
         //do whatever you want here
                         
    });
        


```