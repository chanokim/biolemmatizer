package edu.ucdenver.ccp.nlp.biolemmatizer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.northwestern.at.morphadorner.corpuslinguistics.lemmatizer.DefaultLemmatizerRule;
import edu.northwestern.at.morphadorner.corpuslinguistics.lemmatizer.EnglishLemmatizer;
import edu.northwestern.at.utils.ListFactory;
import edu.northwestern.at.utils.StringUtils;

/** Lemmatizer for English. */
public class MorphAdornerLemmatizer extends EnglishLemmatizer implements
		Serializable {

	/** list of detachment rules. */
	protected static String rulesFileName = "englishrules.txt";

	public MorphAdornerLemmatizer(List<String> rawRulesString) throws Exception{
		loadFromString(rawRulesString);
	}

	/**
	 * Create an English lemmatizer.
	 *
	 * @throws Exception
	 *             because the {@link EnglishLemmatizer} constructor throws
	 *             Exception
	 *
	 */
	public MorphAdornerLemmatizer() throws Exception {
		// release the rules of original MorphAdorner Lemmatizer
		rules.clear();
		// load new rules
		try {
			loadRules(BioLemmatizer.class.getResource(rulesFileName), "utf-8");
		} catch (IOException e) {
			throw new RuntimeException("Unable to load English rules file.", e);
		}
		// release the irregularForm file of original MorphAdorner Lemmatizer
		// the irregular English forms are integrated into current Lexicon
		irregularForms.clear();
	}

	public void loadFromString(List<String> lines) {
		String posTag = "";
		String[] tokens = new String[2];

		List rulesForTag = ListFactory.createNewList();

		for (String line : lines) {
			line = line.trim();

			if ((line.length() <= 0) || (line.charAt(0) == '#'))
				continue;
			tokens = StringUtils.makeTokenArray(line);

			if (tokens.length <= 0)
				continue;
			int l = tokens[0].length();

			if (tokens[0].charAt(l - 1) == ':') {
				if (rulesForTag.size() > 0) {
					this.rules.put(posTag, rulesForTag);

					rulesForTag = ListFactory.createNewList();
				}

				posTag = tokens[0].substring(0, l - 1);

				this.rulesWordClasses.add(posTag);
			} else {
				rulesForTag.add(new DefaultLemmatizerRule(line));
			}

		}

		if (rulesForTag.size() > 0) {
			this.rules.put(posTag, rulesForTag);
		}

	}
}
