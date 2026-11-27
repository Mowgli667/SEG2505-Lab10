package seg2505.lab10;

/**
 * Logique de validation du formulaire de connexion, séparée de l'activité
 * pour pouvoir être testée par des tests unitaires locaux (JVM), donc
 * exécutables par le pipeline CircleCI sans émulateur.
 */
public final class ValidateurConnexion {

    public static final String OK = "Login Successful";
    public static final String PRENOM_INVALIDE = "Invalid First Name";
    public static final String NOM_INVALIDE = "Invalid Last Name";
    public static final String COURRIEL_INVALIDE = "Invalid Email";
    public static final String MOT_DE_PASSE_INVALIDE = "Invalid Password";

    private ValidateurConnexion() {
    }

    /** Vrai si le texte n'est ni null ni composé uniquement d'espaces. */
    public static boolean estRempli(String texte) {
        return texte != null && !texte.trim().isEmpty();
    }

    /** Vérifie la forme d'un courriel : une partie locale, un @, un domaine avec un point. */
    public static boolean estCourrielValide(String courriel) {
        if (!estRempli(courriel)) {
            return false;
        }
        return courriel.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[A-Za-z]{2,}$");
    }

    /** Un mot de passe valide a au moins 8 caractères, dont une lettre et un chiffre. */
    public static boolean estMotDePasseValide(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 8) {
            return false;
        }
        return motDePasse.matches(".*[A-Za-z].*") && motDePasse.matches(".*\\d.*");
    }

    /**
     * Vérifie les champs dans l'ordre et renvoie le message du premier champ invalide,
     * ou OK si tout est valide.
     */
    public static String valider(String prenom, String nom, String courriel, String motDePasse) {
        if (!estRempli(prenom)) {
            return PRENOM_INVALIDE;
        }
        if (!estRempli(nom)) {
            return NOM_INVALIDE;
        }
        if (!estCourrielValide(courriel)) {
            return COURRIEL_INVALIDE;
        }
        if (!estMotDePasseValide(motDePasse)) {
            return MOT_DE_PASSE_INVALIDE;
        }
        return OK;
    }
}
