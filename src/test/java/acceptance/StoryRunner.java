package acceptance;

import acceptance.AccountManagement.AccountManagementSteps;
import acceptance.BusinessIdeaValidation.BusinessIdeaValidationSteps;
import acceptance.DocumentSearch.DocumentSearchSteps;
import acceptance.KeywordExtraction.KeywordExtractionSteps;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

public class StoryRunner extends JUnitStories{

    private String[] _storyFiles = new String[] {
            "acceptance/KeywordExtraction/i_can_input_business_idea_and_get_keywords.story",
            "acceptance/KeywordExtraction/i_can_prioritise_keywords_by_changing_weighting.story",
            "acceptance/DocumentSearch/i_can_search_and_get_a_set_of_documents.story",
            "acceptance/KeywordExtraction/i_can_add_or_remove_keywords.story",
            "acceptance/BusinessIdeaValidation/i_can_search_and_get_idea_maturity.story",
            "acceptance/AccountManagement/user_can_signup.story"
    };

    public StoryRunner() {
        configuredEmbedder()
                .embedderControls()
                .doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(false)
                .doIgnoreFailureInView(false)
                .doVerboseFailures(true)
                .useThreads(1);
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                        .withDefaultFormats()
                        .withFormats(Format.CONSOLE, Format.TXT, Format.HTML_TEMPLATE, Format.XML_TEMPLATE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new KeywordExtractionSteps(), new DocumentSearchSteps(), new KeywordExtractionSteps(), new BusinessIdeaValidationSteps(), new AccountManagementSteps());
    }

    @Override
    protected List<String> storyPaths() {
        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findPaths(codeLocation, _storyFiles, new String[] {});
    }

}