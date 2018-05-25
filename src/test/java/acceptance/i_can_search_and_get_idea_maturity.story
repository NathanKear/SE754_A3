Narrative:
IN ORDER TO understand the search results better
AS A user
I WANT TO see the importance/popularity of the returned categories

Scenario: Documents form search are categorised and weighted
Given The application is open
And A valid search
When The documents are processed
And I assign a category a relevancy of WEAK_RELEVANT
And I assign a category a relevancy of VERY_RELEVANT
Then The documents should be categorised and weighted
And I should see the maturity of my idea