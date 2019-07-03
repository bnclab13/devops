pipeline {

    agent any

    stages {

        stage('Build Application'){

            steps {

                sh "mvn clean compile"

            }

        }

       stage('Sonarqube') {

            environment {

                scannerHome = tool 'scanner1'

            }

            steps {

                withSonarQubeEnv('install1') {

                    sh "${scannerHome}/bin/sonar-scanner " +
                        "-Dsonar.java.binaries=/var/lib/jenkins/workspace/EventsManager_develop/target/classes"

                }
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

        



     
        stage('Build Image Docker'){

            when {

                branch 'develop'

            }

            steps {

                sh "sudo docker build -t event-manager ."

            }

        }

        stage('Deploy'){

            when {

                branch 'develop'

            }

            steps {

                script {

                  try {

                      sh 'sudo docker rm -f EventManagerAPI'

                  } catch (Exception e) {

                      echo "Container does not exist... But it's OK"

                  }

                  sh "sudo docker run --name EventManagerAPI -p 50001:9090 -d event-manager"

                }

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
