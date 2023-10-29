# electricity-data-analysis

## Purpose
The purpose of this project is to have a framework to perform various types of analyses of energy-related data sets.

## Logic

- `RunProcessing` is the top level executable.
  - It calls `DataProcessor` that
    - uses `DataFileReader` which relies on `AbstractDataFileReader`
    - and provides selected sets of `ElectricityRecord`


