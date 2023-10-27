package galaxygenerator;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import galaxygenerator.Star;
import io.NameList;

public class GalaxyGenerator {

	//seed variables
	private static int seed = 0;
	private static final int INIT_STAR = 0; //1 for preset; 0 otherwise
	
	//stellar variables
	public static final float SCALE_CONSTANT = 1.2f; //changes size of galaxy (1.2 default)
	public static final int STAR_NUM = 300; //changes stellar density (default 300)
	public static final float ELIM_R2 = 576.0f; //changes radius of elimination (this is the SQUARE of ROE, default 24^2)
												//ELIM_RD MUST BE SET TO 0 FOR PRESET IF STAR REMOVAL FALSE
	public static final int BARRIER = 70; //dist from edges (default 70)
	public static final int INNERBARRIER = 25; //dist from center (default 25)
	
	//region variables
	public static final int HA_STAR_ODDS = 10; //i.e., 1 in HA_STAR_ODDS //default 10 for 1.5 R_HA, 1000 for 0.9 R_HA
	public static final float RADIUS_HA = 1.5f;				//default 0.9 OR 1.5
	public static final float RADIUS_NAT_HA = 0.8f;			//NOT TO BE PLACED ABOVE 1 //default 0.8
	public static final float RADIUS_NEBULA_C = 1.8f;		//default 1.8
	public static final float RADIUS_NEBULA_SUB = 0.9f;  	//NOT TO BE PLACED ABOVE 1 //default 0.9
	public static final float RADIUS_RD_C = 2.1f;			//default 2.1
	public static final float RADIUS_RD_SUB = 0.7f;			//NOT TO BE PLACED ABOVE 1 //default 0.7
	
	//galaxy variables
	public static final int GALAXY_SIZE = (int) (250.0f*3.0f*SCALE_CONSTANT);
	//public static final GalaxyType GALAXY_TYPE = GalaxyType.SPIRAL;
	
	//spiral variables
	public static final int NUM_OF_ARMS = 3; //changes number of spiral arms
	public static final float ROT_CONSTANT = 1.4f / ((float) (Math.sqrt((double) NUM_OF_ARMS))); //changes length of spiral arms
	
	//ring variables
	public static final float INNER_PERC = 0.7f;	//default 0.8
	public static final float INNER_VAR = 0.1f;        	//default 0.1	 //THIS PLUS INNER PERC NOT TO EXCEED 1
	
	//random variables
	public static final float RAND_LIMIT = 1.2f;	//default 1.2f, no effect beyond 1.415f
	
	//irregular variables
	public static final float REGION_CONSTANT = 48.0f; //default 48.0f, diminishing effects as increase
	public static final int REGION_COUNT = (int) (SCALE_CONSTANT*SCALE_CONSTANT*REGION_CONSTANT);
	public static final float MAX_RADIUS = 0.4f;	//default 0.4f, diminishing effects beyond 1.415f
	
	//irregular NH variables
	public static final float IRREGULARNH_SCALE_CON = 0.9f; //default 0.8f
	public static final float SPEC_ELIM_R2 = 196.0f; //default 144.0f
	public static final float IRR_COEFF = 10.0f; //default 1.0f to 10.0f
	
	//preset
	public static final boolean GALACTIC_NORTH_UP = true;
	public static final float ZOOM = 1.0f;
	public static final float RAD_SCALE_PRESET = ZOOM*SCALE_CONSTANT/1.2f;
	public static final boolean RAD_LOG = true;
	public static final float RAD_LOG_SCALE = 60.0f;
	public static final boolean STAR_REMOVAL = true;
	
	//generator variables
	public static final float STARS_PER_HA = 25.0f;	//default 25.0f
	public static final float STARS_PER_NEBULA = 36.0f;	//default 36.0f
	public static final float STARS_PER_HRD = 50.0f;	//default 50.0f
	public static final float STARS_PER_LRD = 50.0f;	//default 50.0f
	
	public static final float CULTURE_RATIO = 0.04f; // default 0.04f
	public static final int CULTURE_NUM = (int) ((STAR_NUM * Math.pow((((float) GALAXY_SIZE)/1000.0f),2)) * CULTURE_RATIO);		
	
