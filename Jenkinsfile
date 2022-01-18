pipeline{
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh './mvnw clean package'
            }
        }
        stage('Archive Artifacts') {
            steps {
                echo 'Archiving..'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, followSymlinks: false
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}