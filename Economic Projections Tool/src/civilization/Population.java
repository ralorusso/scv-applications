package civilization;

import core.Colonizable;

public class Population {

	private Species species;
	private Nation nation;
	
	public boolean matchesHabitability(Colonizable world) {
		return this.species.matchesHabitability(world);
	}
	
}
