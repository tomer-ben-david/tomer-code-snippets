package org.tomerbd.snippets.nlp;

import javax.inject.Named;

@Named
public class NLPConfig {
    public String getTargetLocalModelDir() { return "/home/itamar/tmp"; }
    public String getModelBaseURL() { return "http://opennlp.sourceforge.net/models-1.5"; }
}
