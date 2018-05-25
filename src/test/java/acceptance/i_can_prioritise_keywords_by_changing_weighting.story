Narrative:
IN ORDER TO get a better search
AS A user
I WANT TO prioritise keywords of my search

Scenario: Prioritise keyword
Given Application is open
When I input the business idea "The quick brown fox jumps"
And I increase priority of keyword jumps
Then Keywords in order of priority are jumps, fox

Scenario: Deprioritise keyword
Given Application is open
When I input the business idea "The quick brown fox jumps"
And I decrease priority of keyword fox by 0.5
Then Keywords in order of priority are jumps, fox

Scenario: Completely deprioritise keyword
Given Application is open
When I input the business idea "The quick brown fox jumps"
And I decrease priority of keyword fox by 1
Then Keywords in order of priority are jumps