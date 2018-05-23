package Search;

import Result.Category;

public interface NaturalLanguageProcessor {
    String[] findKeywords(String phrase);
    String extractCategoryLabel(Category category);
    String extractCategorySummary(Category category);

}
