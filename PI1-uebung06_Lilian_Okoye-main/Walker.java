/**
 * Die Klasse Walker steuert das Verhalten eines NPC, der zunächst normal
 * eine Strecke abläuft, sich aber bei Sichtkontakt mit dem Spieler in einen
 * Verfolgermodus schaltet und dessen Bewegungen zeitverzögert nachgeht.
 * 
 *
 * @author Thomas Röfer
 */
class Walker
{
    private final GameObject avatar;
    private final int maxSteps;
    private int stepsSoFar;
    private final RingBuffer buffer; // Der tatsächliche RingBuffer 
    private boolean verfolgen = false; // Gibt an, ob der NPC sich im Verfolgermodus befindet
    private int sichtweite = 2; // Maximale Entfernung, in der der NPC den Spieler erkennen kann

     /**
     * Konstruktor für einen Walker.
     *
     * @param avatar      Das NPC-Objekt
     * @param maxSteps    Maximale Anzahl Schritte, bevor der NPC umdreht
     * @param stepsSoFar  Bereits gemachte Schritte zu Beginn
     */
    Walker(final GameObject avatar, final int maxSteps, final int stepsSoFar)
    { 
        this.avatar = avatar; 
        this.maxSteps = maxSteps;
        this.stepsSoFar = stepsSoFar;
        this.buffer = new RingBuffer(3); // RingBuffer mit eine Kapazität von 3 (Verzörgerung)
    }
    /**
     * Dies ist die Hauptmethode des NPC, die einmal pro Spieltick aufgerufen wird.
     * Sie steuert:
     *  - Normalbewegung
     *  - Umschalten in den Verfolgermodus
     *  - Bewegung im Verfolgermodus
     */
    void act(final GameObject player)
    {
        // Zuerst prüfen, ob der NPC den Spieler sieht
        Verfolgermodus(player);
        // VERFOLGERMODUS AN
        if (verfolgen){
            // Sobald der Buffer voll genug ist, kann der NPC dem Spieler folgen
            if (buffer.size() > buffer.capacity()){
                int nextRotation = buffer.pop(); // Älteste gespeicherte Rotation holen
                avatar.setRotation(nextRotation); // NPC dreht in diese Richtung
            }
            Walking(); 
            // Wenn gleiche Position wie Spielfigur, lasse diese verschwinden
            if (avatar.getX() == player.getX() && avatar.getY() == player.getY()) {
                    player.setVisible(false);
                    avatar.playSound("go-away");
                }
            return; 
            }
        
        // NORMALMODUS 
        // Vorwärs bewegen
        Walking(); 
        
        // Sound dazu abspielen
        avatar.playSound("step");

        // Weiterzählen
        stepsSoFar = stepsSoFar + 1;

        // Wenn maximale Anzahl erreicht, umdrehen und Zählung neu beginnen
        if (stepsSoFar == maxSteps) {
            avatar.setRotation(avatar.getRotation() + 2);
            stepsSoFar = 0;
        }
        // Wenn gleiche Position wie Spielfigur, lasse diese verschwinden
        if (avatar.getX() == player.getX() && avatar.getY() == player.getY()) {
            player.setVisible(false);
            avatar.playSound("go-away");
        }
    }
    
    /**
     * Führt eine Bewegung um genau ein Feld in Blickrichtung des NPC aus.
     * Rotation: 0 = rechts, 1 = unten, 2 = links, 3 = oben
     */
    void Walking() {
        // Vorwärs bewegen
        if (avatar.getRotation() == 0) {
            avatar.setLocation(avatar.getX() + 1, avatar.getY());
        }
        else if (avatar.getRotation() == 1) {
            avatar.setLocation(avatar.getX(), avatar.getY() + 1);
        }
        else if (avatar.getRotation() == 2) {
            avatar.setLocation(avatar.getX() - 1, avatar.getY());
        }
        else {
            avatar.setLocation(avatar.getX(), avatar.getY() - 1);
        }
    }
    
    /**
     * Prüft, ob die Spielfigur in Sichtweite des NPC liegt und aktiviert gegebenenfalls
     * den Verfolgermodus. Befindet sich der Spieler im Sichtfeld, wird die aktuelle
     * Rotation des Spielers in den Ringpuffer eingetragen, damit der NPC diese später
     * nachahmen kann.
     */
    boolean Verfolgermodus(final GameObject player){
        // Positionen von Spieler (pX/pY) und NPC (aX/aY)
        int pX = player.getX(); 
        int pY = player.getY(); 
        int aX = avatar.getX();
        int aY = avatar.getY(); 
        
        // Blickrichtung des NPC 
        int rot = avatar.getRotation(); 
        
        boolean siehtSpieler = false;
        
        // Sichtprüfung abhängig von Blickrichtung
        if (rot == 0){ // Blick nach rechts
            if (pY == aY && pX > aX && pX <= aX + sichtweite){
                siehtSpieler = true; 
            }
        }
        else if (rot == 1){ // Blick nach unten 
            if (pX == aX && pY > aY && pY <= aY + sichtweite){
                siehtSpieler = true; 
            }
        }
        else if (rot == 2){ // Blick nach links 
            if (pY == aY && pX < aX && pX >= aX - sichtweite){
                siehtSpieler = true; 
            }
        }
        else {// rot == 3, Blick nach oben
             if (pX == aX && pY < aY && pY >= aY - sichtweite){
                siehtSpieler = true; 
            }
        }
        // Wird der Spieler gesehen, schaltet der NPC in den Verfolgermodus.
        if (siehtSpieler){
            verfolgen = true; 
        }
        // Solange der NPC verfolgt, wird die aktuelle Spielerrotation
        // in den Ringpuffer eingetragen, um später nachgelaufen zu werden.
        if (verfolgen) {
                buffer.push(rotationZumSpieler(aX,  aY,  pX, pY));
                return true; 
            }
        return true; 
    }
    /**
     * Berechnet eine Rotation, die den NPC in Richtung des Spielers drehen lässt.
     * PRIORITÄT: zuerst horizontal, dann vertikal.
     *
     * @return 0 = rechts, 1 = unten, 2 = links, 3 = oben
     */
    int rotationZumSpieler(int aX, int aY, int pX, int pY) {
        if (pX > aX){
        return 0;// Spieler rechts → nach rechts drehen
        }
        else if (pY > aY) {
        return 1;  // Spieler unten → nach unten drehen
        }
        else if (pX < aX) {
        return 2;  // Spieler links → nach links drehen
        }
        else if (pY < aY) {
        return 3;               // Spieler oben → nach oben drehen
        } else {return 0;}
    }
}