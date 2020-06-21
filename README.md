# Project Name

Crawl Links

## Usage

start the Application: 

from ide:
run
        ```
        CrawllinksApplication
        ```

from command line:
```
    mvn clean package
    java -jar target/crawllinks-0.0.1-SNAPSHOT.jar
```

open swagger: 
http://127.0.0.1:8080/swagger-ui.html

APIs (sync and a-sync) are found under crawler-controller

## Credits

Written by: Ariel Malik

## Remarks

Since most of the sites takes long time to read and they have many child links
I've written some example pages

You can use: http://127.0.0.1:8080/examples/test1 as the URL

 *                    test3
 *                  /
 *            test2
 *          /      \
 *     test1        test5 (not exists)
 *          \
 *           test4
 *                \
 *                 test2 (exists under test) 


## Credits

Written by: Ariel Malik