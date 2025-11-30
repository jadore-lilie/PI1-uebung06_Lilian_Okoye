 // Importieren der VK_*-Tastenkonstanten
import static java.awt.event.KeyEvent.*;

/**
 * Dies ist die Hauptklasse eines Spiels. Sie enthält die Hauptmethode, die zum
 * Starten des Spiels aufgerufen werden muss.
 *
 * @author Thomas Röfer
 */
abstract class PI1Game extends Game
{
    /** Das Spiel beginnt durch Aufruf dieser Methode. */
    static void main()
    {
        new GameObject(0, 0, 0, "path-e");
        new GameObject(1, 0, 0, "path-i");
        new GameObject(2, 0, 1, "path-t");
        new GameObject(3, 0, 0, "path-i");
        new GameObject(3, 0, 0, "goal");
        new GameObject(4, 0, 0, "bridge");
        new GameObject(0, 1, 0, "path-e");
        new GameObject(1, 1, 0, "path-i");
        new GameObject(2, 1, 0, "path-x");
        new GameObject(3, 1, 2, "path-e");
        new GameObject(4, 1, 3, "water-l");
        new GameObject(0, 2, 0, "path-e");
        new GameObject(1, 2, 0, "path-i");
        new GameObject(2, 2, 0, "path-x");
        new GameObject(3, 2, 0, "path-i");
        new GameObject(4, 2, 2, "path-e");
        new GameObject(0, 3, 0, "path-e");
        new GameObject(1, 3, 0, "path-i");
        new GameObject(2, 3, 2, "path-l");
        new GameObject(3, 3, 0, "water-l");
        new GameObject(4, 3, 0, "water-i");
        
        
        final GameObject player = new GameObject(0, 3, 0, "woman");
        
        final Walker walker1 = new Walker(new GameObject(3, 2, 2, "child"), 4, 1 );
        final Walker walker2 = new Walker(new GameObject(0, 1, 0, "laila"), 3, 0);
        final Walker walker3 = new Walker(new GameObject(1, 0, 2, "claudius"), 3, 2);
         
        
        

        while (player.isVisible()) {
            final int key = getNextKey();
            if (key == VK_RIGHT) {
                player.setRotation(0);
                player.setLocation(player.getX() + 1, player.getY());
            }
            else if (key == VK_DOWN) {
                player.setRotation(1);
                player.setLocation(player.getX(), player.getY() + 1);
            }
            else if (key == VK_LEFT) {
                player.setRotation(2);
                player.setLocation(player.getX() - 1, player.getY());
            }
            else if (key == VK_UP) {
                player.setRotation(3);
                player.setLocation(player.getX(), player.getY() - 1);
            }
            else {
                playSound("error");
                continue;
            }

            playSound("step");
            sleep(200);
            walker1.act(player);
            walker2.act(player);
            walker3.act(player);
            
            walker1.Verfolgermodus(player);
            walker2.Verfolgermodus(player);
            walker3.Verfolgermodus(player);
        }
    }
}
