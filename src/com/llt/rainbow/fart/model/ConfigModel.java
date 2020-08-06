package com.llt.rainbow.fart.model;

import java.util.List;

/**
 * @author llt
 */
public class ConfigModel {

    List<Contribute> contributes;

    List<Contribute> timers;

    List<String> longTimes;

    public List<Contribute> getContributes() {
        return contributes;
    }

    public List<Contribute> getTimers() {
        return timers;
    }

    public void setTimers(List<Contribute> timers) {
        this.timers = timers;
    }

    public List<String> getLongTimes() {
        return longTimes;
    }

    public void setLongTimes(List<String> longTimes) {
        this.longTimes = longTimes;
    }

    public void setContributes(List<Contribute> contributes) {
        this.contributes = contributes;
    }

    public static class Contribute{
        List<String> keywords;
        List<String> voices;

        public List<String> getVoices() {
            return voices;
        }

        public void setVoices(List<String> voices) {
            this.voices = voices;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }
    }
}