	public static int STAR_NAME_COUNT = 0;
	public static int index = 0;
	public static int index2 = 0;
	
	private static Random generator;
	
	public static Galaxy generateGalaxy(int seedInt, GalaxyType GALAXY_TYPE) {
		seed = seedInt;
		generator = new Random(seed);
		
		float rotConstant = (float) (generator.nextFloat()*2*Math.PI);
		
		int starCount = (int) (STAR_NUM * Math.pow((((float) GALAXY_SIZE)/1000.0f),2));
		
		//initial counts
		int haCount = starCount/40;
		int nebulaCount = starCount/60;
		int hrdCount = starCount/80;
		int lrdCount = starCount/80;
		
		LinkedList<Star> starAr = new LinkedList<Star>();
		LinkedList<RegionHA> haAr = new LinkedList<RegionHA>();
		LinkedList<RegionNebula> nebAr = new LinkedList<RegionNebula>();
		LinkedList<RegionHighRD> hrdAr = new LinkedList<RegionHighRD>();
		LinkedList<RegionLowRD> lrdAr = new LinkedList<RegionLowRD>();
		LinkedList<Region> regionAr = new LinkedList<Region>();
		
		int galSpanConstant = (GALAXY_SIZE-(int)(BARRIER+INNERBARRIER*SCALE_CONSTANT)*2)/2;
		
		try (FileWriter writer = new FileWriter("randomlist.txt")) {
			List<String> nameAr = NameList.getNames("randomlist.txt");
			STAR_NAME_COUNT = nameAr.size();
			for(int i = 0; i < STAR_NAME_COUNT; i++) {
				String name = nameAr.get(generator.nextInt(STAR_NAME_COUNT-i));
				nameAr.remove(name);
				writer.write(name);
				writer.write(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//initialize star
		Star starInitial = new Star(0,0,0,GALAXY_SIZE,true);
		starAr.add(starInitial);
		starAr.get(0).setType(INIT_STAR);
		index++;
		
		System.out.println("Attempted Stars: " + (int) (starCount*GALAXY_TYPE.getCoeff()));
		
		//galaxy generation by type
		if (GALAXY_TYPE == GalaxyType.RING) {
			
			//star generation
			for (int i = 1; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				float variability = generator.nextFloat()*(2*INNER_VAR)-INNER_VAR;
				float x = (float) generator.nextInt(galSpanConstant)*(1-(INNER_PERC+variability)) 
						+galSpanConstant*(INNER_PERC+variability)
						+INNERBARRIER*SCALE_CONSTANT;
				float y = (float) (generator.nextFloat()*2*Math.PI);
				
				Star star = new Star(i,x,y,GALAXY_SIZE,true);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					starAr.add(star);
					index++;
				}
			}
			
		}
		else if (GALAXY_TYPE == GalaxyType.ELLIPTICAL) {
			
			//star generation
			for (int i = 1; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				float x = (float) generator.nextInt(galSpanConstant)
						+INNERBARRIER*SCALE_CONSTANT;
				float y = (float) (generator.nextFloat()*2*Math.PI);
				
				Star star = new Star(i,x,y,GALAXY_SIZE,true);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					starAr.add(star);
					index++;
				}
			}
			
		}
		else if (GALAXY_TYPE == GalaxyType.SPIRAL) {
			
			//star generation
			for (int i = 1; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				float x = 0;
				float y = 0;
				if(generator.nextInt(6) != 0) {
					float theta = (float) (generator.nextFloat()*2*Math.PI);
					float arm = (float) generator.nextInt(NUM_OF_ARMS);
					float armAngle = (float) ((float) arm*(2*Math.PI)/NUM_OF_ARMS);
					
					x = (float) ((float) theta*(galSpanConstant)/(2*Math.PI)
							+INNERBARRIER*SCALE_CONSTANT);
					y = theta*ROT_CONSTANT + rotConstant + armAngle 
							+ (generator.nextFloat()) * ROT_CONSTANT
							* ((float) x/galSpanConstant);
				}
				else {
					x = (float) generator.nextInt(galSpanConstant)
							+INNERBARRIER*SCALE_CONSTANT;
					y = (float) (generator.nextFloat()*2*Math.PI);
				}
				
				Star star = new Star(i,x,y,GALAXY_SIZE,true);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					starAr.add(star);
					index++;
				}
			}
			
		}
		else if (GALAXY_TYPE == GalaxyType.RANDOM || GALAXY_TYPE == GalaxyType.RANDOM_FULL) {
			
			//star generation
			for (int i = 1; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				float x = (float) generator.nextInt(galSpanConstant*2)-galSpanConstant;
				float y = (float) generator.nextInt(galSpanConstant*2)-galSpanConstant;
				
				Star star = new Star(i,x,y,GALAXY_SIZE,false);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					if (distance2(star,starInitial) < RAND_LIMIT*Math.pow(galSpanConstant, 2) &&
							distance2(star,starInitial) > INNERBARRIER*SCALE_CONSTANT) {
						starAr.add(star);
						index++;
					}
				}
			}
			
		}
		else if (GALAXY_TYPE == GalaxyType.IRREGULAR_NOHYPER) {
	
			Region rInitial = new Region(-1,GALAXY_SIZE/2,GALAXY_SIZE/2,GALAXY_SIZE,false);
			rInitial.setRadius(MAX_RADIUS*galSpanConstant);
			regionAr.add(rInitial);
			index++;
			//irregular region generation
			for (int i = 1; i < REGION_COUNT; i++) {
				float x = (float) generator.nextInt(GALAXY_SIZE);
				float y = (float) generator.nextInt(GALAXY_SIZE);
				
				Region region = new Region(-1*i-1,x,y,GALAXY_SIZE,false);
				region.setRadius(generator.nextFloat()*generator.nextFloat()*MAX_RADIUS*galSpanConstant);
				regionAr.add(region);
				
				int starIndex = starAr.size();
				
				for (int j = 0; j < (int) ((starCount*GALAXY_TYPE.getCoeff()*IRR_COEFF*generator.nextFloat()/REGION_COUNT)+1); j++) {
					index2++;
					
					float rj = generator.nextInt(((int) region.getRadius())+1);
					float thetaj = (float) (generator.nextFloat()*Math.PI*2);	
					
					float xj = (float) (rj*Math.cos(thetaj));
					float yj = (float) (rj*Math.sin(thetaj));
					
					Star star = new Star(starIndex+j,xj+x-GALAXY_SIZE/2,yj+y-GALAXY_SIZE/2,GALAXY_SIZE,false);
					float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
					if (delta > SPEC_ELIM_R2) {
						if (distance2(star,starInitial) > INNERBARRIER*SCALE_CONSTANT &&
								distance2(star,starInitial) < IRREGULARNH_SCALE_CON*Math.pow(galSpanConstant, 2)) {
							starAr.add(star);
							index++;
						}
					}
				}
				
			}
			
			//star generation
			int starIndex = starAr.size();
			
			
			for (int i = 0; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				index2++;
				float x = (float) generator.nextInt(galSpanConstant*2)*IRREGULARNH_SCALE_CON-galSpanConstant*IRREGULARNH_SCALE_CON;
				float y = (float) (generator.nextFloat()*Math.PI*2);	
				
				Star star = new Star(i+starIndex,x,y,GALAXY_SIZE,true);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > SPEC_ELIM_R2) {
					boolean inIrregular = false;
					
					for(Region j : regionAr) {
						if (j.inRegion(star)) inIrregular = true;
					}
					
					if (inIrregular &&
							distance2(star,starInitial) > INNERBARRIER*SCALE_CONSTANT) {
						starAr.add(star);
						index++;
					}
				}
			}
			
