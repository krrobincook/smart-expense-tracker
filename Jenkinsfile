pipeline {
    agent any
    stages {
        stage('Build') { steps { sh 'chmod +x mvnw && ./mvnw -B -DskipTests package' } }
        stage('Unit tests') { steps { sh './mvnw -B test' } }
        stage('Integration tests') { steps { sh './mvnw -B -Dtest=*IntegrationTest test' } }
        stage('REST Assured tests') { steps { sh './mvnw -B -Dtest=*ApiTest test' } }
        stage('Publish test reports') {
            steps { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' }
        }
    }
}
