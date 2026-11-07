pipeline {
    agent any
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Build') { steps { bat 'mvnw.cmd -B -DskipTests package' } }
        stage('Unit tests') { steps { bat 'mvnw.cmd -B test' } }
        stage('Integration tests') { steps { bat 'mvnw.cmd -B -Dtest=*IntegrationTest test' } }
        stage('REST Assured tests') { steps { bat 'mvnw.cmd -B -Dtest=*ApiTest test' } }
        stage('Publish test reports') {
            steps { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' }
        }
    }
}