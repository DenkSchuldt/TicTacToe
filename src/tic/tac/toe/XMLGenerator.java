/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author rodrigo
 */
public class XMLGenerator {
    
    private ArrayList<Token>tokensDrawer;
    private int id_state;
    private String color;
    
    public XMLGenerator(ArrayList<Token>tokens,int id){
        this.tokensDrawer=tokens;
        this.id_state=id;
        this.color="#ffffff";
    }
    
    public void setColor(String c){
        this.color=c;
    }
    
    public void generateStateXML(int id) throws Exception {
        String name = "state" + id;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        //se crea la el tablero de tic tac toe
        Element raiz = document.getDocumentElement();
        createTemplate(raiz, document);

        Element shapeNode = document.createElement("shape");
        shapeNode.setAttribute("name", name);

        raiz.appendChild(shapeNode);
        
        Element backNode = document.createElement("background");
        shapeNode.appendChild(backNode);
        
        Element ellipse = document.createElement("ellipse");
        ellipse.setAttribute("x", "0");
        ellipse.setAttribute("y", "0");
        ellipse.setAttribute("w", "100");
        ellipse.setAttribute("h", "100");
        backNode.appendChild(ellipse);
        
        Element Stroke=document.createElement("strokecolor");
        Stroke.setAttribute("color",color);
        backNode.appendChild(Stroke);
        
        Element strokewidth=document.createElement("strokewidth");
        strokewidth.setAttribute("width","2");
        backNode.appendChild(strokewidth);
        
        Element fillcolor=document.createElement("fillcolor");
        fillcolor.setAttribute("color",color);
        backNode.appendChild(fillcolor);
        
        backNode.appendChild(document.createElement("fillstroke"));
       
        Element itemNode = document.createElement("foreground");
        shapeNode.appendChild(itemNode);

        Element childItemNode = document.createElement("include-shape");
        childItemNode.setAttribute("x", "25");
        childItemNode.setAttribute("y", "0");
        childItemNode.setAttribute("w", "50");
        childItemNode.setAttribute("h", "50");
        childItemNode.setAttribute("name", "tablero");
        itemNode.appendChild(childItemNode);
        
        if(this.tokensDrawer!=null){
            for (Token t : this.tokensDrawer) {
                Element child = document.createElement("include-shape");
                if (t.getJugador() == 1) {
                    child.setAttribute("name", "cross");
                } else {
                    child.setAttribute("name", "circle");
                }
                child.setAttribute("x", "" + (int) t.getPoint().getX());
                child.setAttribute("y", "" + (int) t.getPoint().getY());
                child.setAttribute("w", "30");
                child.setAttribute("h", "30");
                itemNode.appendChild(child);
            }
        }

        // Generate XML
        Source source = new DOMSource(document);
        // Indicamos donde lo queremos almacenar
        Result result = new StreamResult(new java.io.File(name + ".xml")); // nombre
        // del
        // archivo
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.transform(source, result);
    }

    void createTemplate(Element raiz, Document document) {
        Element shapeNode = document.createElement("shape");
        shapeNode.setAttribute("name", "tablero");
        shapeNode.setAttribute("aspect", "fixed");
        raiz.appendChild(shapeNode);
        
        Element foregroundNode = document.createElement("foreground");
        shapeNode.appendChild(foregroundNode);

        Element strokeColor = document.createElement("strokecolor");
        strokeColor.setAttribute("color", "#000000");
        foregroundNode.appendChild(strokeColor);

        Element strokeWidth = document.createElement("strokewidth");
        strokeWidth.setAttribute("width", "4");
        foregroundNode.appendChild(strokeWidth);

        Element lineCap = document.createElement("linecap");
        lineCap.setAttribute("cap", "round");
        foregroundNode.appendChild(lineCap);

        Element path = document.createElement("path");
        path.setAttribute("crisp", "0");
        foregroundNode.appendChild(path);

        path.appendChild(createElement(document, "move", "25", "25"));
        path.appendChild(createElement(document, "line", "25", "175"));

        Element stroke = document.createElement("stroke");
        foregroundNode.appendChild(stroke);

        Element pa = document.createElement("path");
        foregroundNode.appendChild(pa);

        stroke = document.createElement("stroke");
        foregroundNode.appendChild(stroke);

        pa.appendChild(createElement(document, "move", "75", "25"));
        pa.appendChild(createElement(document, "line", "75", "175"));

        Element pathTwo = document.createElement("path");
        pathTwo.setAttribute("crisp", "1");
        foregroundNode.appendChild(pathTwo);

        pathTwo.appendChild(createElement(document, "move", "-25", "75"));
        pathTwo.appendChild(createElement(document, "line", "125", "75"));

        Element strokeTwo = document.createElement("stroke");
        foregroundNode.appendChild(strokeTwo);

        Element paTwo = document.createElement("path");
        foregroundNode.appendChild(paTwo);

        strokeTwo = document.createElement("stroke");
        foregroundNode.appendChild(strokeTwo);

        paTwo.appendChild(createElement(document, "move", "-25", "125"));
        paTwo.appendChild(createElement(document, "line", "125", "125"));

        createCross(raiz, document);
        createCircle(raiz, document);
    }

