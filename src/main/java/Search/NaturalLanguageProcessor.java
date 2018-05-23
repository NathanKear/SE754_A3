package Search;

import Result.Category;

public interface NaturalLanguageProcessor {
    String[] findKeywords(String phrase);
    String findCategoryLabel(Category category);
    String findCategorySummary(Category category);
}
