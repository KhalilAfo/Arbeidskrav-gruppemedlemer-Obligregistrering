/*
 * Oblig 3 (DAPE1400) - forfattet av følgende
 * studenter fra Anvendt datateknologi, 2. året:
 * 
 * Vegar Norman <s189153@stud.hioa.no> 
 * Per Erik Finstad <s189138@stud.hioa.no>
 * Even Holthe <s189124@stud.hioa.no>
 */

import java.util.ArrayList;

public class ObligRegister {
	// https://github.com/evenh/java-oblig3/issues/21
	// 
	// Gått fra Array til ArrayList, da det ikke er mulig
	// å "ta høyde" (oppgaveteksten) for at det er plass i et array
	private ArrayList<Student> studenter;
	private int antallObliger;

	// Konstruktør
	public ObligRegister(int antStud, int antOblig){
		this.studenter = new ArrayList<Student>();
		this.antallObliger = antOblig;
	}

	// Hjelpemetode for ArbeidskravGUI.lesGruppe()
	public ArrayList<Student> getStudenter() {
		return this.studenter;
	}

	public void nyStudent(Student ny) {
		studenter.add(ny);
	}

	public int posisjon(String navn){
		int teller = 0;
		for(Student s : this.studenter){
			if(s.getNavn() == navn){
				return teller;
			}
			teller++;
		}
		return -1;
	}

	public void registrer(Oblig oppg) {
		for (int i = 0; i < oppg.getDeltakere().length; i++) {
			String navn = oppg.getDeltakere()[i].getNavn();
			boolean finnes = false;

			for (Student s : this.studenter) {
				if (navn.equals(s.getNavn())) {
					s.innlevering(oppg);
					finnes = true;
				}
			}

			if (!finnes) {
				Student ny = new Student(oppg.getDeltakere()[i].getNavn(), oppg.getDeltakere()[i].getKlasse(), this.antallObliger);
				this.studenter.add(ny);
				if(this.studenter.size() == 0){
					this.studenter.get(this.studenter.size()).innlevering(oppg);
				} else {
					this.studenter.get(this.studenter.size()-1).innlevering(oppg);
				}
			}
		}
	}

	public String godkjent(String navn) {
		for (Student s : this.studenter) {
			if (navn.equals(s.getNavn())) {
				return s.toString();
			}
		}

		return "Ingen data funnet.";
	}


	public String[] statusListe(String klasse) {

		int studentCounter = 0;

		for (Student s : this.studenter) {
			if (klasse.equals(s.getKlasse())) {
				studentCounter++;
			}
		}

		String[] resultat = new String[studentCounter + 1];
		int indeks = 0;

		for (Student s : this.studenter) {
			if (klasse.equals(s.getKlasse())) {
				resultat[indeks] = s.toString();
				indeks++;
			}
		}

		return resultat;
	}

}
