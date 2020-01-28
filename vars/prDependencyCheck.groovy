def call(Map<String, String> args) {
    assert args['currentBranch'] != null
    assert args['targetBranch'] != null

    // pull target branch, in order to compare the git logs
    // this requires a checkout
    sh "git checkout ${args['targetBranch']}"
    sh 'git pull'
    sh "git checkout ${args['currentBranch']}"
    List<String> unmergedPRIds = sh(returnStdout: true, script: 'git ls-remote origin "pull/*/merge" | awk "{print \$2}" | cut -d"/" -f3')
            .split('\n')
    String difflog = sh(returnStdout: true, script: "git log ${args['targetBranch']}..${args['currentBranch']}")
    List<String> unmergedPRKeywords = unmergedPRIds.stream().collect { "requires[" + it + "]" }
    List<String> unmergedRequired = []
    unmergedPRKeywords.each {
        if (difflog.contains(it)) {
            unmergedRequired.add(it)
        }
    }
    return unmergedRequired
}