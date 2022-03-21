# USE CASE: 2. Three reports for top X most populated countries in world, continent or region where X is manually entered. 

## CHARACTERISTIC INFORMATION

### Goal in Context

As an employee of World health organisation, I require the top X most populated countries in the world, continent and region where I am able to enter the X, so I am able to know the first countries I will need to contact regarding any health risks.

### Scope

Company.

### Level

Primary task

### Preconditions

Database contains the population and location of countries.

### Success End Condition

Three different reports segmented by area are made available to the employees where they can enter the top X they want to review.
Reports:
 - The top N populated countries in the world where N is provided by the user.
 - The top N populated countries in a continent where N is provided by the user.
 - The top N populated countries in a region where N is provided by the user.

### Failed End Condition

No reports are produced.

### Primary Actor

Employee of World Health Organisation

### Trigger

Request for the top X most populated cities in an area.

## MAIN SUCCESS SCENARIO

1. Requests top X countries by population from largest to the smallest.
2. Requests top X continents by population from largest to the smallest.
3. Requests top X regions by population from largest to the smallest.
4. Employee has easy access to the 3 different reports where they can enter X.

## EXTENSIONS

1. User enters 0 so the report returns 0 values.
2. User enters more values than the table has available.
3. User enters non numerical value.

## SUB-VARIATIONS

None.

## SCHEDULE

12/04/2022: All requested reports provided 