			System.out.println("Full Attempted Stars: " + index2);
			
		}
		else if (GALAXY_TYPE == GalaxyType.IRREGULAR) {
			
			Region rInitial = new Region(-1,GALAXY_SIZE/2,GALAXY_SIZE/2,GALAXY_SIZE,false);
			rInitial.setRadius(MAX_RADIUS*galSpanConstant);
			regionAr.add(rInitial);
			index++;
			//irregular region generation
			for (int i = 1; i < REGION_COUNT; i++) {
				float x = (float) generator.nextInt(GALAXY_SIZE);
				float y = (float) generator.nextInt(GALAXY_SIZE);
				
				Region region = new Region(-1*i-1,x,y,GALAXY_SIZE,false);
				region.setRadius(generator.nextFloat()*generator.nextFloat()*MAX_RADIUS*galSpanConstant);
				regionAr.add(region);
			}
			
			//star generation
			for (int i = 1; i < (int) starCount*GALAXY_TYPE.getCoeff(); i++) {
				float x = (float) generator.nextInt(galSpanConstant*2)-galSpanConstant;
				float y = (float) generator.nextInt(galSpanConstant*2)-galSpanConstant;
				
				Star star = new Star(i,x,y,GALAXY_SIZE,false);
				float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					boolean inIrregular = false;
					
					for(Region j : regionAr) {
						if (j.inRegion(star)) inIrregular = true;
					}
					
					if (inIrregular &&
							distance2(star,starInitial) > INNERBARRIER*SCALE_CONSTANT) {
						starAr.add(star);
						index++;
					}
				}
			}
			
		}
		else if (GALAXY_TYPE == GalaxyType.PRESET) {
			
			starAr.pop();
			//starAr.get(0).setType(1); //set all stars to type 1 after
			
			List<String> coordsAr = NameList.getNames("preset.txt");

			int strIndex = 0;
			
			coordsAr.sort(null);
			
			//System.out.println(coordsAr.get(0));
			
			for(String coord : coordsAr) {
				String[] coords = coord.split(" ");
			
				float rad = Float.parseFloat(coords[0]);
				float raH = Float.parseFloat(coords[1]);
				float raM = Float.parseFloat(coords[2]);
				float raS = Float.parseFloat(coords[3]);
			
				if (RAD_LOG && (rad != 0)) rad = (float) (Math.log(rad)*RAD_SCALE_PRESET*RAD_LOG_SCALE);
				else rad = (float) ((rad)*RAD_SCALE_PRESET);
				
				String name = coords[4];
			
				for(int i = 5; i < coords.length; i++) {
					name += " " + coords[i]; 
				}
				
				float raDeg = 0.0f;
			
				if (GALACTIC_NORTH_UP) {
					raDeg = -90.0f + 15.0f*(raH+raM/60+raS/3600);
				}
				else {
					raDeg = 15.0f*(raH+raM/60+raS/3600);
				}
			
				float raRad = (float) (raDeg/180.0f*Math.PI);
			
				Star star = new Star(strIndex,rad,raRad,GALAXY_SIZE,true);
				star.setType(1);
				star.setName(name);
				
				if(STAR_REMOVAL) {
					float delta = distToClosestStar(star, starAr.toArray(new Star[0]));
					if (delta > ELIM_R2) {
						starAr.add(star);
						strIndex++;
					}
				}
				else {
					starAr.add(star);
					strIndex++;
				}
				
			}
			//System.out.println(starAr.size());
			
		}
		else {
			throw new RuntimeException("Illegal Galaxy Type");
		}
		
		
		//starAr size for region gen
		haCount = (int) (starAr.size()/STARS_PER_HA);
		nebulaCount = (int) (starAr.size()/STARS_PER_NEBULA);
		hrdCount = (int) (starAr.size()/STARS_PER_HRD);
		lrdCount = (int) (starAr.size()/STARS_PER_LRD);
		
		//setRadii
		for (int i = 0; i < starAr.size(); i++) {
			float delta = distToClosestStar(starAr.get(i), starAr.toArray(new Star[0]));
			if (delta > ELIM_R2 || !(GALAXY_TYPE.hyper())) {
				starAr.get(i).setLaneRadius(delta);
			}
		}
		
		//HA, Nebula, HRD, LRD Generation
		for (int i = 0; i < haCount; i++) {
			int haGen = generator.nextInt(starAr.size());
			
			Star starInput = starAr.get(haGen);
			if (starInput.getType() == 0) {
				RegionHA region = new RegionHA(index+1,starInput.getX(),starInput.getY(),GALAXY_SIZE,false);
				region.setRadius((float) ((float) RADIUS_HA*Math.sqrt(starInput.getLaneRadius())));
				haAr.add(region);
				index++;
				
				//HA degeneration (0 is always unaffected, otherwise 10% chance of remaining) for all stars in radius. 
				for (Star j : starAr) {
					if(j.getType() == 0 && region.inRegion(j)) {
						if (generator.nextInt(HA_STAR_ODDS) == 0) j.setType(2);
						else j.setType(4);
					}
				}
				
				LinkedList<Star> starArA = new LinkedList<Star>();
				for (Star j : starAr) {
					if (j.getType() != 4) {
						starArA.add(j);
					}
				}
				starAr = (LinkedList<Star>) starArA.clone();
			}
		}
		
		for (int i = 0; i < nebulaCount; i++) {
			int nebGen = generator.nextInt(starAr.size());
			
			Star starInput = starAr.get(nebGen);
			if (starInput.getType() == 0) {
				RegionNebula region = new RegionNebula(index+1,starInput.getX(),starInput.getY(),GALAXY_SIZE,false);
				region.setRadius((float) ((float) RADIUS_NEBULA_C*Math.sqrt(starInput.getLaneRadius())));
				nebAr.add(region);
				starInput.setType(3);
				index++;
				
				for (Star j : starAr) {
					if(j.getType() == 0 && region.inRegion(j)) {
						j.setType(3);
						RegionNebula region2 = new RegionNebula(index+1,j.getX(),j.getY(),GALAXY_SIZE,false);
						region2.setRadius((float) ((float) RADIUS_NEBULA_SUB*Math.sqrt(j.getLaneRadius())));
						nebAr.add(region2);
					}
				}
			}
		}
		
		for (int i = 0; i < hrdCount; i++) {
			int hrdGen = generator.nextInt(starAr.size());
			
			Star starInput = starAr.get(hrdGen);
			RegionHighRD region = new RegionHighRD(index+1,starInput.getX(),starInput.getY(),GALAXY_SIZE,false);
			region.setRadius((float) ((float) RADIUS_RD_C*Math.sqrt(starInput.getLaneRadius())));
			hrdAr.add(region);
			starInput.setDrift(starInput.getDrift()+1);
			index++;
				
			for (Star j : starAr) {
				if(j.getType() == 0 && region.inRegion(j)) {
					RegionHighRD region2 = new RegionHighRD(index+1,j.getX(),j.getY(),GALAXY_SIZE,false);
					region2.setRadius((float) ((float) RADIUS_RD_SUB*Math.sqrt(j.getLaneRadius())));
					hrdAr.add(region2);
					j.setDrift(j.getDrift()+1);
				}
			}
		}
		
		for (int i = 0; i < lrdCount; i++) {
			int lrdGen = generator.nextInt(starAr.size());
			
			Star starInput = starAr.get(lrdGen);
			RegionLowRD region = new RegionLowRD(index+1,starInput.getX(),starInput.getY(),GALAXY_SIZE,false);
			region.setRadius((float) ((float) RADIUS_RD_C*Math.sqrt(starInput.getLaneRadius())));
			lrdAr.add(region);
			starInput.setDrift(starInput.getDrift()-1);
			index++;
				
			for (Star j : starAr) {
				if(j.getType() == 0 && region.inRegion(j)) {
					RegionLowRD region2 = new RegionLowRD(index+1,j.getX(),j.getY(),GALAXY_SIZE,false);
					region2.setRadius((float) ((float) RADIUS_RD_SUB*Math.sqrt(j.getLaneRadius())));
					lrdAr.add(region2);
					j.setDrift(j.getDrift()-1);
				}
			}
		}
		
		//regenerate id
		for (int i = 0; i < starAr.size(); i++) {
			starAr.get(i).setId(i);
		}
		
		//outlier degeneration--relative to ID 0
		for (int i = 0; i < starAr.size(); i++) {
			float delta = distToClosestStar(starAr.get(i), starAr.toArray(new Star[0]));
			if (delta > ELIM_R2) {
				starAr.get(i).setLaneRadius(delta);
			}
		}
		for (Star i : starAr) {
			for (Star j : starAr) {
				if (i.getId() != j.getId()) {
					float dist2 = distance2(j, i);
					if (dist2 < i.getLaneRadius() + j.getLaneRadius()) {
						i.addNeighbors(j.getId());
					}
				}
			}
		}
		
		LinkedList<Star> starArA = new LinkedList<Star>();
		for (Star i : starAr) {
			i.inArea = new LinkedList<Integer>();
			i.inArea.add(i.getId());
			i.inArea = findArea(i, starAr, i.inArea);
		
			if (!(GALAXY_TYPE == GalaxyType.IRREGULAR_NOHYPER || 
					GALAXY_TYPE == GalaxyType.PRESET) && i.inArea.contains(0)) {
				starArA.add(i);
			}
			else if ((GALAXY_TYPE == GalaxyType.IRREGULAR_NOHYPER)) {
				starArA.add(i);
			}
			else if ((GALAXY_TYPE == GalaxyType.PRESET)) {
				if (i.inArea.contains(0)) {
					starArA.add(i);
				}
				else {
					float[] outsideStar = distToClosestOutsideStar(i,starAr);
					int new_id = (int) outsideStar[1];
					int i_id = i.getId();
					
					if (i_id < new_id) {
						i.addNeighbors(new_id);
						starAr.get(new_id).addNeighbors(i_id);
						
						i.inArea.add(0);
					}
					else {
						starArA.get(new_id).addNeighbors(i_id);
						i.addNeighbors(new_id);
						
						i.inArea.add(0);
					}
					starArA.add(i);
				}
			}
		}
		
		///MOVED TO ABOVE; this may need to be re-instated
		//for (Star i : starAr) {
		//	if (!(GALAXY_TYPE == GalaxyType.IRREGULAR_NOHYPER || 
		//			GALAXY_TYPE == GalaxyType.PRESET) && i.inArea.contains(0)) {
		//		starArA.add(i);
		//	}
		//	else if ((GALAXY_TYPE == GalaxyType.IRREGULAR_NOHYPER)) {
		//		starArA.add(i);
		//	}
		//	else if ((GALAXY_TYPE == GalaxyType.PRESET)) {
		//		starArA.add(i);
		//	}
		//}
		starAr = (LinkedList<Star>) starArA.clone();
		
		if(GALAXY_TYPE != GalaxyType.PRESET) {
			//regenerate id, reset neighbors
			for (int i = 0; i < starAr.size(); i++) {
				starAr.get(i).setId(i);
				starAr.get(i).removeNeighbors();
			}
			
			//lane generation
			for (int i = 0; i < starAr.size(); i++) {
				float delta = distToClosestStar(starAr.get(i), starAr.toArray(new Star[0]));
				if (delta > ELIM_R2) {
					starAr.get(i).setLaneRadius(delta);
				}
			}
			for (Star i : starAr) {
				for (Star j : starAr) {
					if (i.getId() != j.getId()) {
						float dist2 = distance2(j, i);
						if (dist2 < i.getLaneRadius() + j.getLaneRadius()) {
							i.addNeighbors(j.getId());
						}
					}
				}
			}
			
			//type/drift sets
			for (Star i : starAr) {
				///
				i.setSystem();
			}
		}
		
		
		for (Star i : starAr) {
			if (i.getType() == 4) {
				RegionHA region = new RegionHA(index+1,i.getX(),i.getY(),GALAXY_SIZE,false);
				region.setRadius((float) ((float) RADIUS_NAT_HA*Math.sqrt(i.getLaneRadius())));
				haAr.add(region);
				i.setType(2);
				index++;
			}
			if (i.getType() == 5) {
				RegionNebula region = new RegionNebula(index+1,i.getX(),i.getY(),GALAXY_SIZE,false);
				region.setRadius((float) ((float) RADIUS_NEBULA_SUB*Math.sqrt(i.getLaneRadius())));
				nebAr.add(region);
				i.setType(3);
				index++;
			}
		}
		
		int systemsGenerated = starAr.size();
		if (systemsGenerated > CULTURE_NUM) {
			int[] culture_origin = new int[CULTURE_NUM];
			float[] culture_origin_x = new float[CULTURE_NUM];
			float[] culture_origin_y = new float[CULTURE_NUM];
			for (int j = 0; j < CULTURE_NUM; j++) {
			
				culture_origin[j] = generator.nextInt(systemsGenerated);
				culture_origin_x[j] = starAr.get(j).getX();
				culture_origin_y[j] = starAr.get(j).getY();
			
			}
		//set cultures to closest
		
		//gen culture naming conventions?
		
			System.out.println("Cultures Generated: "+CULTURE_NUM);
		
			try (FileWriter writer = new FileWriter("starlist.txt")) {
				String name = String.format("%-18s", "Star Name");
				String id = String.format("%4s", "ID #");
				String foodYield = String.format("%3s", "F");
				String indYield = String.format("%3s", "I");
				String sciYield = String.format("%3s", "S");
				writer.write(id + " " + name + " " + foodYield + " / " + indYield + " / " + sciYield);
				writer.write(System.getProperty("line.separator"));
			
				for(int i = 0; i < starAr.size(); i++) {
					name = String.format("%-20s", starAr.get(i).getName());
					id = String.format("%04d", starAr.get(i).getId());
					foodYield = String.format("%03d", starAr.get(i).getFoodYield());
					indYield = String.format("%03d", starAr.get(i).getIndYield());
					sciYield = String.format("%03d", starAr.get(i).getSciYield());
					writer.write(id + " " + name + " " + foodYield + " / " + indYield + " / " + sciYield);
					writer.write(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//starAr.sort(Comparator.comparing(Star::getTotalYields));
		//systems to CSV
		if(GALAXY_TYPE != GalaxyType.PRESET) {
			//systems.SystemWriter.writeSystems(starAr);
		}
			
		return new Galaxy(starAr.toArray(new Star[0]),
				haAr.toArray(new RegionHA[0]),
				nebAr.toArray(new RegionNebula[0]),
				hrdAr.toArray(new RegionHighRD[0]),
				lrdAr.toArray(new RegionLowRD[0]),
				regionAr.toArray(new Region[0]));
	}
	
	private static LinkedList<Integer> findArea(Star i, LinkedList<Star> starAr, LinkedList<Integer> inArea) {
		for (Integer j : i.getNeighbors().toArray(new Integer[0])) {
			if(!inArea.contains(j)) {
				inArea.add(j);
				inArea = findArea(starAr.get(j), starAr, inArea);
			}
		}
		return inArea;
	}
	
	//Public Methods
	public static float distance2 (Location star, Location star2) { 
		return (float) (Math.pow((star.getX()-star2.getX()),2)+Math.pow((star.getY()-star2.getY()),2));
	}
	public static float distToClosestStar(Location star, Location[] starAr) {
		float dist = Float.POSITIVE_INFINITY;
		for (Location i : starAr)  {
			if (i.getId() != star.getId()) {
				float dist2 = distance2(star, i);
				if (dist2 < dist) dist = dist2;
			}
		}
		return dist;
	}
	public static float[] distToClosestOutsideStar(Star star, LinkedList<Star> starAr) {
		float dist = Float.POSITIVE_INFINITY;
		float id = 0;
		for (Star i : starAr)  {
			if (i.getId() != star.getId() && i.inArea.contains(0)) {
				float dist2 = distance2(star, i);
				if (dist2 < dist) {
					dist = dist2;
					id = i.getId();
				}
			}
		}
		
		float[] returnAr = new float[2];
		returnAr[0] = dist;
		returnAr[1] = id;
		
		return returnAr;
	}
	
	//Main
	public static void main(String[] args) {
		Galaxy galaxy = generateGalaxy(3, GalaxyType.ELLIPTICAL);
	}

}
