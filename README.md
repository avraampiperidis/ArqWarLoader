# ArqWarLoader
---------------
A simple Arquillian .war loader

#### Install
```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.protectsoft</groupId>
    <artifactId>ArqWarLoader</artifactId>
    <version>1.3</version>
    <scope>test</scope>
</dependency>
```
<b>OR just copy this class</b>
https://github.com/avraampiperidis/ArqWarLoader/blob/master/src/main/java/com/protectsoft/arqwarloader/Arq.java  <br>

<b>Usage</b>
```java
@RunWith(Arquillian.class)
public class Test2IT {
       
    @Deployment 
    //with defaults persistance.xml src/main/resources/META-INF/
    //glassfish-web.xml  src/test/resources/META-INF/
    public static Archive<?> createDeployment() {
            return Arq.Init("mywebapp-1.0.war")
                .loadWar();
    }
 
    @Test
    public void test() {
        assertTrue(true);
    }   
}
```
more options <br>
```java
@RunWith(Arquillian.class)
public class TestIT  {
    
    @Deployment 
    public static Archive<?> createDeployment() {
          return Arq.Init("webapp-1.0_test.war","target/webapp-1.0.war"
                  ,"src/main/resources/META-INF/persistence.xml","persistence.xml"
                  ,"src/test/resources/META-INF/glassfish-web.xml","glassfish-web.xml")
                .loadWar();
    }
   
    @Test
    public void test() {
        assertTrue(true);
    }
}
```
extra methods <br>
```java
appendClasses(String[])
appendLibraries(String[])
appendPackages(String[])
appendResources(String[])
```

## License
MIT License

Copyright (c) 2018 Avraam Piperidis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
