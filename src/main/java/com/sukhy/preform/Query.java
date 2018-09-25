/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sukhy.preform;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Sukhy <https://github.com/S-Mann>
 */
public class Query {

    public boolean queryBuilder(String values, String columns, String table, String condition) throws Exception {
        File fXmlFile = new File("src\\main\\resources\\config\\MetamugBoilerplate.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Request");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            String methodString = node.getAttributes().getNamedItem("method").getTextContent();
            String itemString = node.getAttributes().getNamedItem("item").getTextContent();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String textContent = element.getElementsByTagName("Query").item(0).getTextContent();
                String finalQuery = textContent.replace(Constants.MTG_TABLE, table)
                        .replace(Constants.MTG_COLUMNS, columns)
                        .replace(Constants.MTG_VALUES, values)
                        .replace(Constants.MTG_CONDITION, condition);
                element.getElementsByTagName("Query").item(0)
                        .setTextContent(finalQuery);
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        if (new File("src\\main\\resources\\generated\\").exists()) {
            StreamResult result = new StreamResult(new File("src\\main\\resources\\generated\\" + table + ".xml"));
            transformer.transform(source, result);
        } else {
            new File("src\\main\\resources\\generated\\").mkdir();
            StreamResult result = new StreamResult(new File("src\\main\\resources\\generated\\" + table + ".xml"));
            transformer.transform(source, result);
        }
        return true;
    }
}
