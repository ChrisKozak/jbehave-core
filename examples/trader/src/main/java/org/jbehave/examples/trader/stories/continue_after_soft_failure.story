Scenario: A scenario with softly failing step

Given I do nothing
Then I fail softly
Given I do nothing
Given I do nothing


Scenario: A scenario with a hard failing step

Given I do nothing
Then I fail hard
Given I do nothing
Given I do nothing


Scenario: A scenario with both a soft and a hard failing step should report the hard failure

Given I do nothing
Then I fail softly
Given I do nothing
Then I fail hard
Given I do nothing
Given I do nothing