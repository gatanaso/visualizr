# Visualizr
Simple vaadin dashboard for spring boot applications.

## Getting started
1. Add the visualizr dependency to an existing vaadin spring boot applicaiton:

```
    <dependency>
      <groupId>org.vaadin</groupId>
      <artifactId>visualizr</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
```

2. Run the application and navigate to _app-domain/#!visualizr_

For example, if running on localhost, navigate to http://localhost:8080/#!visualizr

## Configuration
The visualizr supports configuration of the UI endpoint via the __visualizr.endpoint__ property.

By default, the dashboard is available on the __visualizr__ path. To override this, simply add the following line to the _application.properties_ file of the spring boot application:

```
    visualizr.endpoint=[your-new-endpoint]
```

After which the visualizr dashboard UI will be available on _app-domain/#!your-new-endpoint_

## Note
The project uses [Vaadin Charts](https://vaadin.com/charts), which requires a license. You can use the free trial license to test the visualizr.
