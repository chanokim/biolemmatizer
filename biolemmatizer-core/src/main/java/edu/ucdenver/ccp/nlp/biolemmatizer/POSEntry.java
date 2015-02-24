package edu.ucdenver.ccp.nlp.biolemmatizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.northwestern.at.morphadorner.corpuslinguistics.lexicon.DefaultWordLexicon;
import edu.northwestern.at.morphadorner.corpuslinguistics.lexicon.Lexicon;



/** 
 * POSEntry: store different POS tags and the corresponding tagset label 
 * 
 * */
public class POSEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	public Map<String, String> tagToTagSet;

	/**
	 * new constructor to be remove dependency on file system.
	 * @param wordLexicon
	 * @param mappingPennPOStoNUPOS
	 */
	public POSEntry(DefaultWordLexicon wordLexicon, Map<String, String[]> mappingPennPOStoNUPOS) {
		tagToTagSet = new HashMap<String, String>();
		addNewTagSet(Arrays.asList(wordLexicon.getCategories()), "NUPOS");
		addNewTagSet(mappingPennPOStoNUPOS.keySet(), "PennPOS");
	}
	
	/**
	 * Construtor to initialize the class field by loading different POS tagsets
	 */
	public POSEntry() {
		tagToTagSet = new HashMap<String, String>();
		// NUPOS tags
		Lexicon wordLexicon;
		try {
			wordLexicon = new DefaultWordLexicon();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		addNewTagSet(Arrays.asList(wordLexicon.getCategories()), "NUPOS");

		// PennPOS tags
		String mappingFileName = "PennPOStoNUPOS.mapping";
		InputStream is = BioLemmatizer.class.getResourceAsStream(mappingFileName);
		Map<String, String[]> mappingPennPOStoNUPOS;
		try {
			mappingPennPOStoNUPOS = BioLemmatizer.loadPOSMappingFile(is);
		} catch (IOException e) {
			throw new RuntimeException("Error while opening mapping file: " + mappingFileName, e);
		}
		addNewTagSet(mappingPennPOStoNUPOS.keySet(), "PennPOS");
	}

	/**
	 * Add new POS tagset
	 *
	 * @param tags
	 *            a set of POS tags
	 * @param tagSetLabel
	 *            the corresponding tagset label
	 */
	public void addNewTagSet(Collection<String> tags, String tagSetLabel) {
		for (String tag : tags) {
			tagToTagSet.put(tag, tagSetLabel);
		}
	}

	/**
	 * Retrieve the tagset label of the input POS tag
	 *
	 * @param category
	 *            an input POS tag
	 * @return the corresponding POS tagset label
	 */
	public String getTagSetLabel(String category) {
		String defaultLabel = "NONE";
		return tagToTagSet.containsKey(category) ? tagToTagSet.get(category) : defaultLabel;
	}
}