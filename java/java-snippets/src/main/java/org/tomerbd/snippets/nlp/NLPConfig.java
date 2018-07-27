package org.tomerbd.snippets.nlp;

import javax.inject.Named;

@Named
public class NLPConfig {
    public String getTargetLocalModelDir() { return "/home/itamar/tmp"; }
    public String getModelBaseURL() { return "http://opennlp.sourceforge.net/models-1.5"; }
    public String getTokenizerENModelName() { return "en-token.bin"; }
    public String getTokenzierENModelFilePath() { return getTargetLocalModelDir() + "/" + getTokenizerENModelName(); }
}
