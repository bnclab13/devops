pipeline {

    agent any

    stages {

        stage('Build Application'){

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

        



     
        stage('Build Image Docker'){

            when {

                branch 'develop'

            }

            steps {

                sh "sudo docker build -t event-manager ."

            }

        }

        
     stage('Sonarqube') {

            environment {

                scannerHome = tool 'scanner1'

            }

            steps {

                withSonarQubeEnv('install1') {

                    sh "${scannerHome}/bin/sonar-scanner " +
                        "-Dsonar.log.level=debug \
                         -Dsonar.sources=/var/lib/jenkins/workspace/EventsManager_develop/src/main/java/ca/bnc/nbfg/devops \
                         -Dsonar.tests=/var/lib/jenkins/workspace/EventsManager_develop/src/test/java/ca/bnc/nbfg/devops \
                         -Dsonar.java.binaries=/var/lib/jenkins/workspace/EventsManager_develop/target/classes \
                         -Dsonar.java.libraries=/var/lib/jenkins/workspace/EventsManager_develop/target/*.jar \
                         -Dsonar.java.test.binaries=/var/lib/jenkins/workspace/EventsManager_develop/target/test-classes \
                         -Dsonar.junit.reportPaths=/var/lib/jenkins/workspace/EventsManager_develop/target/surefire-reports"


                }
            }

        }
        
        
        stage('Push Dockerhub and Deploy'){

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

                  sh "sudo docker login --username=lab13bnc --password=edgendalab13"
				  sh "sudo docker tag event-manager:latest bnclab13/event-manager:latest"
                  sh "sudo docker push bnclab13/event-manager:latest"
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
