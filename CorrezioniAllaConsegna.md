Correzioni alla consegna
========================

La versione v5.1 di Domotix rivoluziona il codice per realizzare le modifiche richieste durante la fase di correzione.

Le correzioni da apportare al codice date dal professore alla consegna sono le seguenti:
1. Evitare i Singleton
2. Evitare classi statiche per intere porzioni di progetto
3. Evitare i toString() per costruire la descrizione da mostrare a video per un'entità
4. Suddividere le classi troppo grosse in più classi (ed evitare "troppe" righe di codice)
5. Sostituire i tipi di ritorno dei metodi con le interface (ex ArrayList -> List, HashMap -> Map)
6. Interfaccia per nascondere la view contenente il metodo avvia (forse)

Le modifiche pensate per soddisfare le correzioni sono:
* Trasformare i singleton in classi istanziabili, in particolar modo:
  * Classi timer: non è necessario l'accesso globale, è sufficiente istanziarli nel main in quanto unico punto di uso.
  * Classi 'ElencoXXX': diventeranno classi da istanziare e di conseguenza essere passate ovunque siano necessari. 
  * Classi "operazioni iniziali e finali": da rivedere in modo da renderli istanziabili o renderle funzioni di utilità.
  * Classi di lettura, scrittura e altre operazioni sui dati locali: rendere le implementazioni delle interfaccie già presenti instanziabili.
    Passare queste istanze alle relative parti del controller che le utilizzano.
* Trasformare le classi statiche in classi istanziabili, dove le dipendenze sono istanze indicate nel costruttore e richiamate nei diversi metodi:
  * Classi controller: diventano singole classi istanziabili le quali richiedono il riferimento alle istanze del model nel costruttore.
  * Classi view: diventano singole classi istanziabili, le quali chiedono quasi tutte il riferimento all'istanza del controller nel costruttore.
    Inoltre, i diversi menu ramificati vengono istanziati all'interno della classe che lo utilizzano:
      ad esempio la classe 'MenuLogin' genera l'istanza della classe 'MenuFruitore' e 'MenuManutentore'. Inoltre si prevede di passare parametri ai costruttori dei vari menu a seconda di
      quello di cui hanno bisogno. Ad esempio passo ad un costruttore il nome di un'unità immobiliare o il nome di una stanza se quel meu offre operazioni sull'unità o sulla stanza
* Senza toccare i metodi toString() così costruiti, si rende indipendente la classe Recuperatore da essi costruendo la stringa dai singoli attributi (questo se necessario effettivamente).
    Eventualmente si può dividere questa responsabilità in un'altra classe.
* Onde evitare di dover passare molte istanze per rappresentare il model o il controller, si può pensare di implementare tale struttura:
  * Implementare un'interfaccia Model contenente i metodi per recuperare le istanze delle cinque entità principali (categorie, dispositivi e unità).
      In questo modo un solo riferimento ad un'istanza di questa interfaccia è da passare ai costruttori che utilizzano il model.
  * Sviluppare una classe AccessoModel, la quale implementa l'interfaccia sopra, per effettuare l'accesso al model con i metodi appositi.
  * Implementare un'interfaccia Controller analoga a quella per il model. Da analizzare se suddividere i molteplici metodi del controller in interfacce distinte o raggrupparle
    tutte in questa.
  * Sviluppare una classe AccessoController, la quale implementa l'interfaccia sopra, per effettuare l'accesso al controller in un'unica istanza.
     (Anche qui si ha poi una sola istanza da passare)
  * Nel main generare tutte le istanze necessarie e collegarle tra loro tramite i riferimenti indicati nei costruttori.
* Sul suddividere le classi "grandi", si dovranno valutare tutte le classi una per una.

I vantaggi ottenuti alla fine di tutte le migliorie saranno due:
* Nessun componente statico (o quanto meno il minimo necessario).
* Modello MVC puro (o quanto più puro possibile)

# Ricevimento ulteriore con il prof

