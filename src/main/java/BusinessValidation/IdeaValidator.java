package BusinessValidation;

import java.util.List;
import Result.Category;

public class IdeaValidator {

    public double calculateIdeaMaturity (List<Category> categories) {
        double ideaMaturityScore = 0;

        for (Category c : categories) {
            ideaMaturityScore += c.getPopularityWeighting() * c.getWeightedRelevance();
        }

        return  ideaMaturityScore;
    }

}
