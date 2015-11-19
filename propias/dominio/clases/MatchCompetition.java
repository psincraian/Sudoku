package propias.dominio.clases;

import java.time.Duration;
import java.time.Instant;

/** author: Petru Rares */
public class MatchCompetition extends Match {

	private Instant startTime;
	private Instant endTime;
	
	public MatchCompetition(String username, Sudoku sudoku) {
		super(username, sudoku);
	}
	
	public void startTime() {
		startTime = Instant.now();
	}
	
	public void endTime() {
		endTime = Instant.now();
	}
	
	public Duration getMatchTime() {
		endTime = Instant.now();
		return Duration.between(startTime, endTime);
	}
}
