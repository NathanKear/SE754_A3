package Search;

import java.util.*;
import java.util.Map.Entry;

public class SearchQuery {

    private final Map<String, Double> _keywords = new HashMap<String, Double>();

    public SearchQuery(String[] keywords) {
        for (String keyword : keywords) {
            if (_keywords.containsKey(keyword)) {
                this.adjustKeywordWeighting(keyword, 1.0);
            } else {
                _keywords.put(keyword, 1.0);
            }
        }
    }

    public boolean addKeyword(String keyword) {
        if (_keywords.containsKey(keyword)) {
            return false;
        }

        _keywords.put(keyword, 1.0);

        return true;
    }

    public boolean removeKeyword(String keyword) {
        if (_keywords.containsKey(keyword)) {
            _keywords.remove(keyword);

            return true;
        }

        return false;
    }

    public void adjustKeywordWeighting(String keyword, double adjustment) {

        double newWeight = _keywords.get(keyword) + adjustment;

        if (newWeight > 0.0) {
            _keywords.put(keyword, newWeight);
        } else {
            _keywords.remove(keyword);
        }
    }

    public double getKeywordWeighting(String keyword) {

        if (_keywords.containsKey(keyword)) {
            return _keywords.get(keyword);
        }

        return 0.0;
    }

    public List<String> getKeywords() {
        List<Entry<String, Double>> entries = new ArrayList<Entry<String, Double>>(_keywords.entrySet());
        java.util.Collections.sort(entries, new KeywordComparator());

        List<String> keywords = new ArrayList<String>();

        for (Entry<String, Double> entry : entries) {
            keywords.add(entry.getKey());
        }

        return keywords;
    }

    class KeywordComparator implements Comparator<Entry<String, Double>> {
        public int compare(Entry<String, Double> a, Entry<String, Double> b) {
            if (a.getValue() >= b.getValue()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
