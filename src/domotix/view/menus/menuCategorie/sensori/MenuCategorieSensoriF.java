package domotix.view.menus.menuCategorie.sensori;


import domotix.controller.Rappresentatore;
import static domotix.view.ViewUtil.*;
import domotix.view.MyMenu;

/** @author Edoardo Coppola*/
public class MenuCategorieSensoriF {
    private static final String TITOLO = "Menu Categorie Sensori Fruitore ";
    private static final String[] VOCI = { "Visualizza Categorie Sensori"};
    private static final String INDIETRO = "Indietro";

    private MyMenu menu;
    private Rappresentatore r;

    public MenuCategorieSensoriF(MyMenu menu, Rappresentatore r) {
        this.menu = menu;
        this.menu.setTitolo(TITOLO);
        this.menu.setVoci(VOCI);
        this.r = r;
    }

    /**
     * Presenta all'utente fruitore un menu che consente di visualizzare tutte le categorie di sensori presenti oppure di
     * tornare indietro e chiuedere questo menu
     */
    public void avvia(){
ripristinaMenuOriginale(menu, TITOLO, VOCI);

        int sceltaMenu = 0;
        do {
            sceltaMenu = menu.scegli(INDIETRO);

            switch(sceltaMenu) {
                case 0://Indietro
                    return;
                case 1: //visualizza categorie sensori
                    for (String descrizione: r.getDescrizioniCategorieSensori()) {
                        System.out.println(descrizione);
                    }
                    break;
            }
        }while(sceltaMenu != 0);
    }
}
