package common;

/* Classe Usuari per compartir
 * Cluster: 41
 * Equip: Kenken
 */
public class UsuariCompartit {
        //ATRIBUTS
        //nom de l'usuari instanciat
        private String user;
        //CONSTRUCTORES
        public UsuariCompartit(String usr) {
                //constructora a partir d'un nom d'usuari
                this.user = usr;
        }
        public UsuariCompartit() {
                //constructora d'un usuari buit
                this.user = null;
        }
        //METODES
        public String getUser() {
                //retorna el nom de l'usuari instanciat
                return this.user;
        }
       
        public void setUser(String user) {
                //registra el nom d'usuari a la instancia
                this.user = user;
        }
}