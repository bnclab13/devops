    pipeline {
        agent any
        options {
            // only keep at most 30 job results
            buildDiscarder(logRotator(numToKeepStr: '30'))

            // timeout control for the build
            timeout(time: 30, unit: 'MINUTES')
        }

        stages {

            stage('Build jar and run tests'){
                steps {
                    script {
                            sh "mvn clean install"
                    }
                }
            }

            stage('Publish to SonarQube') {
                steps {
                    sh "echo source code quality check"
                }
            }

            stage('Snapshot to Nexus') {
                when {
                    branch 'develop'
                }
                steps {
                    sh "echo publish snapshot to nexus"
                }
            }

            stage('Release to Nexus') {
                when {
                    branch 'master'
                }
                steps {
                    sh "echo publish release to nexus"
                }
            }
        }

        post {

            cleanup {
                // leave workspace clean after build
                cleanWs()
            }
        }
    }
