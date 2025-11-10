package vue;
/**
 * Cadre simple, plein ?cran qui contient un panneau dessin.
 *
 * Int?gre la logique de confirmation de fermeture de fen?tre
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CadrePrincipal extends JFrame implements Runnable{

    PanneauPrincipal panneauPrincipal;
    Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void run() {

        initCadre(); //Configure la fenêtre
        initPanneau(); //Installe le panneau principal

        setVisible(true);
    }


    private void initCadre() {

        // maximize la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        // Demande une confirmation avant de fermer
        this.addWindowListener(new WindowAdapter() {

            // gestionnaire d'événement pour la fermeture
            public void windowClosing(WindowEvent we) {

                // ajoute une demande de confirmation
                int result = JOptionPane.showConfirmDialog(null,
                        "Voulez-vous quitter ?", "Confirmation de sortie: ",
                        JOptionPane.YES_NO_OPTION);

                // Si la réponse est OUI
                if (result == JOptionPane.YES_OPTION) {

                    //Ferme la fenêtre en activant la gestion de l'événement
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else if (result == JOptionPane.NO_OPTION) {

                    // Désactive la gestion de l'événement
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private void initPanneau() {

        // Instancier le panneau principal
        panneauPrincipal = new PanneauPrincipal(tailleEcran);

        //Remplace le JPanel existant dans le JFrame par notre classe définie
        this.setContentPane(panneauPrincipal);
    }

}
