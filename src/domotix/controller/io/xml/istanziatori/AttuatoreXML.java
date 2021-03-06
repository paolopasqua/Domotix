package domotix.controller.io.xml.istanziatori;

import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import domotix.controller.Recuperatore;
import domotix.controller.io.xml.CostantiXML;
import domotix.controller.io.xml.IstanziatoreXML;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.Modalita;

public class AttuatoreXML implements IstanziatoreXML<Attuatore> {

	private Recuperatore recuperatore = null;

	public AttuatoreXML(Recuperatore recuperatore) {
		this.recuperatore = recuperatore;
	}

    /** Metodo per scrittore: ATTUATORE **/
    @Override
    public Attuatore getInstance(Element el) throws Exception {
        return instanceElement(el, this.recuperatore);
	}

	/** Metodo per lettore: ATTUATORE **/
	public static Attuatore instanceElement(Element el, Recuperatore recuperatore) {
	    //controllo tag elemento
	    if (el.getTagName().equals(CostantiXML.NODO_XML_ATTUATORE)) {
	        String nome;
	        boolean stato;
	        String categoria;
	        CategoriaAttuatore cat;
	        String modalita;
	        Modalita mod;
	        Attuatore attuatore;
	
	        //estrazione attrubuti
	        if (el.hasAttribute(CostantiXML.NODO_XML_ATTUATORE_NOME)) {
	            nome = el.getAttribute(CostantiXML.NODO_XML_ATTUATORE_NOME);
	        } else
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): attributo " + CostantiXML.NODO_XML_ATTUATORE_NOME + " assente.");
	
	        //estrazione elementi
	        NodeList childs = el.getElementsByTagName(CostantiXML.NODO_XML_ATTUATORE_STATO);
	        if (childs.getLength() > 0) {
	            stato = childs.item(0).getTextContent().equalsIgnoreCase("1") ? true : false;
	        } else
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_ATTUATORE_STATO + " assente.");
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ATTUATORE_CATEGORIA);
	        if (childs.getLength() > 0) {
	            categoria = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_ATTUATORE_CATEGORIA + " assente.");
	
	        cat = recuperatore.getCategoriaAttuatore(categoria);
	        if (cat == null) {
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): lettura attuatore di categoria " + categoria + " inesistente o ancora non letta.");
	        }
	
	        childs = el.getElementsByTagName(CostantiXML.NODO_XML_ATTUATORE_MODALITA);
	        if (childs.getLength() > 0) {
	            modalita = childs.item(0).getTextContent();
	        } else
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): elemento " + CostantiXML.NODO_XML_ATTUATORE_MODALITA + " assente.");
	
	        mod = null;
	        for (Modalita m : cat.getElencoModalita()) {
	            if (m.getNome().equals(modalita)) {
	                mod = m;
	                break;
	            }
	        }
	        if (mod == null) {
	            throw new NoSuchElementException("AttuatoreXML.instanceElement(): lettura attuatore con modalita " + modalita + " sconosciuta.");
	        }
	
	        attuatore = new Attuatore(nome, cat, mod);
	        attuatore.setStato(stato);
	
	        //ritorno istanza corretta
	        return attuatore;
	    }
	    else
	        throw new NoSuchElementException("AttuatoreXML.instanceElement(): elemento " + el.getTagName() + "non di tipo " + CostantiXML.NODO_XML_ATTUATORE);
	}

	

	
    
}