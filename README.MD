# Usage Examples

## Looking for every beans.xml in all jars of a ClassLoader

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

## Looking for one persistence.xml in all jars of a ClassLoader

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
    ResultsWrapper results = Jarex.createInstance(classLoader)
        .one(persistenceXML)
        .all(beansXML)
        .withResults();
    
    
    Optional<ResultsWrapper.Item> persistenceDescriptor = results.of(persistenceXML).findFirst();
     
    results.of(beansXML).forEach(item -> {
                         
         InputStream stream = item.getInputStream();
         //do whatever you want here
                         
    });
        


```