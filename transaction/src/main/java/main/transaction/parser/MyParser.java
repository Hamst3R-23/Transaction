package main.transaction.parser;

import main.transaction.model.Valute;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class MyParser {

    public static Valute parse(String valuteName, InputSource http) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler(valuteName);
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            parser.parse(http, handler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return handler.getValute();
    }
}