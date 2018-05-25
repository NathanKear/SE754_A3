Narrative:
GIVEN I am logged in as a user AND I have entered a valid search
WHEN I add or remove keywords from the search
THEN my search query is altered

Scenario: Add keyword
Given Application is open
When I input the business idea "The quick brown fox jumps over the lazy dog"
And I add the keyword quick
Then Keywords found fox, jumps, dog, quick

Scenario: Remove keyword
Given Application is open
When I input the business idea "The quick brown fox jumps over the lazy dog"
And I remove the keyword jumps
Then Keywords found fox, dog