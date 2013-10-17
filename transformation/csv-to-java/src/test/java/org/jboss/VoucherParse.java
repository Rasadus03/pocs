package org.jboss;

import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.milyn.container.ExecutionContext;
import org.milyn.csv.CSVRecordParserConfigurator;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class VoucherParse {

    public static final String SMOOKS_CONFIG_PATH="smooks.config.path";
    public static final String CSV_PATH="csv.path";

    private static String messageIn = readInputMessage();
    private static JavaResult javaResult = null;

    protected static void runSmooksTransform() throws IOException, SAXException, SmooksException {

        Smooks smooks = new Smooks(System.getProperty(SMOOKS_CONFIG_PATH));

        try {
            // Configure the execution context to generate a report...
            ExecutionContext executionContext = smooks.createExecutionContext();
            executionContext.setEventListener(new HtmlReportGenerator("/tmp/report.html"));

            javaResult = new JavaResult();
            smooks.filterSource(executionContext, new StringSource(messageIn), javaResult);

        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

        VoucherParse.runSmooksTransform();

        System.out.println("==============Message Out=============");
        System.out.println("main() # of java objects returned in Voucher list : "+ ((List)javaResult.getBean("voucherList")).size());
        System.out.println("main() # of java objects returned in VoucherLine list : "+ ((List)javaResult.getBean("voucherLineList")).size());
        System.out.println("======================================\n\n");
    }

    private static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream(System.getProperty(CSV_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>";
        }
    }
}
