# USE CASE: 4. Five reports for top X most populated cities in world, continent, region, country or district where X is manually entered.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an employee of World health organisation, I require the top X most populated cities in the world, continent, region, country or district where I am able to enter the X, so I have easy access to the first city representatives I will need to contact regarding any health risks.

### Scope

Company.

### Level

Primary task

### Preconditions

Database contains the population and location of cities.

### Success End Condition

Five different reports segmented by area showing the top X cities from most populated to least where they can enter X they want to review.

Reports:
 - The top N populated cities in the world where N is provided by the user.
 - The top N populated cities in a continent where N is provided by the user.
 - The top N populated cities in a region where N is provided by the user.
 - The top N populated cities in a country where N is provided by the user.
 - The top N populated cities in a district where N is provided by the user.

### Failed End Condition

No reports are produced.

### Primary Actor

Employee of World Health Organisation

### Trigger

Request to see the top X cities by population in different areas.

## MAIN SUCCESS SCENARIO

1. Requests for all top X cities in the world by population from largest to the smallest
2. Requests for all top X cities in the continent by population from largest to the smallest.
3. Requests for all top X cities in the region by population from largest to the smallest
4. Requests for all top X cities in the country by population from largest to the smallest
5. Requests for all top X cities in the district by population from largest to the smallest
6. Employee has easy access to the 5 different reports where they can enter X.

## EXTENSIONS

1. User enters 0 so the report returns 0 values.
2. User enters more values than the table has available.
3. User enters non numerical value.

## SUB-VARIATIONS

None.

## SCHEDULE

12/04/2022: All requested reports provided 