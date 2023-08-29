package main.transaction.parser;

import main.transaction.exception.HttpRequestException;
import main.transaction.exception.ParserException;
import main.transaction.model.Valute;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ParserXml {

    public static Valute parse(String valuteName, InputSource http) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler(valuteName);
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(http, handler);
        }  catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParserException("Parser exception");
        }

        return handler.getValute();
    }
}