    public Element createElement(Document document, String name, String x, String y) {
        Element move = document.createElement(name);
        move.setAttribute("x", x);
        move.setAttribute("y", y);
        return move;
    }

    public void createCross(Element raiz, Document document) {
        Element shapeNode = document.createElement("shape");
        shapeNode.setAttribute("name", "cross");
        shapeNode.setAttribute("aspect", "fixed");
        raiz.appendChild(shapeNode);

        Element foregroundNode = document.createElement("foreground");
        shapeNode.appendChild(foregroundNode);

        Element strokeColor = document.createElement("strokecolor");
        strokeColor.setAttribute("color", "#000ff");
        foregroundNode.appendChild(strokeColor);

        Element fillColor = document.createElement("fillcolor");
        fillColor.setAttribute("color", "#00000");
        foregroundNode.appendChild(fillColor);

        Element strokeWidth = document.createElement("strokewidth");
        strokeWidth.setAttribute("width", "6");
        foregroundNode.appendChild(strokeWidth);

        Element lineCap = document.createElement("linecap");
        lineCap.setAttribute("cap", "round");
        foregroundNode.appendChild(lineCap);

        Element path = document.createElement("path");
        path.setAttribute("crisp", "0");
        foregroundNode.appendChild(path);

        path.appendChild(createElement(document, "move", "25", "25"));
        path.appendChild(createElement(document, "line", "75", "75"));

        Element stroke = document.createElement("stroke");
        foregroundNode.appendChild(stroke);

        Element pathTwo = document.createElement("path");
        pathTwo.appendChild(createElement(document, "move", "75", "25"));
        pathTwo.appendChild(createElement(document, "line", "25", "75"));
        foregroundNode.appendChild(pathTwo);

        stroke = document.createElement("stroke");
        foregroundNode.appendChild(stroke);

    }

    public void createCircle(Element raiz, Document document) {

        Element shapeNode = document.createElement("shape");                
        shapeNode.setAttribute("name", "circle");
        shapeNode.setAttribute("aspect", "fixed");
        raiz.appendChild(shapeNode);

        Element back = document.createElement("background");
        shapeNode.appendChild(back);
        
        Element ellipse = document.createElement("ellipse");
        ellipse.setAttribute("x", "0");
        ellipse.setAttribute("y", "0");
        ellipse.setAttribute("w", "50");
        ellipse.setAttribute("h", "50");
        back.appendChild(ellipse);
        
        Element strokeColor = document.createElement("strokecolor");
        strokeColor.setAttribute("color","#000000");
        back.appendChild(strokeColor);
        
        Element strokeWidth=document.createElement("strokeWidth");
        strokeWidth.setAttribute("width","2");
        back.appendChild(strokeWidth);
        
        Element fillColor=document.createElement("color");
        fillColor.setAttribute("color","#ffffff");
        back.appendChild(fillColor);
        
        Element foreground=document.createElement("foreground");
        
        shapeNode.appendChild(foreground);
        Element fillstroke=document.createElement("fillstroke");
        foreground.appendChild(fillstroke);
    }
}
