pipeline{
    agent any
    environment {
        VERSION = "${env.BUILD_ID}"
        DOCKER_URL = "${env.DOCKER_REPO_URL}"
        DOCKER_IMAGE_NAME = "feast"
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

        stage("Docker Build & Docker Push"){
            steps {
                withCredentials([string(credentialsId: 'docker_repo_pass', variable: 'DOCKER_REPO_PASS')]) {
   
                    echo "Creating Docker Image"
                    sh '''
                    docker build -t  ${DOCKER_URL}/${DOCKER_IMAGE_NAME}:${VERSION} .
                    docker login -u admin -p ${DOCKER_REPO_PASS} ${DOCKER_URL}
                    docker push ${DOCKER_URL}/${DOCKER_IMAGE_NAME}:${VERSION}
                    docker rmi ${DOCKER_URL}/${DOCKER_IMAGE_NAME}:${VERSION}
                    '''
                }

            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