## Quali singleton dovremmo eliminare?
__Prof:__ in lato teorico non si dovrebbe avere nulla di statico eccetto che costanti e funzioni pure. Quelli usati in un solo posto non servono a nulla. I singleton creano dipendenze implicite, in quanto non si vede dall'esterno della classe che questa usa il sigleton.
Le dipendenze implicite sono pericolose dato il possibile uso involontario di componenti e/o impossibilità di creare test automatici.
Tutto esplicito invece rende chiaro chi usa quel sistema e ne decido il modo di utilizzo. Inoltre, è semplice mascherare la dipendenza dietro un'interfaccia e quindi rendere facile sostituire tale dipendenza.
Pertanto volendo le cose fatte bene sono da eliminare tutti.

## I toString() nascosti dietro al Controller come sono visti?
__Prof:__ Sarebbe opportuno di no, pensate alla visualizzazione come HTML: la view si costruisce il codice dai getter dei dati, non il controller o il model.
In una visione MVC il controller restituisce alla view l'istanza dell'oggetto del model e la view ne costruisce la rappresentazione. Si potrebbe poi introdurre delle interfacce intermedie contenenti i getter da visualizzare nella view.
Un controller che costruisce in modo mirato alla view i dati da rappresentare _più che MVC è GRASP_.
Realizzare una classe Rappresentatore per costruire quanto da visualizzare dall'istanza di un oggetto del model è accettabile. Questa classe può stare nel controller e il controller sa qualcosa della view (logica MVC) oppure stare nell view in ambito di logica di visualizzazione.

## AccessoModel e AccessoController
__Prof:__ Ok, basta che non ritornino oggetti sui quali eseguo altre operazioni, bensì abbia metodi per effettuare ciò che deve. Perciò AccessoModel agisce sugli elenchi e non fornisce gli elenchi per la modifica.
Per il Controller invece conviene lasciare la logica divisa e non un'unica interfaccia che comprende tutti gli aspetti.
Inoltre istanze del controller divise possono essere passate all'occorrenza della view e non tutte sempre ovunque.

## Interfaccia per la view
__Prof:__ Nessuno dipende dalla view e perciò non serve.


# Decisioni dopo ricevimento

## Classi singleton e statiche
Rendere tutti i singleton e classi statiche ASSOLUTO.

Per il model --> AccessoModel che unifica l'istanza del model e agisce sugli elenchi.

Per il controller --> Lasciare le istanze scollegate e passarle dove servono (esempio: vedi collegamento istanze). Le classi OperazioniIniziali e OperatoriFinali possono diventare classi PopolamentoDati e SalvataggioDati con la responsabilità di caricare nel model tutti i dati salvati e salvare nei dati tutte le entità del model reciprocamente. Il metodo 'generaUnitaBase()' e 'controlloEsistenzaUnita()' sono demandati al model.
Rappresentatore è classe derivata da Recuperatore con la sola responsabilità di ricavare la descrizione da mostrare a video da una istanza passata. Interpretatore è simile all'attuale Modificatore, con la sola modifica che si appoggerà al nuovo Modificatore il quale avrà metodi che agiscono sul model con i dati indicati (istanza di entità per aggiunta, dati per modifiche, ...)

Classi io --> LettoriXML esplode in una classe contenenti le costanti e la HashMap lettori ora contenuta in LetturaDatiLocali. Tutto statico in quanto viste come 'funzioni pure'.
Per ciascuna entità si genera una classe composta da un'unica funzione per leggere l'entità.

Per la view --> tutte le classi di menù saranno da rendere istanziabili e ciascuna nel costruttore accetta Interpretatore, Rappresentatore e Verificatore (se utilizzato). Nel costruttore genera le istanze dei menu figli richiamati nell'esecuzione. Il metodo avvia resta inalterato.

## Spostamento dei package
Il package io dentro a model, risulta più familiare a controller e pertanto qui viene spostato.

Le classi ora implementate con pattern visitor da spostare in package 'visitatori' e sottopackage per 'xml' e 'console'

## Pattern da implementare
* _Visitor_ sulle entità per Rappresentatore e ScrittoriXML (Interfaccia Visitatore con metodi per entità con la relativa classe astratta VisitatoreAdapter ed interfaccia Visitabile<V>)
* _Observer_ puro per l'observer utilizzato del SommarioDispositivi --> Implementato già correttamente, con aggiunte legittimate dall'uso che ci serviva (per giustificare il non uso delle interfacce già incluse in Java).

