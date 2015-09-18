# ErRabbit-log4j-apeender

Log4j 1.x Custom appender for ErRabbit
ErRabbit is visual remote logging system using Log4j. 
You cant track all your server applications on ErRabbit just add log4j-appender.
(log4j2.x is not required this custom appender, check out below ErRabbit link)

ErRabbit URL : https://github.com/soleaf/ErRabbit

#### Setup log4j.xml

1. Declare 'errabbit' appender to `log4j.xml` with your ActiveMQ URL, userName, password, rabbitID
 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

    <!-- Console view -->
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d [%t] %p - %C{1}.%M(%L) | %m%n"/>
        </layout>
    </appender>

    <appender name="errabbit" class="org.mintcode.errabbit.log4j.Log4jAppender">
        <param name="host" value="tcp://localhost:61616"/>
        <param name="rabbitID" value="example"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%5p [%d{HH:mm:ss}] %m%n"/>
        </layout>
    </appender>

    <logger name="org.mintcode.errabbit.example">
        <level value="ERROR"/>
        <appender-ref ref="errabbit"/>
    </logger>

    <!-- Root Logger -->
    <root>
        <priority value="INFO" />
        <appender-ref ref="console" />
    </root>
    
</log4j:configuration>
```

CustomAppender's source : https://github.com/soleaf/ErRabbit-log4j-apeender

#### Use In Application Code

You can collect all kind of log(info, debug, trace .. etc). But, for your application performance,
use only as exception error logging.

1. Get Log4j Logger
 
```java
Logger logger = Logger.getLogger(getClass());
```

1. Log error with exception, Just type `logger.error([message],e)`

```java
try{
    int a[] = new int[2];
    System.out.println("Access element three :" + a[3]);
}
catch (Exception e){
    logger.error(e.getMessage(),e);
}
```

Example Project : https://github.com/soleaf/ErRabbit-Example-log4j1


