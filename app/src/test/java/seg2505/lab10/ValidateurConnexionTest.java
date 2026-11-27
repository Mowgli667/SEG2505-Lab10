package seg2505.lab10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests unitaires locaux exécutés automatiquement par le pipeline CircleCI
 * (job « tests-unitaires », tâche Gradle testDebugUnitTest) à chaque push.
 */
public class ValidateurConnexionTest {

    // ----- Champs obligatoires -----

    @Test
    public void champVide_nEstPasRempli() {
        assertFalse(ValidateurConnexion.estRempli(""));
        assertFalse(ValidateurConnexion.estRempli("   "));
        assertFalse(ValidateurConnexion.estRempli(null));
    }

    @Test
    public void champAvecTexte_estRempli() {
        assertTrue(ValidateurConnexion.estRempli("Manu"));
    }

    // ----- Courriel -----

    @Test
    public void courrielBienForme_estValide() {
        assertTrue(ValidateurConnexion.estCourrielValide("eents096@uottawa.ca"));
        assertTrue(ValidateurConnexion.estCourrielValide("prenom.nom+seg@exemple.com"));
    }

    @Test
    public void courrielSansDomaine_estInvalide() {
        assertFalse(ValidateurConnexion.estCourrielValide("email@"));
    }

    @Test
    public void courrielSansArobase_estInvalide() {
        assertFalse(ValidateurConnexion.estCourrielValide("uottawa.ca"));
    }

    @Test
    public void courrielSansPointDansLeDomaine_estInvalide() {
        assertFalse(ValidateurConnexion.estCourrielValide("etudiant@uottawa"));
    }

    // ----- Mot de passe -----

    @Test
    public void motDePasseTropCourt_estInvalide() {
        assertFalse(ValidateurConnexion.estMotDePasseValide("abc1"));
    }

    @Test
    public void motDePasseSansChiffre_estInvalide() {
        assertFalse(ValidateurConnexion.estMotDePasseValide("motdepasse"));
    }

    @Test
    public void motDePasseSansLettre_estInvalide() {
        assertFalse(ValidateurConnexion.estMotDePasseValide("12345678"));
    }

    @Test
    public void motDePasseAvecLettresEtChiffres_estValide() {
        assertTrue(ValidateurConnexion.estMotDePasseValide("seg2505Lab10"));
    }

    // ----- Validation complète (ordre des vérifications) -----

    @Test
    public void formulaireComplet_renvoieSucces() {
        assertEquals(ValidateurConnexion.OK,
                ValidateurConnexion.valider("Manu", "Entseya", "eents096@uottawa.ca", "seg2505Lab10"));
    }

    @Test
    public void prenomVide_renvoieErreurPrenom() {
        assertEquals(ValidateurConnexion.PRENOM_INVALIDE,
                ValidateurConnexion.valider("", "Entseya", "eents096@uottawa.ca", "seg2505Lab10"));
    }

    @Test
    public void nomVide_renvoieErreurNom() {
        assertEquals(ValidateurConnexion.NOM_INVALIDE,
                ValidateurConnexion.valider("Manu", "", "eents096@uottawa.ca", "seg2505Lab10"));
    }

    @Test
    public void courrielInvalide_renvoieErreurCourriel() {
        assertEquals(ValidateurConnexion.COURRIEL_INVALIDE,
                ValidateurConnexion.valider("Manu", "Entseya", "email@", "seg2505Lab10"));
    }

    @Test
    public void motDePasseInvalide_renvoieErreurMotDePasse() {
        assertEquals(ValidateurConnexion.MOT_DE_PASSE_INVALIDE,
                ValidateurConnexion.valider("Manu", "Entseya", "eents096@uottawa.ca", "abc"));
    }

    @Test
    public void plusieursChampsInvalides_seulLePremierEstSignale() {
        // Comme dans le Tutoriel 8 : les champs sont vérifiés dans l'ordre
        assertEquals(ValidateurConnexion.PRENOM_INVALIDE,
                ValidateurConnexion.valider("", "", "email@", "abc"));
    }
}
