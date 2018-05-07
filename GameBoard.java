
/**
 * Beschreiben Sie hier die Klasse GameBoard.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GameBoard   {
    
    private int[][] field;
    
    private int placed = 1;
    
    public GameBoard() {
      field = new int[10][10];
    }
    
    public boolean placeShip(int x, int y, boolean tilted, int length) {
        for (int i = 0; i < length; i++) {        //Teile des neu zu plazierenden Schiffes nacheinander durchgehen
                                            //prüfen ob neues Schiffteil außerhalb des Spielfeldes
            if (x >= field.length           //zu weit rechts
            || x < 0                        //zu weit links
            || y >= field[x].length         //zu weit unten
            || y < 0) {                     //zu weit oben
                
                for (int h = 0; h < field.length; h++) {
                    for (int j = 0;j < field.length;j++) {
                        field[h][j] = field[h][j] == placed ? 0 : field[h][j];         //ganzes Feld durchgehen und schon halb plaziertes Schiff löschen
                    }
                }
                return false;
            }
            try {
            
            if ((field[x][y] != 0 && field[x][y] != placed) ){      //wenn schon ein anderes Schiff an der Stelle des neuen Schiffteils plaziert ist: 
                
                for (int h = 0; h < field.length; h++) {
                    for (int j = 0;j < field.length;j++) {
                        field[h][j] = field[h][j] == placed ? 0 : field[h][j];         //ganzes Feld durchgehen und schon halb plaziertes Schiff löschen
                    }
                }    
                return false;                                   //false zurückgeben
            }

            }
            catch (Exception e) {}          //Fehlerbehandlung
            
            field[x][y] = placed;           //Schiffteil platzieren (mit Nummer des Schiffes)
            x = tilted ? x : x+1;           //wenn tilted true (senkrechtes Schiff), dann bleibt der x-Wert, sonst wird dieser erhöht(waagerechtes Schiff) (für weitere Schiffteile) 
            y = tilted ? y+1 : y;           //wenn tilted true (senkrechtes Schiff), dann wird der y-Wert erhöht, sonst bleibt er gleich(waagerechtes Schiff) (für weitere Schiffteile)
        }                                   //tilted true: senkrechtes Schiff
                                            //alle Teile des neuen Schiffes gesetzt
        
        placed++;                           //Anzahl der schon gesetzten Schiffe erhöhen
        return true;
    }
    
    public boolean placeShip(int x, int y, boolean tilt) {           //neues Schiff platzieren, ohne dessen Länge festzulegen
        int length = 0;
        if (placed == 1) {                                          //erstes Schiff: 5 lang
            length = 5;
        } else if (placed == 2 || placed == 3) {                    //zweites und drittes Schiff: 4 lang
            length = 4;
        } else if (placed == 4 || placed == 5 || placed == 6) {     //viertes, fünftes und sechstes Schiff: 3 lang
            length = 3;
        } else if (placed == 7 || placed == 8) {                    //siebtes und achtes Schiff: 2 lang
            length = 2;
        }
        return placeShip(x, y,tilt, length);                        //Schiff mit entsprechender Länge platzieren
    }
    
    
    public int shoot(int x, int y) {            //ausgewähltes Feld = Feld das beschossen wird
        if (field[x][y] == 0) {                 //Wenn ausgewähltes Feld Wasser (=0)
            field[x][y] = -10;                  //auf -10 setzen (=schon beschossenes Wasser)
            return 0;                           //Rückgabe für "daneben/Wasser"
        }
        if (field[x][y] < 0) {                  //Wenn ausgewähltes Feld schon beschossen (Wasser oder kaputtes Schiff)
            return -1;                          //Fehler ausgeben
        }
        if (field[x][y] > 0) {                  //Wenn ausgewähltes Feld von Schiff besetzt (>0)
            field[x][y] = -field[x][y];         //Auf den negativen Wert des Schiffes setzen (z.B. für zweites Schiff(2) auf -2 setzen)
            
            int versenkt = 0;
            
            for (int h = 0; h < field.length; h++) {                    //ganzes Feld durchgehen
                    for (int j = 0; j < field.length; j++) {
                        versenkt = field[h][j] == (-field[x][y]) ? versenkt+1 : versenkt;
                      //wenn das Feld des Schiffes noch positiv: versenkt wird erhöht -> nicht versenkt
                      //wenn das Feld des Schiffes negativ, bleibt versenkt auf seinem Wert
                }
            }
            if (versenkt == 0) {            //wenn alle Schiffteile negativ: Schiff ist versenkt
                return 2;                   //Rückgabe für "versenkt"
            }
            
        }
        
        return 1;                           //Rückgabe für "getroffen"
    }
    
    public void outputBoard() {             //Ausgabe des Spielfeldes zum Testen
        for (int h = 0;h < field.length;h++) {
                    for (int j = 0;j < field.length;j++) {
                        System.out.print("|" + field[h][j]);
            }
            System.out.println("|");
        }
        System.out.println("");
    }
}
