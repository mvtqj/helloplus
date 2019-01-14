package fr.ul.miage.helloplus;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class App {
	private static final Logger LOG = Logger.getLogger(App.class.getName());
	
	//attributs
	private String filename;
	//constructeurs
	public App(String filename) {
		setFilename(filename);
	}
	//methodes
	public CSVParser buildCVSParser() throws IOException {
		CSVParser res = null;
		Reader in;
		in = new FileReader(filename);
		CSVFormat csvf = CSVFormat.DEFAULT.withCommentMarker('#').withDelimiter(';');
		res = new CSVParser(in, csvf);
		return res;
	}	
	//setters & getters
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	//main
	public static void main(String[] args) {
		//paramètres
		String filename = null;
		Options options = new Options();
		Option input = new Option("i","input", true, "Nom du fichier CSV");
		input.isRequired();
		options.addOption(input);
		//parsing
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(options, args);
			filename = line.getOptionValue("i");
		} catch (Exception e) {
			LOG.severe("Erreur dans la ligne de commande");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("App", options);
			System.exit(1);
		}
		//processing
	    App app = new App(filename);
	    try {
			CSVParser p = app.buildCVSParser();
			for (CSVRecord r : p) {
				String nom = r.get(0);
				String prenom = r.get(1);
				System.out.println("Hello "+nom+" "+prenom+" !");
			}
		} catch (IOException e) {
			LOG.severe("Erreur de lecture dans le fichier CSV");
			e.printStackTrace();
		}
	}

}
