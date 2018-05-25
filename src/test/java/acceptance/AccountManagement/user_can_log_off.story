Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO log off

Scenario: User log off
Given I am logged in with a BASIC account
When I log off my account
Then I am logged off

Scenario: Admin log off
Given I am logged in with a ADMIN account
When I log off my account
Then I am logged off