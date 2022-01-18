pipeline{
    agent any
    stages {
        stage('Sonar Quality Check') {
            agent {
                docker {
                    image 'openjdk:11'
                }
            }
            steps {
                script{
                    withSonarQubeEnv(credentialsId: 'sonar_token') {
                        echo 'Building..'
                        //sh './mvnw clean package'
                        sh './mvnw sonar:sonar'
                }
                }


            }
        }
        stage('Archive Artifacts') {
            steps {
                echo 'Archiving..'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, followSymlinks: false
            }
        }

        stage("Create Docker Image"){
            steps {
                echo "Creating Docker Image"
                sh 'docker build -t  feast:v${BUILD_ID} .'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}