## Collegamento istanze:
Di seguito quello che dovrebbe diventare il metodo main a seguito delle modifiche:

```
PercorsiFile.controllaStruttura();
PercorsiFile generatoreDati = new PercorsiFile(PercorsiFile.SORGENTE_DATI);
PercorsiFile generatoreLibreria = new PercorsiFile(PercorsiFile.SORGENTE_LIBRERIA);
PercorsiFile generatoreLibreriaImportata = new PercorsiFile(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);

LetturaDatiSalvati letturaDati = new LetturaDatiLocali(generatoreDati);
LetturaDatiSalvati letturaLibreria = new LetturaDatiLocali(generatoreLibreria);
ScritturaDatiSalvati scritturaDati = new ScritturaDatiLocali(generatoreDati);
RimozioneDatiLocali rimozioneDati = new RimozioneDatiLocali(generatoreDati);

ImportaDati importaDati = new ImportaDatiLocali(generatoreLibreria, letturaLibreria, generatoreLibreriaImportata);
//Da vedere per la storicizzazione

Model model = new AccessoModel();
Recuperatore recuperatore = new Recuperatore(model);
Verificatore verificatore = new Verificatore(recuperatore);
Modificatore modificatore = new Modificatore(model, recuperatore, verificatore);
Impotatore importatoreLocale = new Importatore(modificatore, importaDatiLocali);
Interpretatore interpretatore = new InterpretatoreConsole(modificatore);
Rappresentatore rappresentatore = new RappresentatoreConsole(recuperatore);

RinfrescaDati rinfrescaDati = new RinfrescaDatiLocali(letturaDati, recuperatore);
TimerRinfrescoDati timerRinfrescoDati = new TimerRinfrescoDati(rinfrescaDati);
TimerGestioneRegole timerGestioneRegole = new TimerGestioneRegole(recuperatore);
TimerAzioniProgrammate timerAzioniProgrammate = new TimerAzioniProgrammate(recuperatore);

MenuAzioniConflitto menuAzioniConflitto = new MenuAzioniConflitto();
MenuErroreApertura menuErroreApertura = new MenuErroreApertura();
MenuErroreChiusura menuErroreChiusura = new MenuErroreChiusura();
AperturaProgramma apertura = new PopolamentoDati(letturaDati, modificatore, menuErroreApertura, menuAzioniConflitto);
ChiusuraProgramma chiusura = new SalvataggioDati(scritturaDati, recuperatore, menuErroreChiusura);

MenuLogin menuLogin = new MenuLogin(interpretatore, rappresentatore, verificatore);

boolean esegui = apertura.apri();

if (esegui) {
    //Controllo se non sono presenti unita immobiliari e in tal caso aggiungo l'unita base generata.
    if (!recuperatore.controlloEsistenzaUnita())
        modificatore.aggiungiUnita(recuperatore.getUnitaBase());

    timerAzioniProgrammate.start(); //avvio timer gestione azioni programmate
    timerRinfrescoDati.start(); //avvio timer rinfresco dati
    timerGestioneRegole.start(); //avvio timer gestione regole
}

while (esegui) {

    menuLogin.avvia();

    esegui = !chiusura.chiudi();
}

timerAzioniProgrammate.stop();
timerRinfrescoDati.stop();
timerGestioneRegole.stop();

```

# Suddivisioni delle modifiche
Da intendere le modifiche come spiegate sopra.

## Andrea
- [ ] Trasformare classi del controller
- [x] Realizzare AccessoModel

## Edoardo
- [ ] Trasformare le classi della view
- [x] Interfacce pattern visitor e spostamento package io in controller

## Paolo
- [ ] Trasformazione package io e model
- [x] Trasformazione classi OperazioniIniziali e Operazioni finali (con spostamento metodi)

## Tutti
- [ ] Se si incontra un HashMap o ArrayList come parametro o tipo di ritorno si cambia in Map o List relativamente.
