package acceptance;

import BusinessValidation.IdeaValidator;
import Result.Category;
import Result.Document;
import Result.DocumentHandler;
import Result.Relevance;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class BusinessIdeaValidationSteps {

    ArrayList<Document> _documents;
    ArrayList<Category> _categories;
    DocumentHandler _documentHandler;

    @Given("The application is open")
    public void initialiseApplication() {
        _documentHandler = new DocumentHandler();
        _documents = new ArrayList<Document>();
        _categories = new ArrayList<Category>();
    }

    @Given("A valid search")
    public void createSearch() {
        _documents.add(new Document("Tourism"));
        _documents.add(new Document("Tourism"));
        _documents.add(new Document("Tourism"));

        _documents.add(new Document("Commerce"));
        _documents.add(new Document("Commerce"));
        _documents.add(new Document("Commerce"));
    }

    @When("The documents are processed")
    public void processDocuments() {
        _categories = _documentHandler.categorise(_documents);
    }

    @When("I assign a category a relevancy of $relevance")
    public void assignRelevancy(Relevance relevance) {
        for (Category c : _categories) {
            if (c.getRelevanceType() == null) {
                c.setRelevance(relevance);
            }
        }
    }

    @Then("The documents should be categorised and weighted")
    public void checkForCategorisationAndWeightOfDocuments(){
        assertEquals(2, _categories.size());

        Category tourism = _categories.get(0);
        Category commerce = _categories.get(0);

        assertEquals(0.5, tourism.getPopularityWeighting(), 0);
        assertEquals(0.5, commerce.getPopularityWeighting(), 0);
    }

    @Then("I should see the maturity of my idea")
    public void checkBusinessMaturity() {
        IdeaValidator ideaValidator = new IdeaValidator();
        double ideaMaturity = ideaValidator.calculateIdeaMaturity(_categories);
        System.out.println(ideaMaturity);
    }
}