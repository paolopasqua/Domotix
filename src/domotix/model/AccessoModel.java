package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.util.SommarioDispositivi;

import java.util.List;

/**
 * Implementazione dell'interfaccia Model.
 */
public class AccessoModel implements Model {
    private ElencoCategorieAttuatori elencoCategorieAttuatori;
    private ElencoAzioniProgrammate elencoAzioniProgrammate;
    private ElencoAttuatori elencoAttuatori;
    private ElencoUnitaImmobiliari elencoUnitaImmobiliari;
    private ElencoSensori elencoSensori;
    private ElencoCategorieSensori elencoCategorieSensori;
    private SensoreOrologio sensoreOrologio;

    public AccessoModel() {
        this.sensoreOrologio = new SensoreOrologio();
        this.elencoCategorieSensori = new ElencoCategorieSensori();
        this.elencoSensori = new ElencoSensori(this.sensoreOrologio);
        this.elencoUnitaImmobiliari = new ElencoUnitaImmobiliari();
        this.elencoAttuatori = new ElencoAttuatori();
        this.elencoAzioniProgrammate = new ElencoAzioniProgrammate();
        this.elencoCategorieAttuatori = new ElencoCategorieAttuatori();
    }

    @Override
    public List<UnitaImmobiliare> getListaUnita() {
        return this.elencoUnitaImmobiliari.getUnita();
    }

    @Override
    public Stanza getStanza(String stanza, String unita) {
        for (Stanza stz : this.getUnita(unita).getStanze()) {
            if (stz.getNome().equals(stanza))
                return stz;
        }
        return null;
    }

    @Override
    public List<CategoriaSensore> getCategorieSensore() {
        return this.elencoCategorieSensori.getCategorie();
    }

    @Override
    public List<CategoriaAttuatore> getCategorieAttuatore() {
        return this.elencoCategorieAttuatori.getCategorie();
    }

    @Override
    public Artefatto getArtefatto(String artefatto, String stanza, String unita) {
        return this.getStanza(unita, stanza).getArtefatto(artefatto);
    }

    @Override
    public UnitaImmobiliare getUnita(String unita) {
        return this.elencoUnitaImmobiliari.getUnita(unita);
    }

    @Override
    public SensoreOrologio getOrologio() {
        return this.sensoreOrologio;
    }

    @Override
    public Sensore getSensore(String sensore) {
        return this.elencoSensori.getDispositivo(sensore);
    }

    @Override
    public Attuatore getAttuatore(String attuatore) {
        return this.elencoAttuatori.getDispositivo(attuatore);
    }

    @Override
    public Azione getAzioneProgrammata(String id) {
        return this.elencoAzioniProgrammate.getAzione(id);
    }

    @Override
    public List<Azione> getAzioniProgrammate() {
        return this.elencoAzioniProgrammate.getAzioni();
    }

    @Override
    public CategoriaAttuatore getCategoriaAttuatore(String nomeCategoriaAttuatore) {
        return this.elencoCategorieAttuatori.getCategoria(nomeCategoriaAttuatore);
    }

    @Override
    public CategoriaSensore getCategoriaSensore(String nomeCategoriaSensore) {
        return this.elencoCategorieSensori.getCategoria(nomeCategoriaSensore);
    }

    @Override
    public SommarioDispositivi getSommarioSensori() {
        return this.elencoSensori;
    }

    @Override
    public SommarioDispositivi getSommarioAttuatori() {
        return this.elencoAttuatori;
    }

}