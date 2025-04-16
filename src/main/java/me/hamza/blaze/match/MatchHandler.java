package me.hamza.blaze.match;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MatchGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hammzar
 * @since 16.04.2025
 */
@Getter
public class MatchHandler {

    private final List<Match> matches = new ArrayList<>();

    public void addMatch(Match cMatch) {
        matches.add(cMatch);
    }

    public void removeMatch(Match cMatch) {
        matches.remove(cMatch);
    }
}
