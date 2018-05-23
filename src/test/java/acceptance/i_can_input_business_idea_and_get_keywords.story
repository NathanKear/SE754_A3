Narrative:
IN ORDER TO get relevant search results
AS A user
I WANT TO get the keywords of my search

Scenario: Find single keyword
Given Application is open
When I input the business idea "The quick brown fox"
Then Keywords found fox

Scenario: Find multiple keywords
Given Application is open
When I input the business idea "The quick brown fox jumps over the lazy dog"
Then Keywords found fox, jumps, dog

Scenario: Find no keywords
Given Application is open
When I input the business idea ""
Then No keywords found