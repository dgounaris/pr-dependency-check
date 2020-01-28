# pr-dependency-check

A jenkins library to facilitate pr dependency checks in the build pipeline.

#Usage
Call the function `prDependencyCheck` specifying the parameters `currentBranch` and `targetBranch`.
Returns a list of unsatisfied pr dependencies.
If it is non-empty, the pipeline should be aborted, as there are unmerged pr dependencies in the new commits.
