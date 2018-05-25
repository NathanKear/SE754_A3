Narrative:
IN ORDER TO be informed of my search history
AS A user
I WANT TO see number of searches in current session

Scenario: User search session count
Given I am logged in with a BASIC account
When I request account information
Then I want to see number of searches in the current session

Scenario: User total search count
Given I am logged in with a BASIC account
When I request account information
Then I want to see total number of searches