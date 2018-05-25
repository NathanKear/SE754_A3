Narrative:
IN ORDER TO search for my business idea
AS A user
I WANT TO be returned fully categorised documents

Scenario: Obtain a list of detailed (labelled and summarised) categories containing documents from a search
Given The search API is connected
When I input the Search Query with keywords "Auckland Pet Care"
Then The set of documents returned totals 10, is split into 3 categories, with the first category labelled Dog and summarised as Dog Walking in Auckland
