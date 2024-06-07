import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

public class LettoreXML {
    public void leggiXML(String nomeFile) {
        try {
            File fileXML = new File(nomeFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileXML);

            doc.getDocumentElement().normalize();

            System.out.println("Elemento radice: " + doc.getDocumentElement().getNodeName());

            // Lettura dei ruoli
            NodeList ruoliNodeList = doc.getElementsByTagName("ruolo");
            System.out.println("Ruoli:");
            for (int i = 0; i < ruoliNodeList.getLength(); i++) {
                Node ruoloNode = ruoliNodeList.item(i);
                if (ruoloNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ruoloElement = (Element) ruoloNode;
                    System.out.println("Ruolo: " + ruoloElement.getTextContent());
                }
            }

            // Lettura dei personaggi
            NodeList personaggiNodeList = doc.getElementsByTagName("personaggio");
            System.out.println("Personaggi:");
            for (int i = 0; i < personaggiNodeList.getLength(); i++) {
                Node personaggioNode = personaggiNodeList.item(i);
                if (personaggioNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element personaggioElement = (Element) personaggioNode;
                    System.out.println("Nome: " + personaggioElement.getElementsByTagName("nome").item(0).getTextContent());
                    System.out.println("Punti Ferita: " + personaggioElement.getAttribute("pf"));
                    System.out.println("Descrizione: " + personaggioElement.getElementsByTagName("descrizione").item(0).getTextContent());
                }
            }

            // Lettura delle armi
            NodeList armiNodeList = doc.getElementsByTagName("arma");
            System.out.println("Armi:");
            for (int i = 0; i < armiNodeList.getLength(); i++) {
                Node armaNode = armiNodeList.item(i);
                if (armaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element armaElement = (Element) armaNode;
                    System.out.println("Nome: " + armaElement.getElementsByTagName("nome").item(0).getTextContent());
                    System.out.println("Distanza: " + armaElement.getElementsByTagName("distanza").item(0).getTextContent());
                    System.out.println("Copie totali: " + armaElement.getElementsByTagName("copie").item(0).getAttributes().getNamedItem("totale").getTextContent());
                }
            }

            // Lettura delle carte...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
