pipeline {
    agent any
    stages {
        stage('Build'){
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Tests'){
            steps {
                sh "mvn test"
            }
        }
        stage('Package'){
            steps {
                sh "mvn install -DskipTests"
            }
        }
    }

    post {
        cleanup {
            // leave workspace clean after build
            archiveArtifacts 'target/*.jar'
            junit 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}
