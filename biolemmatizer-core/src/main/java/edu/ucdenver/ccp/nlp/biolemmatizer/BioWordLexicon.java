package edu.ucdenver.ccp.nlp.biolemmatizer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import edu.northwestern.at.morphadorner.corpuslinguistics.lexicon.DefaultLexicon;


/**
 * BioWordLexicon: Biomedical word Lexicon which extends MorphAdorner's English
 * word lexicon.
 */
public class BioWordLexicon extends DefaultLexicon implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** Resource path to word lexicon. */
	protected static final String lexiconPath = "lexicon.lex";

	/**
	 * Create an empty lexicon.
	 * 
	 * @throws IOException
	 */
	public BioWordLexicon(File lexiconFile) throws IOException {
		// Create empty lexicon.
		super();
		if (lexiconFile == null) {
			// Load default word lexicon.
			loadLexicon(BioLemmatizer.class.getResource(lexiconPath), "utf-8");
		} else {
			loadLexicon(lexiconFile.toURI().toURL(), "utf-8");
		}
	}
}
