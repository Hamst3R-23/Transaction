package main.transaction.parser;


import main.transaction.model.Valute;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;

public class SaxParserHandler extends DefaultHandler {

    private final String TAG_CHAR_CODE = "CharCode";

    private final String TAG_VALUE = "Value";

    private final String valuteNameHandler;

    private String currentTagName;

    private String currentCharCode;

    boolean isValute = false;

    public SaxParserHandler(String valuteName) {
        this.valuteNameHandler = valuteName;
    }

    Valute valute = new Valute();

    public Valute getValute() {
        return valute;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTagName = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTagName == null) return;


        if (currentTagName.equals(TAG_CHAR_CODE)) {
            if (new String(ch, start, length).equals(valuteNameHandler)) {
                isValute = true;
            }
        }

        if (currentTagName.equals(TAG_VALUE) && isValute) {
            valute.setName(valuteNameHandler);
            valute.setValue(new BigDecimal(new String(ch, start, length).replaceAll(",", ".")));
            isValute = false;
        }

    }
}
