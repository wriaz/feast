pipeline{
    agent any
    environment {
        VERSION = "${env.BUILD_ID}"
    }
    stages {
        stage('Sonar Quality Check') {
            agent {
                docker {
                    image 'openjdk:17-alpine'
                }
            }
            steps {
                script{
                    withSonarQubeEnv(credentialsId: 'sonar_token') {
                        echo 'Building..'
                        sh './mvnw clean package'
                        sh './mvnw sonar:sonar'
                    }
                    timeout(time: 30, unit: 'MINUTES') {
                        sleep 10
                        def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
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
                sh 'docker build -t  feast:${VERSION} .'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
