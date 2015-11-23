package propias.dominio.clases;

import java.time.Duration;
import java.time.Instant;

/** La classe MatchComptetition es una sublasse de {@link Match}. A part de totes
 * les caracteristiques de {@link Match} també proporciona el temps que s'ha estat
 * l'usuari jugant la partida.
 * 
 * @author petrusqui
 *
 */
public class MatchCompetition extends Match {

	private Instant startTime;
	private Instant endTime;
	
	/** La cosntructora per defecte. Necessita un nom d'usuari i un sudoku
	 * 
	 * @param username El nom del usuari
	 * @param sudoku El sudoku del usuari
	 */
	public MatchCompetition(String username, Sudoku sudoku) {
		super(username, sudoku);
	}
	
	/** Comença a comptar el temps.
	 * 
	 */
	public void startTime() {
		startTime = Instant.now();
	}
	
	/** Para el temps
	 * 
	 */
	public void endTime() {
		endTime = Instant.now();
	}
	
	/** Obte la duració de la partida. Abans d'obtenir la duració de la partida
	 * primer s'ha de cridar al metode {@link MatchCompetition#startTime()} i
	 * després al mètode {@link MatchCompetition#endTime()}
	 * 
	 * @return Retorna la duració de la partida.
	 */
	public Duration getMatchTime() {
		endTime = Instant.now();
		return Duration.between(startTime, endTime);
	}
}
