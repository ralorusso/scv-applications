package generator;

public enum YGT {

	YGT_P_TP_INNER(DR.d4,-1,DR.d6,DR.d0,-2,DR.d6,-2),
	YGT_P_GG_INNER(DR.d3,-1,DR.d3,DR.d0,-1,DR.d3,-1),
	YGT_P_AB_INNER(DR.d0,-5,DR.d8,DR.d8,0,DR.d0,0),
	
	YGT_P_TP_OUTER(DR.d4,-2,DR.d6,DR.d0,-2,DR.d6,-2),
	YGT_P_GG_OUTER(DR.d3,-1,DR.d3,DR.d0,-1,DR.d3,-1),
	YGT_P_AB_OUTER(DR.d0,-5,DR.d8,DR.d8,0,DR.d0,0),
	
	YGT_L_MN_TERRE(DR.d3,-1,DR.d4,DR.d0,-2,DR.d4,-2),
	YGT_L_R1_TERRE(DR.d0,-5,DR.d4,DR.d0,1,DR.d4,-2),
	YGT_L_AR_TERRE(DR.d0,-5,DR.d4,DR.d0,-2,DR.d4,1),
	
	YGT_L_MN_GIANT(DR.d4,-2,DR.d6,DR.d0,-2,DR.d6,-2),
	YGT_L_R1_GIANT(DR.d0,-5,DR.d4,DR.d0,1,DR.d4,-2),
	YGT_L_R2_GIANT(DR.d0,-5,DR.d4,DR.d0,1,DR.d4,-1),
	YGT_L_AR_GIANT(DR.d0,-5,DR.d4,DR.d0,-2,DR.d4,1);
	
	private final DR foodDie;
	private final int foodMod;
	private final DR indDie;
	private final DR indDie2;
	private final int indMod;
	private final DR sciDie;
	private final int sciMod;
	
	YGT(DR foodDie, int foodMod, DR indDie, DR indDie2, int indMod, DR sciDie, int sciMod) {
        this.foodDie = foodDie;
        this.foodMod = foodMod;
        this.indDie = indDie;
        this.indDie2 = indDie2;
        this.indMod = indMod;
        this.sciDie = sciDie;
        this.sciMod = sciMod;
    }
	
	public int getFood() {
		return (foodDie.rollDie() + foodMod);
	}
	public int getInd() {
		return (indDie.rollDie() + indDie2.rollDie() + indMod);
	}
	public int getSci() {
		return (sciDie.rollDie() + sciMod);
	}
	
}
