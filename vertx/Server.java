import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.AbstractVerticle;
import java.util.function.Consumer;

// execute: /opt/jboss/vert.x-3.0.0-milestone6/bin/vertx run Server.java
/*
 * * @author <a href="http://tfox.org">Tim Fox</a>
 * */
public class Server extends AbstractVerticle {

    private static final String CORE_EXAMPLES_DIR = "core-examples";
    private static final String CORE_EXAMPLES_JAVA_DIR = CORE_EXAMPLES_DIR + "/src/main/java/";

    public static void main(String[] args) {
        Class verticleId = Server.class;
        String exampleDir = CORE_EXAMPLES_JAVA_DIR + verticleId.getPackage().getName().replace(".", "/");
        VertxOptions options = new VertxOptions().setClustered(false);
        System.setProperty("vertx.cwd", exampleDir);

        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle(verticleId.getName());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                    res.cause().printStackTrace();
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }

    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x!</h1></body></html>");
        }).listen(8080);
    }
}
