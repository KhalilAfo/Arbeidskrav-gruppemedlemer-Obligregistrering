/*
 * Oblig 3 (DAPE1400) - forfattet av følgende
 * studenter fra Anvendt datateknologi, 2. året:
 * 
 * Vegar Norman <s189153@stud.hioa.no> 
 * Per Erik Finstad <s189138@stud.hioa.no>
 * Even Holthe <s189124@stud.hioa.no>
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ArbeidskravGUI extends JFrame implements ActionListener {


	// Attributter
	//============

	private JTextField 		person,
							obligNr,
							kl;

	private JTextField[] 	navn,
						 	klasse;

	private JButton 		reg,
						 	sjekk,
						 	status;

	private JTextArea 		output;

	private ObligRegister 	kartotek;

	private JCheckBox 		godkjent;


	// Konstruktør
	//============

	public ArbeidskravGUI(String fag, int antStudenter, int antObliger, int antPrGruppe) {

		// Konstruktør arvet fra JFrame
		super (fag + " - Arbeidskrav");

		// Opprett nytt ObligRegistert basert på informasjon fra konstruktør
		this.kartotek = new ObligRegister(antStudenter, antObliger);

		// Opprett JTextField-objekter
		this.person = new JTextField(10);
		this.kl = new JTextField(10);
		this.obligNr = new JTextField(1);

		// Opprett JTextField[]-arrayer
		this.navn = new JTextField[antPrGruppe];
		this.klasse = new JTextField[antPrGruppe];

		// Fyll JTextField[]-arrayer med JTextField-objekter
		for (int i = 0; i < this.navn.length; i++) {
			navn[i] = new JTextField(20);
			klasse[i] = new JTextField(5);
		}

		// Opprett JButton, JCheckBox og JTextArea-objekter
		this.reg = new JButton("Innlevering");
		this.sjekk = new JButton("Sjekk godkjenning for student: ");
		this.status = new JButton("Liste over godkjente i klasse: ");
		this.godkjent = new JCheckBox("Godkjent");
		this.output = new JTextArea(35, 35);

		// Lag lyttere til JButton-objektene
		this.reg.addActionListener(this);
		this.sjekk.addActionListener(this);
		this.status.addActionListener(this);

		// Hva skal gjøres?
		this.reg.setActionCommand("registrer");
		this.sjekk.setActionCommand("sjekk");
		this.status.setActionCommand("status");

		// Lag en Container med FlowLayout
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// Legg alle objekter i JTextField[]-arrayene til Containeren
		for (int i = 0; i < this.navn.length; i++) {
			c.add(new JLabel("Navn: "));
			c.add(navn[i]);
			c.add(new JLabel("Klasse: "));
			c.add(klasse[i]);
		}

		// Legg alle andre objekter til Containeren
		c.add(new JLabel("Obligatorisk oppgave nr: "));
		c.add(this.obligNr);
		c.add(this.godkjent);
		c.add(this.reg);
		c.add(this.sjekk);
		c.add(this.person);
		c.add(this.status);
		c.add(this.kl);
		c.add(new JScrollPane(output));

		// Innstillinger for JFrame
		if(System.getProperty("os.name").toLowerCase().indexOf("windows")!= -1){
			setSize(420, 800);
		} else {
			setSize(440, 800);
		}
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


	private Gruppemedlem[] lesGruppe() {
		Gruppemedlem[] resultat = new Gruppemedlem[this.navn.length];

		for (int i = 0; i < this.navn.length; i++) {
			resultat[i] = new Gruppemedlem(this.navn[i].getText(), this.klasse[i].getText());
		}

		return resultat;
	}

	public void registrer() {
		boolean erGodkjent = this.godkjent.isSelected();
		int obligNummer = Integer.parseInt(this.obligNr.getText());
		Gruppemedlem[] deltakere = this.lesGruppe();
		Oblig o = new Oblig(deltakere, obligNummer, erGodkjent);

		this.kartotek.registrer(o);
	}

	public void sjekkGodkjenning() {
		String sjekkNavn = this.person.getText();
		boolean funnet = false;

		for (Student s : this.kartotek.getStudenter()) {
			if (sjekkNavn.equals(s.getNavn()) ) {
				this.output.append(s.toString());
				funnet = true;
			}
		}

		if (!funnet) {
			this.output.append("Beklager, studenten ble ikke funnet.\n");
		}
	}

	public void skrivListe() {
		String sjekkKlasse = this.kl.getText();

		for (Student s : this.kartotek.getStudenter()) {
			if (sjekkKlasse.equals(s.getKlasse())) {
				this.output.append(s.toString());
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "registrer":
				System.out.println("[INFO] Registrerer student...");
				this.registrer();
			break;

			case "sjekk":
				System.out.println("[INFO] Sjekker for godkjent oblig...");
				this.sjekkGodkjenning();
			break;

			case "status":
				System.out.println("[INFO] Sjekker status...");
				this.skrivListe();
			break;
		}
	}

}
