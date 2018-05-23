package BusinessValidation;

import java.util.List;
import Result.Category;
import Result.UnassignedRelevanceException;

public class IdeaValidator {

    public double calculateIdeaMaturity (List<Category> categories) {
        double ideaMaturityScore = 0;

        for (Category c : categories) {
            double popularityWeighting = c.getPopularityWeighting();
            double weightedRelevance;

            try {
                weightedRelevance = c.getWeightedRelevance();
            } catch (UnassignedRelevanceException ex) {
                weightedRelevance = 0;
            }

            ideaMaturityScore += popularityWeighting * weightedRelevance;
        }

        return  ideaMaturityScore;
    }

}
